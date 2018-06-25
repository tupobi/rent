package cn.hhit.canteen.login.model;

import cn.hhit.canteen.app.utils.bean.StringResponse;

/**
 * Created by Administrator on 2018/6/23/023.
 */

public interface ILoginModel {
    interface RegisterCallback {

        void onSuccess(StringResponse res);

        void onFailed();
    }

    interface LoginCallback {
        void onSuccess(StringResponse res);

        void onFailed();
    }

    void register(String userName, String userPassword, int userType, RegisterCallback
            registerCallback);

    void login(String userName, String userPassword, int userType, LoginCallback loginCallback);
}
