package cn.hhit.canteen.page_user.model;

import cn.hhit.canteen.page_user.model.bean.User;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public interface IUserModel {
    interface GetUserInfoCallback {
        void onSuccess(User user);

        void onFailed();
    }

    void getUserInfo(String userName, GetUserInfoCallback getUserInfoCallback);
}
