package cn.hhit.canteen.meal_detail.presenter;

import android.content.Context;

import java.util.List;

import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.meal_detail.model.CommentModelImpl;
import cn.hhit.canteen.meal_detail.model.ICommentModel;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.ICommentView;

/**
 * Created by Administrator on 2018/7/3/003.
 */

public class CommentPresenterImpl implements ICommentPresenter {
    private Context mContext;
    private ICommentModel mICommentModel;
    private ICommentView mICommentView;

    public CommentPresenterImpl(Context context, ICommentView ICommentView) {
        mContext = context;
        mICommentView = ICommentView;
        mICommentModel = new CommentModelImpl();
    }

    @Override
    public void getCommentsByHouseNameAndSetData(String houseName) {
        mICommentModel.getCommentsByHouseName(houseName, new ICommentModel
                .GetCommentsByHouseNameCallback() {

            @Override
            public void onSuccess(List<Comment> comments) {
                LogUtil.e("comments == " + comments.toString());
                if (comments == null) {
                    LogUtil.e("comments == null");
                    return;
                }
                mICommentView.setCommentData(comments);
            }

            @Override
            public void onFailed(Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.e(throwable.getMessage());
            }
        });
    }
}
