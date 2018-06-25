package cn.hhit.canteen.page_user.presenter;

import android.content.Context;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.page_user.model.IUserModel;
import cn.hhit.canteen.page_user.model.UserModelImpl;
import cn.hhit.canteen.page_user.model.bean.User;
import cn.hhit.canteen.page_user.view.IUserView;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public class UserPresenterImpl implements IUserPresenter {
    private Context mContext;
    private IUserView mIUserView;
    private IUserModel mIUserModel;

    public UserPresenterImpl(IUserView iUserView, Context context) {
        mIUserView = iUserView;
        mContext = context;
        mIUserModel = new UserModelImpl();
    }

    @Override
    public void getUserInfo(final String userName) {
        mIUserModel.getUserInfo(userName, new IUserModel.GetUserInfoCallback() {
            @Override
            public void onSuccess(User user) {
                mIUserView.updateUserInfo(user);
                LogUtil.e("getUserInfo user == " + user.toString());
            }

            @Override
            public void onFailed() {
                ToastUtil.showShort(mContext, "服务器异常");
            }
        });
    }
}
