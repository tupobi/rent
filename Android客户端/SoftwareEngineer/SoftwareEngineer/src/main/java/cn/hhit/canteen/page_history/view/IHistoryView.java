package cn.hhit.canteen.page_history.view;

import java.util.List;

import cn.hhit.canteen.house_manage.model.bean.House;

/**
 * Created by Administrator on 2018/7/2/002.
 */

public interface IHistoryView {
    void setRvHistoryData(List<House> houses);

    void setRvCollectData(List<House> houses);
}
