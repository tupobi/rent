package cn.hhit.canteen.page_user.model;

import cn.hhit.canteen.app.utils.bean.StringResponse;

/**
 * Created by Administrator on 2018/6/24/024.
 */

public interface IUserInfoModel {
    interface UploadAvatarCallback {
        void onSuccess(StringResponse stringResponse);

        void onFailed();
    }

    void updateAvatar(String avatarPath, String userName, UploadAvatarCallback
            uploadAvatarCallback);
}
