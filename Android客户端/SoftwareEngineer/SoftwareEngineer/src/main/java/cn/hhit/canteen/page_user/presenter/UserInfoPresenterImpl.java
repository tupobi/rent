package cn.hhit.canteen.page_user.presenter;

import android.content.Context;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.page_user.model.IUserInfoModel;
import cn.hhit.canteen.page_user.model.UserInfoModelImpl;
import cn.hhit.canteen.page_user.view.IUserInfoUploadView;
import cn.hhit.canteen.page_user.view.IUserView;

/**
 * Created by Administrator on 2018/6/24/024.
 */

public class UserInfoPresenterImpl implements IUserInfoPresenter {

    private IUserView mIUserView;
    private IUserInfoModel mIUserInfoModel;
    private IUserInfoUploadView mIUserInfoUploadView;
    private Context mContext;

    public UserInfoPresenterImpl(IUserInfoUploadView iUserInfoUploadView, Context context) {
        mIUserInfoUploadView = iUserInfoUploadView;
        mIUserInfoModel = new UserInfoModelImpl();
        mContext = context;
    }

    @Override
    public void uploadAvatarPicture(String avatarPath, String userName) {
        mIUserInfoUploadView.startUpload();
        mIUserInfoModel.updateAvatar(avatarPath, userName, new IUserInfoModel.UploadAvatarCallback() {
            @Override
            public void onSuccess(StringResponse stringResponse) {
                LogUtil.e("uploadAvatarPicture stringResponse == " + stringResponse.getResponse());
                if ("true".equals(stringResponse.getResponse())) {
                    //上传成功
                    ToastUtil.showShort(mContext, "上传成功");
                    mIUserInfoUploadView.stopUpload();
                    mIUserInfoUploadView.updateAvatar();
                } else {
                    //上传失败
                    ToastUtil.showShort(mContext, "上传失败");
                    mIUserInfoUploadView.stopUpload();
                }
            }

            @Override
            public void onFailed() {
                ToastUtil.showShort(mContext, "服务器异常");
                mIUserInfoUploadView.stopUpload();
            }
        });
    }
}
