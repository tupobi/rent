package cn.hhit.canteen.meal_detail.presenter;

import cn.hhit.canteen.meal_detail.model.bean.Comment;

/**
 * Created by Administrator on 2018/7/1/001.
 */

public interface IMealDetailPresenter {
    void addHistory(String userName, String houseName);

    void addCollect(String userName, String houseName);

    void addCommentAndShowResult(String content, String houseName);
}
