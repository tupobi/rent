package cn.hhit.canteen.meal_detail.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ImageView;

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
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.Config;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.meal_detail.view.adapter.TabLayoutAdapter;
import cn.hhit.canteen.meal_detail.view.listener.AppBarStateChangeListener;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealComment;
import cn.hhit.canteen.meal_detail.view.meal_detail_tabs.TabFgMealDetail;
import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.listener.TransitionListener;
import immortalz.me.library.method.InflateShowMethod;

public class AtyMealDetail extends AppCompatActivity {


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

    private boolean mIsFirstCollasping = true;
    private int mAppBarCollaspingState = 0;
    private String[] mTitles = new String[]{"详情", "热评"};
    private List<Fragment> mTabFragments = new ArrayList<>();


    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyMealDetail.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_meal_detail);
        ButterKnife.bind(this);

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
        mTabFragments.add(new TabFgMealDetail());
        mTabFragments.add(new TabFgMealComment());

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
        List<Integer> bannerImgs = new ArrayList<>();
        bannerImgs.add(R.drawable.meal1);
        bannerImgs.add(R.drawable.meal2);
        bannerImgs.add(R.drawable.meal3);
        bannerImgs.add(R.drawable.meal4);

        mBannerMealDetailTopPicture.setBannerAnimation(Transformer.ZoomOut);
        mBannerMealDetailTopPicture.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBannerMealDetailTopPicture.setIndicatorGravity(BannerConfig.CENTER);
        mBannerMealDetailTopPicture.setImages(bannerImgs);

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
                List<String> imgUrls = new ArrayList<>();
                imgUrls.add("http://img5.imgtn.bdimg.com/it/u=826278277,629944972&fm=27&gp=0.jpg");
                imgUrls.add("http://img0.imgtn.bdimg.com/it/u=1234429428,4233304918&fm=27&gp=0.jpg");
                imgUrls.add("http://img0.imgtn.bdimg.com/it/u=906037492,3221559314&fm=27&gp=0.jpg");
                imgUrls.add("http://img3.imgtn.bdimg.com/it/u=3142704261,1326815248&fm=200&gp=0.jpg");
                imgUrls.add("http://img5.imgtn.bdimg.com/it/u=508577620,943812775&fm=27&gp=0.jpg");

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

}
