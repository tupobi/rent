package cn.hhit.canteen.page_rentedhouse.model;

import java.util.List;

import cn.hhit.canteen.house_manage.model.bean.House;

/**
 * Created by Administrator on 2018/6/29/029.
 */

public interface IRentedHouseModel {
    interface GetAllHousesInfoCallback {
        void onSuccess(List<House> houses);

        void onFailed(Throwable throwable);
    }

    void getAllHousesInfo(GetAllHousesInfoCallback getAllHousesInfoCallback);
}
