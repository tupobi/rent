package cn.hhit.canteen.page_history.model;

import cn.hhit.canteen.page_rentedhouse.model.IRentedHouseModel;

/**
 * Created by Administrator on 2018/7/2/002.
 */

public interface IHistoryModel {
    void getHouseByHistory(String userName, IRentedHouseModel.GetAllHousesInfoCallback
            getAllHousesInfoCallback);

    void getHouseByCollect(String userName, IRentedHouseModel.GetAllHousesInfoCallback
            getAllHousesInfoCallback);
}
