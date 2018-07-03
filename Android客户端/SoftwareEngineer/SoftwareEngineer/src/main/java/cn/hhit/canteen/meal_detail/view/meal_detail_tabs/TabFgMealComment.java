package cn.hhit.canteen.meal_detail.view.meal_detail_tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.bean.MessageEvent;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import cn.hhit.canteen.meal_detail.presenter.CommentPresenterImpl;
import cn.hhit.canteen.meal_detail.presenter.ICommentPresenter;
import cn.hhit.canteen.meal_detail.view.adapter.RvMealCommentAdapter;
import cn.hhit.canteen.page_user.view.AtyUploadViewAvatar;

/**
 * Created by Administrator on 2018/5/6/006.
 */

@SuppressLint("ValidFragment")
public class TabFgMealComment extends Fragment implements ICommentView{
    Unbinder unbinder;
    @BindView(R.id.rv_meal_comment)
    RecyclerView mRvMealComment;

    private RvMealCommentAdapter mRvMealCommentAdapter;
    private House mHouse;
    private List<Comment> mComments = new ArrayList<>();
    private ICommentPresenter mICommentPresenter;

    public TabFgMealComment(House house) {
        mHouse = house;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fg_meal_comment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        EventBus.getDefault().register(this);

        mICommentPresenter = new CommentPresenterImpl(getActivity(), this);
        initRvMealComment();
        LogUtil.e("Comment mHouse == " + mHouse);
        mICommentPresenter.getCommentsByHouseNameAndSetData(mHouse.getHouseName());
        return rootView;
    }

    private void initRvMealComment() {
        mRvMealComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvMealCommentAdapter = new RvMealCommentAdapter(getActivity(), mComments);
        mRvMealComment.setAdapter(mRvMealCommentAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setCommentData(List<Comment> comments) {
        mRvMealCommentAdapter.setData(comments);
        mRvMealCommentAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (UPDATE_COMMENT.equals(messageEvent.getMessage())) {
            mICommentPresenter.getCommentsByHouseNameAndSetData(mHouse.getHouseName());
        }
    }

    public static final String UPDATE_COMMENT = "update_comment";
}
