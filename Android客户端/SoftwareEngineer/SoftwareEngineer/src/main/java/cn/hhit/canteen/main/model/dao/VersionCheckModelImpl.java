package cn.hhit.canteen.main.model.dao;

import java.util.HashMap;
import java.util.Map;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory1;
import cn.hhit.canteen.main.model.bean.AppVersion;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class VersionCheckModelImpl implements IVersionCheckModel {
    @Override
    public void getLatestVersion(String appName, final Callback callback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("appName", appName);
        RetrofitFactory1.getInstence().API().getLatestVersion(HttpConfig.PATH, HttpConfig
                .GET_LATEST_VERSION, queryOptions).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Observer<AppVersion>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AppVersion appVersion) {
                callback.onSuccess(appVersion);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                callback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
