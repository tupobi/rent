package cn.hhit.canteen.house_manage.presenter;

import android.content.Context;

import java.util.List;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.house_manage.model.HouseManagerModelImpl;
import cn.hhit.canteen.house_manage.model.IHouseManagerModel;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.house_manage.view.IHouseManagerView;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;

/**
 * Created by Administrator on 2018/6/30/030.
 */

public class HouseManagerPresenterImpl implements IHouseManagerPresenter {
    private IHouseManagerModel mIHouseManagerModel;
    private IHouseManagerView mIHouseManagerView;
    private Context mContext;

    public HouseManagerPresenterImpl(IHouseManagerView IHouseManagerView, Context context) {
        mIHouseManagerView = IHouseManagerView;
        mContext = context;
        mIHouseManagerModel = new HouseManagerModelImpl();
    }

    @Override
    public void getHouseAndSetData(String username) {
        mIHouseManagerModel.getHouseByUserName(username, new IRentedHouseModel
                .GetAllHousesInfoCallback() {

            @Override
            public void onSuccess(List<House> houses) {
                LogUtil.e("getHouseAndSetData houses == " + houses);
                if (houses == null) {
                    ToastUtil.showShort(mContext, "查看房源失败");
                    return;
                }
                mIHouseManagerView.setData(houses);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.e(throwable.getMessage());
            }
        });
    }
}
