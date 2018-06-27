package cn.hhit.canteen.page_rentedhouse.presenter;

import android.content.Context;

import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.page_rentedhouse.view.IRentedHouseView;
import cn.hhit.canteen.page_user.model.IUserModel;
import cn.hhit.canteen.page_user.model.UserModelImpl;
import cn.hhit.canteen.page_user.model.bean.User;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public class RentedHousePresenterImpl implements IRentedHousePresenter {
    private IRentedHouseView mIRentedHouseView;
    private Context mContext;
    private IUserModel mIUserModel;

    public RentedHousePresenterImpl(IRentedHouseView IRentedHouseView, Context context) {
        mIRentedHouseView = IRentedHouseView;
        mContext = context;
        mIUserModel = new UserModelImpl();
    }

    @Override
    public void isHouseOwner(String userName) {
        mIUserModel.getUserInfo(userName, new IUserModel.GetUserInfoCallback() {
            @Override
            public void onSuccess(User user) {
                if (user != null && !user.getUserName().isEmpty()) {
                    if (user.getUserType() == 1) {
                        mIRentedHouseView.startAtyHouseManage();
                    } else {
                        ToastUtil.showShort(mContext, "您不是房东哦");
                    }
                } else {
                    ToastUtil.showShort(mContext, "用户信息错误");
                }
            }

            @Override
            public void onFailed() {
                    ToastUtil.showShort(mContext, "服务器异常");
            }
        });
    }
}
