package cn.hhit.canteen.page_user.model;

import java.util.HashMap;
import java.util.Map;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.page_user.model.bean.User;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public class UserModelImpl implements IUserModel {
    @Override
    public void getUserInfo(String userName, final GetUserInfoCallback getUserInfoCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);
        RetrofitFactory.getInstence().API().getAvatarUrl(HttpConfig.GET_USER_INFO, queryOptions)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<User>() {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                getUserInfoCallback.onSuccess(user);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                getUserInfoCallback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
