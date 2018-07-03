package cn.hhit.canteen.page_history.presenter;

import android.content.Context;
import android.icu.lang.UScript;

import java.util.ArrayList;
import java.util.List;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.network.GsonUtil;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.page_history.model.HistoryModelImpl;
import cn.hhit.canteen.page_history.model.IHistoryModel;
import cn.hhit.canteen.page_history.view.IHistoryView;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;
import cn.hhit.canteen.page_rentedhouse.presenter.RentedHousePresenterImpl;

/**
 * Created by Administrator on 2018/7/2/002.
 */

public class HistoryPresenterImpl implements IHistoryPresenter {

    private List<House> mCachedHouses = new ArrayList<>();
    private Context mContext;
    private IHistoryModel mIHistoryModel;
    private IHistoryView mIHistoryView;

    public HistoryPresenterImpl(Context context, IHistoryView IHistoryView) {
        mContext = context;
        mIHistoryView = IHistoryView;
        mIHistoryModel = new HistoryModelImpl();
    }

    @Override
    public void getHistoryHousesInfoAndSetData(String userName) {
        mIHistoryModel.getHouseByHistory(userName, new IRentedHouseModel.GetAllHousesInfoCallback
                () {

            @Override
            public void onSuccess(List<House> houses) {
                if (SpUtils.getInstance().getString(RentedHousePresenterImpl.CACHED_HOUSES_INFO,
                        "").isEmpty()) {
                } else {
                    List<House> cachedHouses = GsonUtil.jsonList2BeanList(SpUtils.getInstance()
                            .getString(RentedHousePresenterImpl.CACHED_HOUSES_INFO, ""), House
                            .class);
                    mCachedHouses = cachedHouses;
                }
                LogUtil.e("history data == " + houses.toString());
                List<House> resHouses = new ArrayList<>();
                for (int i = 0; i < houses.size(); i++) {
                    for (int j = 0; j < mCachedHouses.size(); j++) {
                        if (houses.get(i).getHouseName().equals(mCachedHouses.get(j).getHouseName
                                ())) {
                            resHouses.add(mCachedHouses.get(j));
                        }
                    }
                }
                mIHistoryView.setRvHistoryData(resHouses);
            }

            @Override
            public void onFailed(Throwable throwable) {
                LogUtil.e(throwable.getMessage());
                throwable.printStackTrace();
                ToastUtil.showShort(mContext, "连接失败");
            }
        });
    }

    @Override
    public void getCollectHousesInfoAndSetData(String userName) {
        mIHistoryModel.getHouseByCollect(userName, new IRentedHouseModel.GetAllHousesInfoCallback
                () {
            @Override
            public void onSuccess(List<House> houses) {
                if (SpUtils.getInstance().getString(RentedHousePresenterImpl.CACHED_HOUSES_INFO,
                        "").isEmpty()) {
                } else {
                    List<House> cachedHouses = GsonUtil.jsonList2BeanList(SpUtils.getInstance()
                            .getString(RentedHousePresenterImpl.CACHED_HOUSES_INFO, ""), House
                            .class);
                    mCachedHouses = cachedHouses;
                }
                LogUtil.e("history data == " + houses.toString());
                List<House> resHouses = new ArrayList<>();
                for (int i = 0; i < houses.size(); i++) {
                    for (int j = 0; j < mCachedHouses.size(); j++) {
                        if (houses.get(i).getHouseName().equals(mCachedHouses.get(j).getHouseName
                                ())) {
                            resHouses.add(mCachedHouses.get(j));
                        }
                    }
                }
                mIHistoryView.setRvCollectData(resHouses);
            }

            @Override
            public void onFailed(Throwable throwable) {
                LogUtil.e(throwable.getMessage());
                throwable.printStackTrace();
                ToastUtil.showShort(mContext, "连接失败");
            }
        });
    }
}
