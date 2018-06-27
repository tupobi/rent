package cn.hhit.canteen.house_manage.model;

import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.bean.StringResponse;

/**
 * Created by Administrator on 2018/6/26/026.
 */

public interface IAddHouseModel {
    interface UploadHouseInfoCallback {
        void onSuccess(StringResponse stringResponse);

        void onFailed();
    }

    void uploadHouseInfo(Map<String, String> fieldMap, List<String> picDirectoryList,
                         UploadHouseInfoCallback uploadHouseInfoCallback);
}
