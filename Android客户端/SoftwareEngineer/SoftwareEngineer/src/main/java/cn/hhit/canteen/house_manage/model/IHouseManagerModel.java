package cn.hhit.canteen.house_manage.model;

import java.util.List;

import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;

/**
 * Created by Administrator on 2018/6/30/030.
 */

public interface IHouseManagerModel {
    interface GetHouseByUserNameCallback {
        void onSuccess(List<House> houses);

        void onFailed(Throwable throwable);

    }

    void getHouseByUserName(String userName, IRentedHouseModel.GetAllHousesInfoCallback
            getAllHousesInfoCallback);
}
