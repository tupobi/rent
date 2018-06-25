package cn.hhit.canteen.main.presenter.download.thread;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import cn.hhit.canteen.main.presenter.download.listener.DownloadListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/12/1.
 */


public class DownloadTask extends AsyncTask<String, Integer, Integer> {//三个泛型参数，第一个表示在执行AsyncTask时需传入一个字符串参数给后台任务，
    //第二个使用整型数据最为进度显示单位，第三个表示使用整型数据来反馈结果执行

    //定义四个整型常量分别表示下载的不同状态
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSE = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener){
        this.listener = listener;//将下载的状态通过此参数进行回调
    }

    //在后台执行具体的下载逻辑
    @Override
    protected Integer doInBackground(String... params) {//String... params:可变长参数列表，必须是String类型，转化为数组处理
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            long downloadedLength = 0;//记录已下载的文件长度
            String downloadUrl = params[0];//获取下载的URL地址
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));//根据URL地址解析出下载的文件名
//            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();//指定下载路径Environment.DIRECTORY_DOWNLOADS，SD卡的Download目录
            String directory = Environment.getExternalStorageDirectory().getPath() + File.separator + "canteen" + File.separator;
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdir();
            }
//            file = new File(directory + fileName);
            file = new File(directory + "canteen.apk");
            if (file.exists()){//判断是否已存在要下载的文件，存在则读取已下载的字节数（可启用断点续传功能）
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);//获取待下载文件的总长度
            if (contentLength == 0){//总长度为0，说明文件有问题
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength){//已下载字节和文件总字节相等，说明已经下载完成了
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载，指定从哪个字节开始下载（已下载部分不需再重新下载）
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){//使用Java文件流方式不断从网络上读取数据，不断写入到本地，直到文件全部下载完
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength);//跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCanceled){//判断用户有没触发暂停或取消操作，如果有则返回相应值来中断下载
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSE;
                    }else {
                        total += len;
                        savedFile.write(b, 0, len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadedLength) * 100 / contentLength);
                        publishProgress(progress);//通知进度
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }
                if (isCanceled && file != null){
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    //在界面更新当前的下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress  = values[0];//获取当前下载进度
        if (progress > lastProgress){//与上一次下载进度对比
            listener.onProgress(progress);//有变化则调用DownloadListener的onProgress()通知下载进度更新
            lastProgress = progress;
        }
    }

    //通知最终下载结果
    @Override
    protected void onPostExecute(Integer status) {
        switch (status){//根据传入的下载状态进行回调
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSE:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException{
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

}