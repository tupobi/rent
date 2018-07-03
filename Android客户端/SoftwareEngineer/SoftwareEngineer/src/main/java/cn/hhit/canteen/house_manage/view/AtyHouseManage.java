package cn.hhit.canteen.house_manage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.house_manage.presenter.HouseManagerPresenterImpl;
import cn.hhit.canteen.house_manage.presenter.IHouseManagerPresenter;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.page_rentedhouse.view.adapter.RvAllMealAdapter;

public class AtyHouseManage extends AppCompatActivity implements IHouseManagerView {

    @BindView(R.id.fab_add_house)
    FloatingActionButton mFabAddHouse;
    @BindView(R.id.tb_manage_house)
    Toolbar mTbManageHouse;
    @BindView(R.id.rv_my_house)
    RecyclerView mRvMyHouse;
    private RvAllMealAdapter mRvAllMealAdapter;
    private List<House> mHouses = new ArrayList<>();
    private IHouseManagerPresenter mIHouseManagerPresenter;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyHouseManage.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_house_manage);
        ButterKnife.bind(this);
        initToolbar();
        initRv();

        mIHouseManagerPresenter = new HouseManagerPresenterImpl(this, this);
        mIHouseManagerPresenter.getHouseAndSetData(SpUtils.getInstance().getString(AtyLogin
                .SESSION_USERNAME, ""));
    }

    private void initRv() {
        mRvAllMealAdapter = new RvAllMealAdapter(this, mHouses);
        mRvMyHouse.setLayoutManager(new LinearLayoutManager(this));
        mRvMyHouse.setAdapter(mRvAllMealAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(mTbManageHouse);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTbManageHouse.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.fab_add_house)
    public void onViewClicked() {
        AtyAddHouse.actionStart(AtyHouseManage.this);
    }

    @Override
    public void setData(List<House> houses) {
        mHouses = houses;
        mRvAllMealAdapter.setData(mHouses);
        mRvAllMealAdapter.notifyDataSetChanged();
    }
}
