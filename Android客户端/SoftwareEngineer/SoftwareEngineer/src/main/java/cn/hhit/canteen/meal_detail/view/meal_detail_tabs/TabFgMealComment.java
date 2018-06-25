package cn.hhit.canteen.meal_detail.view.meal_detail_tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.meal_detail.view.adapter.RvMealCommentAdapter;

/**
 * Created by Administrator on 2018/5/6/006.
 */

public class TabFgMealComment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.rv_meal_comment)
    RecyclerView mRvMealComment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fg_meal_comment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initRvMealComment();
        return rootView;
    }

    private void initRvMealComment() {
        mRvMealComment.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<String> data = new ArrayList<>();

        RvMealCommentAdapter rvMealCommentAdapter = new RvMealCommentAdapter(getActivity(), data);
        mRvMealComment.setAdapter(rvMealCommentAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
