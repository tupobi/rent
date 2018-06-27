package cn.hhit.canteen.house_manage.presenter;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/26/026.
 */

public interface IAddHousePresenter {
    void uploadHouseInfo(Map<String, String> fieldMap, List<String> picFilesList);
}
