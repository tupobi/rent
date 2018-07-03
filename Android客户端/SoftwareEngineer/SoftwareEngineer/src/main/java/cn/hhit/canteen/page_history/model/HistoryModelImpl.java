package cn.hhit.canteen.page_history.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/2/002.
 */

public class HistoryModelImpl implements IHistoryModel {

    @Override
    public void getHouseByHistory(String userName, final IRentedHouseModel.GetAllHousesInfoCallback
            getAllHousesInfoCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                ""));
        RetrofitFactory.getInstence().API().getHousesByHistory(HttpConfig.GET_HOUSES_BY_HISTORY,
                queryOptions).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread
                ()).subscribe(new Observer<List<House>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<House> houses) {
                getAllHousesInfoCallback.onSuccess(houses);
            }

            @Override
            public void onError(Throwable e) {
                getAllHousesInfoCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void getHouseByCollect(String userName, final IRentedHouseModel.GetAllHousesInfoCallback
            getAllHousesInfoCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                ""));
        RetrofitFactory.getInstence().API().getHousesByCollect(HttpConfig.GET_HOUSES_BY_COLLECT,
                queryOptions).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread
                ()).subscribe(new Observer<List<House>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<House> houses) {
                getAllHousesInfoCallback.onSuccess(houses);
            }

            @Override
            public void onError(Throwable e) {
                getAllHousesInfoCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
