package cn.hhit.canteen.page_rentedhouse.presenter;

import android.content.Context;

import java.util.List;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.network.GsonUtil;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;
import cn.hhit.canteen.page_rentedhouse.model.RentedHouseModelImpl;
import cn.hhit.canteen.page_rentedhouse.view.IRentedHouseView;
import cn.hhit.canteen.page_user.model.IUserModel;
import cn.hhit.canteen.page_user.model.UserModelImpl;
import cn.hhit.canteen.page_user.model.bean.User;

/**
 * Created by Administrator on 2018/6/25/025.
 */

public class RentedHousePresenterImpl implements IRentedHousePresenter {

    public static final String CACHED_HOUSES_INFO = "cached_houses_info";

    private IRentedHouseView mIRentedHouseView;
    private Context mContext;
    private IUserModel mIUserModel;
    private IRentedHouseModel mIRentedHouseModel;

    public RentedHousePresenterImpl(IRentedHouseView IRentedHouseView, Context context) {
        mIRentedHouseView = IRentedHouseView;
        mContext = context;
        mIUserModel = new UserModelImpl();
        mIRentedHouseModel = new RentedHouseModelImpl();
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

    @Override
    public void getAllHousesInfo() {
        mIRentedHouseModel.getAllHousesInfo(new IRentedHouseModel.GetAllHousesInfoCallback() {
            @Override
            public void onSuccess(List<House> houses) {
                LogUtil.e("getAllHousesInfo houses == " + houses.toString());
                if (houses != null) {
                    SpUtils.getInstance().save(RentedHousePresenterImpl.CACHED_HOUSES_INFO,
                            GsonUtil.beanList2JsonList(houses));
                }
                mIRentedHouseView.setData();
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.e("getAllHousesInfo onFailed == " + throwable.getMessage());
            }
        });
    }
}
