package cn.hhit.canteen.login.model;

import java.util.HashMap;
import java.util.Map;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/23/023.
 */

public class LoginModelImpl implements ILoginModel {

    @Override
    public void register(String userName, String userPassword, int userType, final
    RegisterCallback registerCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);
        queryOptions.put("userPassword", userPassword);
        queryOptions.put("userType", String.valueOf(userType));

        RetrofitFactory.getInstence().API().register(HttpConfig.REGISTER, queryOptions)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<StringResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse response) {
                LogUtil.e("regist onNext response == " + response);
                registerCallback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("onError" + e.getMessage());
                e.printStackTrace();
                registerCallback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void login(String userName, String userPassword, int userType, final LoginCallback
            loginCallback) {

        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);
        queryOptions.put("userPassword", userPassword);
        queryOptions.put("userType", String.valueOf(userType));

        RetrofitFactory.getInstence().API().login(HttpConfig.LOGIN, queryOptions).subscribeOn
                (Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<StringResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse response) {
                LogUtil.e("login onNext response == " + response);
                loginCallback.onSuccess(response);
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("onError" + e.getMessage());
                e.printStackTrace();
                loginCallback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}

