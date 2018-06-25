package cn.hhit.canteen.main.model.dao;

import cn.hhit.canteen.main.model.bean.AppVersion;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public interface IVersionCheckModel {
    void getLatestVersion(String appName, Callback callback);

    interface Callback {

        void onSuccess(AppVersion appVersion);

        void onFailed();
    }

}
