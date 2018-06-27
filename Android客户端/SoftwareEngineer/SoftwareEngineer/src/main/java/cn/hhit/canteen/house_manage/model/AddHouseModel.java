package cn.hhit.canteen.house_manage.model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/6/26/026.
 */

public class AddHouseModel implements IAddHouseModel {

    @Override
    public void uploadHouseInfo(Map<String, String> fieldMap, List<String> picFileList, final
    UploadHouseInfoCallback uploadHouseInfoCallback) {
//        Map<String, RequestBody> filesMap = new HashMap<>();
//
//        for (int i = 0; i < picFileList.size(); i++) {
//            File file = new File(picFileList.get(i));
//            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
//            filesMap.put("pic" + (i + 1) + "Url", photoRequestBody);
//        }
//
//        RetrofitFactory.getInstence().API().uploadHouseInfo(HttpConfig.UPLOAD_HOUSE_INFO,
//                fieldMap, filesMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers
//                .mainThread()).subscribe(new Observer<StringResponse>() {
//
//
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(StringResponse stringResponse) {
//                LogUtil.e("uploadHouseInfo stringResponse == " + stringResponse);
//                uploadHouseInfoCallback.onSuccess(stringResponse);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                e.printStackTrace();
//                uploadHouseInfoCallback.onFailed();
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        Map<String, RequestBody> filesMap = new HashMap<>();

        for (int i = 0; i < picFileList.size(); i++) {
            File file = new File(picFileList.get(i));
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
            filesMap.put("pic" + (i + 1) + "Url" + "\"; " + "filename=\"pic.png", photoRequestBody);
            //第一个参数是key
        }

        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            filesMap.put(entry.getKey(), RequestBody.create(null, entry.getValue()));
        }

        RetrofitFactory.getInstence().API().uploadHouseInfo(HttpConfig.UPLOAD_HOUSE_INFO,
                filesMap).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StringResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse stringResponse) {
                LogUtil.e("uploadHouseInfo stringResponse == " + stringResponse);
                uploadHouseInfoCallback.onSuccess(stringResponse);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                uploadHouseInfoCallback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
