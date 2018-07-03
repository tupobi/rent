package cn.hhit.canteen.meal_detail.model;

import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.meal_detail.model.bean.Comment;

/**
 * Created by Administrator on 2018/7/1/001.
 */

public interface IMealDetailModel {
    interface InsertHistoryCallback{
        void onSuccess(StringResponse stringResponse);

        void onFailed(Throwable throwable);
    }

    void insertHistory(String userName, String houseName, InsertHistoryCallback
            insertHistoryCallback);

    void addCollect(String userName, String houseName, InsertHistoryCallback
            insertHistoryCallback);

    void addComment(Comment comment, InsertHistoryCallback insertHistoryCallback);
}
