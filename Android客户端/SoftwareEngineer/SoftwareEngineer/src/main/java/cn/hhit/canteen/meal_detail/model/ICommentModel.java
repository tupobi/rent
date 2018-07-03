package cn.hhit.canteen.meal_detail.model;

import java.util.List;

import cn.hhit.canteen.meal_detail.model.bean.Comment;

/**
 * Created by Administrator on 2018/7/3/003.
 */

public interface ICommentModel {
    interface GetCommentsByHouseNameCallback{
        void onSuccess(List<Comment> comments);

        void onFailed(Throwable throwable);
    }

    void getCommentsByHouseName(String houseName, GetCommentsByHouseNameCallback
            getCommentsByHouseNameCallback);
}
