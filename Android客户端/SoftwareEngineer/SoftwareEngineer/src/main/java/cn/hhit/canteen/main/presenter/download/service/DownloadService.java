package cn.hhit.canteen.main.presenter.download.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;
import java.util.List;

import cn.hhit.canteen.BuildConfig;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.CanteenApplication;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.main.presenter.download.listener.DownloadListener;
import cn.hhit.canteen.main.presenter.download.thread.DownloadTask;
import cn.hhit.canteen.main.view.AtyMain;


/**
 * Created by Administrator on 2018/1/8.
 */

public class DownloadService extends Service {
    private static String CHANNEL_ID = "1";

    private DownloadTask downloadTask;
    private String downloadUrl;

    //创建DownloadListener匿名类实例
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("正在下载...", progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载成功", -1));
            Toast.makeText(DownloadService.this, "下载成功，开始安装", Toast.LENGTH_SHORT).show();
            LogUtil.e("下载成功，开始安装");

            installApk();
        }

        private Rationale mRationale = new Rationale() {
            @Override
            public void showRationale(Context context, List<String> permissions,
                                      final RequestExecutor executor) {
                // 这里使用一个Dialog询问用户是否继续授权。
                new AlertDialog.Builder(CanteenApplication.getGlobalApplication())
                        .setTitle("下面我们将获取权限")
                        .setMessage("请求获取权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 如果用户继续：
                                executor.execute();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 如果用户中断：
                        executor.cancel();
                    }
                }).show();

            }
        };

        @Override
        public void onFailed() {
            downloadTask = null;
            //下载失败时将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();
            LogUtil.e("下载失败");
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            getNotificationManager().notify(1, getNotification("Download Paused", -1));
//            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
            LogUtil.e("下载中止");
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
//            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
            LogUtil.e("下载取消");
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification("Downloading...", 0));//注意对应创建的频道
                Toast.makeText(DownloadService.this, "开始下载", Toast.LENGTH_SHORT).show();
                LogUtil.e("开始下载");
            }
        }

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    //取消下载时需将文件删除，并将通知关闭
                    //注意下载的地址，取消下载也要相应改变
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directroy = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directroy + fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress) {
        //设置适配android O的频道：
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);

            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            manager.createNotificationChannel(channel);

            Intent intent = new Intent(this, AtyMain.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            builder.setContentText(title);

            builder.setChannelId(CHANNEL_ID);

            if (progress >= 0) {
                //当progress大于或等于0时才需显示下载进度
                builder.setContentText(progress + "%");
                builder.setProgress(100, progress, false);//三个参数：通知的最大进度，通知的当前进度，是否使用模糊进度条
            }
            return builder.build();
        } else {
            Intent intent = new Intent(this, AtyMain.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setContentIntent(pi);
            builder.setContentTitle(title);
            builder.setContentText(title);
            if (progress >= 0) {
                //当progress大于或等于0时才需显示下载进度
                builder.setContentText(progress + "%");
                builder.setProgress(100, progress, false);//三个参数：通知的最大进度，通知的当前进度，是否使用模糊进度条
            }
            return builder.build();
        }
    }

    private void installApk() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "canteen" + File.separator + "canteen.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //.fileprovider和manifest中注册的fileProvider的authority属性对应
            Uri contentUri = FileProvider.getUriForFile(DownloadService.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }
}
