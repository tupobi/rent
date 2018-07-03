package cn.hhit.canteen.meal_detail.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.clans.fab.FloatingActionMenu;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.Config;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.meal_detail.presenter.IMealDetailPresenter;
import cn.hhit.canteen.meal_detail.presenter.MealDetailPresenterImpl;
import cn.hhit.canteen.meal_detail.view.adapter.TabLayoutAdapter;
import cn.hhit.canteen.meal_detail.view.listener.AppBarStateChangeListener;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealComment;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealDetail;
import cn.hhit.canteen.page_rentedhouse.view.FgRentedHouse;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.listener.TransitionListener;
import immortalz.me.library.method.InflateShowMethod;

public class AtyMealDetail extends AppCompatActivity implements IMealDetailView{

    @BindView(R.id.tb_meal_detail)
    Toolbar mTbMealDetail;
    @BindView(R.id.ctb_meal_detail)
    CollapsingToolbarLayout mCtbMealDetail;
    @BindView(R.id.abl_meal_detail)
    AppBarLayout mAblMealDetail;
    @BindView(R.id.fab_meal_detail)
    FloatingActionButton mFabMealDetail;
    @BindView(R.id.fam_meal_detail_thumb_and_comment)
    FloatingActionMenu mFamMealDetailThumbAndComment;
    @BindView(R.id.banner_meal_detail_top_picture)
    Banner mBannerMealDetailTopPicture;
    @BindView(R.id.tbl_meal_detail)
    TabLayout mTblMealDetail;
    @BindView(R.id.vp_meal_detail)
    ViewPager mVpMealDetail;
    @BindView(R.id.nsv_meal_detail)
    NestedScrollView mNsvMealDetail;
    @BindView(R.id.cl_meal_detail)
    CoordinatorLayout mClMealDetail;
    @BindView(R.id.fab_comment)
    com.github.clans.fab.FloatingActionButton mFabComment;
    @BindView(R.id.fab_thumb)
    com.github.clans.fab.FloatingActionButton mFabThumb;
    @BindView(R.id.fab_rent)
    com.github.clans.fab.FloatingActionButton mFabRent;

    private boolean mIsFirstCollasping = true;
    private int mAppBarCollaspingState = 0;
    private String[] mTitles = new String[]{"详情", "热评"};
    private List<Fragment> mTabFragments = new ArrayList<>();
    private House mHouse;
    private IMealDetailPresenter mIMealDetailPresenter;


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyMealDetail.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_meal_detail);
        ButterKnife.bind(this);
        mIMealDetailPresenter = new MealDetailPresenterImpl(this, this);

        House house = (House) getIntent().getSerializableExtra(FgRentedHouse.HOUSE_BUNDLE_KEY);
        mHouse = house;
        LogUtil.e("mHouse == " + mHouse.toString());
        LogUtil.e("houseName == " + mHouse.getHouseName());

        mIMealDetailPresenter.addHistory(SpUtils.getInstance().getString(AtyLogin
                .SESSION_USERNAME, ""), mHouse.getHouseName());

        initToolbarMealDetail();
        initAnimation();
        initFam();
        initViewPagerMealDetail();
        initCollaspingLayout();
    }

    private void initCollaspingLayout() {
        mClMealDetail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtil.e("滑动了！");
                return false;
            }
        });
    }

    private void initViewPagerMealDetail() {
        mTabFragments.add(new TabFgMealDetail(mHouse));
        mTabFragments.add(new TabFgMealComment(mHouse));

        TabLayoutAdapter tabAdapter = new TabLayoutAdapter(AtyMealDetail.this,
                getSupportFragmentManager(), mTabFragments, mTitles);
        mVpMealDetail.setAdapter(tabAdapter);
        mTblMealDetail.setupWithViewPager(mVpMealDetail);
    }

    private void initFam() {
        mFamMealDetailThumbAndComment.setClosedOnTouchOutside(true);
    }

    private void initToolbarMealDetail() {
        setSupportActionBar(mTbMealDetail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTbMealDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //在xml中定义了title属性且赋值才可以在代码中设置title
        mTbMealDetail.setTitle("房源信息");

        //设置ToolbarLayout是否折叠的监听
        mAblMealDetail.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    LogUtil.e("展开");
                    mAppBarCollaspingState = 0;
                    //展开状态
                    if (mIsFirstCollasping) {
                        // do nothing cause mFamMealDetailThumbAndComment is hide
                    } else {
                        mFamMealDetailThumbAndComment.hideMenuButton(true);
                    }
                    mIsFirstCollasping = false;
//                    mTblMealDetail.setBackgroundColor(Color.parseColor("#FFFAFA"));

                } else if (state == State.COLLAPSED) {
                    LogUtil.e("折叠");
                    mAppBarCollaspingState = 2;
                    //折叠状态
                    mFamMealDetailThumbAndComment.showMenuButton(true);
//                    mTblMealDetail.setBackgroundColor(getResources().getColor(R.color
// .colorPrimary));

                } else {
                    LogUtil.e("中间");
                    mAppBarCollaspingState = 1;
                    //中间状态
                    if (mIsFirstCollasping) {
                        // do nothing cause mFamMealDetailThumbAndComment is hide
                    } else {
                        mFamMealDetailThumbAndComment.hideMenuButton(true);
                    }
                    mIsFirstCollasping = false;

                }
            }
        });
    }

    private void initAnimation() {
        int mutedColor = Palette.from(BitmapFactory.decodeResource(getResources(), R.drawable
                .meal1)).generate().getMutedColor(Color.parseColor("#4CAf50"));
        //ColorShowMethod替换成原来的布局，这样不会有一个视差间隔，感觉像卡了一样，中间过度的那个颜色不好调，直接用布局替代
        //但是注意，布局不能有内容，否则会提前显示。或者写一个一样的布局，没有可动态显示的控件（图片，文字等）而已
        TransitionsHeleper.build(this).setShowMethod(new InflateShowMethod(this, R.layout
                .aty_meal_detail) {
            @Override
            public void loadPlaceholder(InfoBean bean, ImageView placeholder) {
                RequestOptions options = new RequestOptions().centerCrop();
                //设置centerCrop()否则图片有边距，和要跳转的banner或者imageview一致
                //移动的过程显示
                Glide.with(AtyMealDetail.this).load(bean.getLoad()).apply(options).into
                        (placeholder);
            }

            @Override
            public void loadTargetView(InfoBean bean, View targetView) {
//                        Glide.with(AtyMealDetail.this)
//                                .load(bean.getLoad())
//                                .into((ImageView) targetView);
//                        targetView.setBackgroundResource((Integer) bean.getLoad());
//                        Glide.with(AtyMealDetail.this)
//                                .load(bean.getLoad())
//                                .into(mIvMealDetail);
                //设置banner
                initTopBanner();
//                        Palette palette = Palette.from(BitmapFactory.decodeResource
// (getResources(),
// R.drawable.meal1)).generate();
//                        palette.getVibrantColor(Color.parseColor("#4CAf50"));
            }
        })
//                .setExposeColor(getResources().getColor(R.color.colorAccent))
//                .setExposeColor(Color.parseColor("#4CAf50"))
                .setExposeColor(mutedColor).intoTargetView(mAblMealDetail).setTransitionDuration
                (500).setExposeAcceleration(15).setTransitionListener(new TransitionListener() {
            @Override
            public void onExposeStart() {

            }

            @Override
            public void onExposeProgress(float progress) {

            }

            @Override
            public void onExposeEnd() {
                mTbMealDetail.setVisibility(View.VISIBLE);
                mFabMealDetail.setVisibility(View.VISIBLE);
                mFamMealDetailThumbAndComment.setVisibility(View.VISIBLE);
                mFamMealDetailThumbAndComment.hideMenuButton(false);
            }
        }).show();

    }

    private void initTopBanner() {
        final List<String> imgUrls = new ArrayList<>();
        if (mHouse.getPic1Url() != null && !mHouse.getPic1Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic1Url());
        }
        if (mHouse.getPic2Url() != null && !mHouse.getPic2Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic2Url());
        }
        if (mHouse.getPic3Url() != null && !mHouse.getPic3Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic3Url());
        }
        if (mHouse.getPic4Url() != null && !mHouse.getPic4Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic4Url());
        }
        if (mHouse.getPic5Url() != null && !mHouse.getPic5Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic5Url());
        }
        if (mHouse.getPic6Url() != null && !mHouse.getPic6Url().isEmpty()) {
            imgUrls.add(HttpConfig.PIC_BASE_URL + mHouse.getPic6Url());
        }

        mBannerMealDetailTopPicture.setBannerAnimation(Transformer.ZoomOut);
        mBannerMealDetailTopPicture.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBannerMealDetailTopPicture.setIndicatorGravity(BannerConfig.CENTER);
        mBannerMealDetailTopPicture.setImages(imgUrls);

        mBannerMealDetailTopPicture.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });

        mBannerMealDetailTopPicture.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //这句不能少，否则对话框出不来
                getDeviceDensity();


                new ImageBrowserDialog(AtyMealDetail.this, imgUrls, position).show();
            }
        });
        mBannerMealDetailTopPicture.start();
    }

    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBannerMealDetailTopPicture.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBannerMealDetailTopPicture.stopAutoPlay();
    }

    @OnClick({R.id.fab_meal_detail, R.id.fab_comment, R.id.fab_thumb, R.id.fab_rent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_meal_detail:
                mIMealDetailPresenter.addCollect(SpUtils.getInstance().getString(AtyLogin
                        .SESSION_USERNAME, ""), mHouse.getHouseName());
                break;
            case R.id.fab_comment:
                View dialogView = getLayoutInflater().inflate(R.layout.comment_dialog_view, null);
                final EditText editText = (EditText) dialogView.findViewById(R.id.dialog_edit);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setIcon(R.mipmap.icon)//设置标题的图片
                        .setTitle("请输入评论内容：")//设置对话框的标题
                        .setView(dialogView)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = editText.getText().toString().trim();
                                mIMealDetailPresenter.addCommentAndShowResult(content, mHouse.getHouseName());
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                mFamMealDetailThumbAndComment.close(true);
                break;
            case R.id.fab_thumb:
                ToastUtil.showShort(AtyMealDetail.this, "点赞");
                mFamMealDetailThumbAndComment.close(true);
                break;
            case R.id.fab_rent:
                ToastUtil.showShort(AtyMealDetail.this, "联系房东租房");
                diallPhone("123456");
                mFamMealDetailThumbAndComment.close(true);
                break;
        }
    }

    public void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
