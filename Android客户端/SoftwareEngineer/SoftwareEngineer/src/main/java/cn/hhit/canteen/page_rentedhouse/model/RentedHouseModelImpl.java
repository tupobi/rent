package cn.hhit.canteen.page_rentedhouse.model;

import java.util.List;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.house_manage.model.bean.House;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/29/029.
 */

public class RentedHouseModelImpl implements IRentedHouseModel {
    @Override
    public void getAllHousesInfo(final GetAllHousesInfoCallback getAllHousesInfoCallback) {
        RetrofitFactory.getInstence().API().getAllHousesInfi(HttpConfig.GET_ALL_HOUSES_INFO)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<List<House>>() {


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
