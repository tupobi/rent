package cn.hhit.canteen.house_manage.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/30/030.
 */

public class HouseManagerModelImpl implements IHouseManagerModel {
    @Override
    public void getHouseByUserName(String userName, final IRentedHouseModel
            .GetAllHousesInfoCallback getAllHousesInfoCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);

        RetrofitFactory.getInstence().API().getHouseByUsername(HttpConfig.GET_HOUSE_BY_USERNAME,
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
