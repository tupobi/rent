package cn.hhit.canteen.page_history.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.network.GsonUtil;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.page_history.presenter.HistoryPresenterImpl;
import cn.hhit.canteen.page_history.presenter.IHistoryPresenter;
import cn.hhit.canteen.page_rentedhouse.presenter.RentedHousePresenterImpl;
import cn.hhit.canteen.page_rentedhouse.view.adapter.RvAllMealAdapter;

/**
 * Created by Administrator on 2018/4/22/022.
 */

@SuppressLint("ValidFragment")
public class FgHistory extends Fragment implements IHistoryView {

    @BindView(R.id.tb_history)
    Toolbar mTbHistory;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;
    Unbinder unbinder;
    @BindView(R.id.srl_history)
    SwipeRefreshLayout mSrlHistory;

    private RvAllMealAdapter mRvAllMealAdapter;
    private List<House> mHouses = new ArrayList<>();
    private final Context mContext;
    private int historyOrCollect = 0;
    private IHistoryPresenter mIHistoryPresenter;

    public FgHistory(Context context) {
        this.mContext = context;
        mRvAllMealAdapter = new RvAllMealAdapter(mContext, mHouses);
        mIHistoryPresenter = new HistoryPresenterImpl(mContext, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_history, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        initToolbar();
        initRv();
        initSwipRefreshLayout();

        mIHistoryPresenter.getHistoryHousesInfoAndSetData(SpUtils.getInstance()
                .getString(AtyLogin.SESSION_USERNAME, ""));

        return rootView;
    }

    private void initSwipRefreshLayout() {
        mSrlHistory.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color
                .holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        //刷新的弹力，越大阻力越大
        mSrlHistory.setDistanceToTriggerSync(450);
        //第一个参数scale是否自动缩放，第二个参数能下拉的最大位置，越大下拉越长
        mSrlHistory.setProgressViewEndTarget(true, 230);
        mSrlHistory.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (historyOrCollect == 0) {
                    mIHistoryPresenter.getHistoryHousesInfoAndSetData(SpUtils.getInstance()
                            .getString(AtyLogin.SESSION_USERNAME, ""));
                } else {
                    mIHistoryPresenter.getCollectHousesInfoAndSetData(SpUtils.getInstance()
                            .getString(AtyLogin.SESSION_USERNAME, ""));
                }
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSrlHistory.setRefreshing(false);
                            }
                        });
                    }
                }, 1500);
            }
        });
    }

    private void initRv() {
        mRvHistory.setLayoutManager(new LinearLayoutManager(mContext));
        mRvHistory.setAdapter(mRvAllMealAdapter);

    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mTbHistory);
        setHasOptionsMenu(true);
        mTbHistory.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_history:
                        historyOrCollect = 0;
                        mIHistoryPresenter.getHistoryHousesInfoAndSetData(SpUtils.getInstance()
                                .getString(AtyLogin.SESSION_USERNAME, ""));
                        break;
                    case R.id.action_collect:
                        historyOrCollect = 1;
                        mIHistoryPresenter.getCollectHousesInfoAndSetData(SpUtils.getInstance()
                                .getString(AtyLogin.SESSION_USERNAME, ""));
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.history_menu, menu);
    }

    @Override
    public void setRvHistoryData(List<House> houses) {
        mHouses = houses;
        mRvAllMealAdapter.setData(mHouses);
        mRvAllMealAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRvCollectData(List<House> houses) {
        mHouses = houses;
        mRvAllMealAdapter.setData(mHouses);
        mRvAllMealAdapter.notifyDataSetChanged();
    }
}
