package cn.hhit.canteen.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.login.view.adapter.RvFlavourAdapter;
import cn.hhit.canteen.login.view.helper.MyLayoutAnimationHelper;
import cn.hhit.canteen.login.view.helper.OnStartDragListener;
import cn.hhit.canteen.login.view.helper.SimpleItemTouchHelperCallback;
import cn.hhit.canteen.main.view.AtyMain;

public class AtyPreference extends AppCompatActivity implements OnStartDragListener {

    @BindView(R.id.btn_reset_flavour)
    Button mBtnResetFlavour;
    @BindView(R.id.btn_confirm_flavour)
    Button mBtnConfirmFlavour;
    @BindView(R.id.tb_flavour)
    Toolbar mTbFlavour;
    @BindView(R.id.rv_flavour)
    RecyclerView mRvFlavour;

    private List<String> mFlavourList = new ArrayList<>();
    private RvFlavourAdapter mRvFlavourAdapter;
    private ItemTouchHelper mItemTouchHelper;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyPreference.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_flavour);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        mFlavourList.clear();
        mFlavourList.add("采光好");
        mFlavourList.add("交通便利");
        mFlavourList.add("市中心");
        mFlavourList.add("社区环境优雅");
        mFlavourList.add("十层以下");
        mFlavourList.add("物业管理规范");
        mFlavourList.add("学区房");
        mFlavourList.add("有车库");
    }

    private void initView() {
        initTbFlavour();
        initRvFlavour();
    }

    private void initRvFlavour() {
        mRvFlavourAdapter = new RvFlavourAdapter(mFlavourList, AtyPreference.this, AtyPreference.this);
        mRvFlavour.setLayoutManager(new LinearLayoutManager(AtyPreference.this));
        mRvFlavour.setAdapter(mRvFlavourAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mRvFlavourAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRvFlavour);

        mRvFlavourAdapter.setOnItemClickListener(new RvFlavourAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mFlavourList = mRvFlavourAdapter.getmData();
                ToastUtil.showShort(AtyPreference.this, mFlavourList.get(position) + getString(R.string.flavour_priority_prompt) + (position + 1));
            }
        });

        playLayoutAnimation(MyLayoutAnimationHelper.getAnimationSetFromRight());
    }

    private void initTbFlavour() {
        setSupportActionBar(mTbFlavour);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @OnClick({R.id.btn_reset_flavour, R.id.btn_confirm_flavour})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_flavour:
                initData();
                mRvFlavourAdapter.setmData(mFlavourList);
                mRvFlavourAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_confirm_flavour:
                showConfirmFlavourSnackbar();

                break;
        }
    }

    private void showConfirmFlavourSnackbar() {
        Snackbar.make(mBtnConfirmFlavour, getString(R.string.flavour_snackbar_prompt), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.flavour_snackbar_confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AtyMain.actionStart(AtyPreference.this);
                        finish();
                    }
                })
                .show();
    }

    /**
     * 播放RecyclerView动画
     *
     * @param animation
     *
     */
    public void playLayoutAnimation(Animation animation) {
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(false ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);

        mRvFlavour.setLayoutAnimation(controller);
        mRvFlavour.getAdapter().notifyDataSetChanged();
        mRvFlavour.scheduleLayoutAnimation();
    }
}
