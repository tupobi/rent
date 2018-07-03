package cn.hhit.canteen.meal_detail.view.meal_detail_tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.house_manage.model.bean.House;

/**
 * Created by Administrator on 2018/5/6/006.
 */

@SuppressLint("ValidFragment")
public class TabFgMealDetail extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.house_name)
    TextView mHouseName;
    @BindView(R.id.house_eyes_passed)
    TextView mHouseEyesPassed;
    @BindView(R.id.tv_house_now_price)
    TextView mTvHouseNowPrice;
    @BindView(R.id.tv_house_origin_price)
    TextView mTvHouseOriginPrice;
    @BindView(R.id.house_description)
    TextView mHouseDescription;
    @BindView(R.id.house_type)
    TextView mHouseType;
    @BindView(R.id.house_area)
    TextView mHouseArea;
    @BindView(R.id.house_detail_address)
    TextView mHouseDetailAddress;
    @BindView(R.id.house_contact)
    TextView mHouseContact;

    private House mHouse;

    public TabFgMealDetail(House house) {
        mHouse = house;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fg_meal_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mHouseName.setText(mHouse.getHouseName());
        mHouseEyesPassed.setText("" + (int) (100 + Math.random() * 500));
        mTvHouseNowPrice.setText("" + mHouse.getMonthPrice());
        mTvHouseOriginPrice.setText("" + mHouse.getMonthPrice());
        mHouseDescription.setText(mHouse.getHouseDescription());
        mHouseType.setText(mHouse.getHouseTyle());
        mHouseArea.setText(mHouse.getHouseArea());
        mHouseDetailAddress.setText(mHouse.getHouseLocation());
        mHouseContact.setText(mHouse.getOwnerPhone());
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
