package cn.hhit.canteen.meal_detail.presenter;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.sql.Date;
import java.text.SimpleDateFormat;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.bean.MessageEvent;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.meal_detail.model.IMealDetailModel;
import cn.hhit.canteen.meal_detail.model.MealDetailModelImpl;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import cn.hhit.canteen.meal_detail.view.IMealDetailView;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealComment;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealDetail;
import cn.hhit.canteen.page_user.model.IUserModel;
import cn.hhit.canteen.page_user.model.UserModelImpl;
import cn.hhit.canteen.page_user.model.bean.User;

/**
 * Created by Administrator on 2018/7/1/001.
 */

public class MealDetailPresenterImpl implements IMealDetailPresenter {
    private Context mContext;
    private IMealDetailModel mIMealDetailModel;
    private IMealDetailView mIMealDetailView;

    public MealDetailPresenterImpl(Context context, IMealDetailView IMealDetailView) {
        mContext = context;
        mIMealDetailView = IMealDetailView;
        mIMealDetailModel = new MealDetailModelImpl();
    }

    @Override
    public void addHistory(String userName, String houseName) {
        mIMealDetailModel.insertHistory(userName, houseName, new IMealDetailModel
                .InsertHistoryCallback() {

            @Override
            public void onSuccess(StringResponse stringResponse) {
                LogUtil.e("addHistory stringResponse == " + stringResponse.getResponse());
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.e(throwable.getMessage());
            }
        });
    }

    @Override
    public void addCollect(String userName, String houseName) {
        mIMealDetailModel.addCollect(userName, houseName, new IMealDetailModel
                .InsertHistoryCallback() {

            @Override
            public void onSuccess(StringResponse stringResponse) {
                LogUtil.e("addCollect stringResponse == " + stringResponse.getResponse());
                if (stringResponse.getResponse().equals("true")) {
                    ToastUtil.showShort(mContext, "收藏成功！");
                } else {
                    ToastUtil.showShort(mContext, "收藏失败！");
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.e(throwable.getMessage());
            }
        });
    }

    @Override
    public void addCommentAndShowResult(final String content, final String houseName) {

        IUserModel iUserModel = new UserModelImpl();
        iUserModel.getUserInfo(SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME, ""), new IUserModel.GetUserInfoCallback() {

            @Override
            public void onSuccess(User user) {
                Comment comment = new Comment();
                comment.setAvatarUrl(user.getAvatarUrl());
                comment.setContent(content);
                comment.setUserName(user.getUserName());
                comment.setHouseName(houseName);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
                comment.setDate(date);
                mIMealDetailModel.addComment(comment, new IMealDetailModel.InsertHistoryCallback() {
                    @Override
                    public void onSuccess(StringResponse stringResponse) {
                        LogUtil.e("addCommentAndShowResult + stringResponse == " + stringResponse
                                .getResponse());
                        if ("true".equals(stringResponse.getResponse())) {
                            ToastUtil.showShort(mContext, "评论成功！");
                            EventBus.getDefault().post(new MessageEvent(TabFgMealComment.UPDATE_COMMENT));
                        } else {
                            ToastUtil.showShort(mContext, "评论失败或已评论过！");
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailed() {

            }
        });

    }
}
