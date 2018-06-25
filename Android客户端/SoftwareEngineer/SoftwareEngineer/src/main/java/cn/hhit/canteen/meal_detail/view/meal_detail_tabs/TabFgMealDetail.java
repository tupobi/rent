package cn.hhit.canteen.meal_detail.view.meal_detail_tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hhit.canteen.R;

/**
 * Created by Administrator on 2018/5/6/006.
 */

@SuppressLint("ValidFragment")
public class TabFgMealDetail extends Fragment {
    Unbinder unbinder;


    public TabFgMealDetail() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fg_meal_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
