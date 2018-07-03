package cn.hhit.canteen.page_rentedhouse.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.DensityUtil;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.network.GsonUtil;
import cn.hhit.canteen.diyview.RecyclerViewNestedScrollView;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.house_manage.view.AtyHouseManage;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.meal_detail.view.AtyMealDetail;
import cn.hhit.canteen.page_rentedhouse.presenter.IRentedHousePresenter;
import cn.hhit.canteen.page_rentedhouse.presenter.RentedHousePresenterImpl;
import cn.hhit.canteen.page_rentedhouse.view.adapter.ListDropDownAdapter;
import cn.hhit.canteen.page_rentedhouse.view.adapter.RvAllMealAdapter;
import cn.hhit.canteen.page_rentedhouse.view.adapter.RvGuessLoveAdapter;
import immortalz.me.library.TransitionsHeleper;

/**
 * Created by Administrator on 2018/4/22/022.
 */

@SuppressLint("ValidFragment")
public class FgRentedHouse extends Fragment implements IRentedHouseView {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_guess_love)
    RecyclerView mRvGuessLove;
    @BindView(R.id.rv_all_meal)
    RecyclerView mRvAllMeal;
    @BindView(R.id.tb_canteen)
    Toolbar mTbCanteen;
    Unbinder unbinder;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.srl_meal)
    SwipeRefreshLayout mSrlMeal;
    @BindView(R.id.ddm_all_meal_filter)
    DropDownMenu mDdmAllMealFilter;
    @BindView(R.id.nsv_canteen)
    RecyclerViewNestedScrollView mNsvCanteen;
    @BindView(R.id.ll_canteen)
    LinearLayout mLlCanteen;
    @BindView(R.id.ll_all_meal)
    LinearLayout mLlAllMeal;
    @BindView(R.id.ll_drop_down_menu_content)
    LinearLayout mLlDropDownMenuContent;
    @BindView(R.id.ll_menu_item_favorite)
    LinearLayout mLlMenuItemFavorite;
    @BindView(R.id.ll_menu_item_house_owner)
    LinearLayout mLlMenuItemHouseOwner;

    public static final String HOUSE_BUNDLE_KEY = "house";

    private View mRootView;//缓存Fragment view
    private Context mContext;
    private String headers[] = {"户型", "方式", "时长", "月租"};
    private String houseType[] = {"不限", "上下铺", "一室", "两室", "三室", "四室", "四室以上"};
    private String way[] = {"不限", "整套出粗", "单间出租"};
    private String duration[] = {"不限", "一个月", "三个月", "半年", "一年", "两年", "三年", "三年以上"};
    private String price[] = {"不限", "500以下", "500-1000", "1000-1500", "1500-2000", "2000-3000",
            "3000-4000", "4000以上"};
    private ListDropDownAdapter peroidAdapter, canteensAdapter, flavoursAdapter, priceAdapter;
    private IRentedHousePresenter mIRentedHousePresenter;

    public FgRentedHouse(Context context) {
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_rented_house, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mIRentedHousePresenter = new RentedHousePresenterImpl(this, mContext);

        initDropDownMenuAllMealFilter();
        initToolbar();
        initSwipeRefreshLayout();
        initDrawerlayout();
        mIRentedHousePresenter.getAllHousesInfo();
        return rootView;
    }

    private void initView() {
        initBanner();
        initRvGuessLove();
        initRvAllMeal();
    }

    private void initDropDownMenuAllMealFilter() {
        final ListView canteenView = new ListView(getActivity());
        canteensAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(houseType));
        canteenView.setDividerHeight(0);
        canteenView.setAdapter(canteensAdapter);

        //init age menu
        final ListView peroidView = new ListView(getActivity());
        peroidView.setDividerHeight(0);
        peroidAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(way));
        peroidView.setAdapter(peroidAdapter);

        //init sex menu
        final ListView flavoursView = new ListView(getActivity());
        flavoursView.setDividerHeight(0);
        flavoursAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(duration));
        flavoursView.setAdapter(flavoursAdapter);

        final ListView priceView = new ListView(getActivity());
        priceView.setDividerHeight(0);
        priceAdapter = new ListDropDownAdapter(getActivity(), Arrays.asList(price));
        priceView.setAdapter(priceAdapter);

        //init popupViews
        List<View> popupViews = new ArrayList<>();
//        popupViews.clear();
        popupViews.add(canteenView);
        popupViews.add(peroidView);
        popupViews.add(flavoursView);
        popupViews.add(priceView);

        //防止父控件消费滑动事件
        final float[] startY = {0};
        canteenView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(false);
                    float endY = motionEvent.getY();
                    LogUtil.e("startY == " + startY[0] + ", endY == " + endY);
                    if (startY[0] - endY > 0) {
//                        mNsvCanteen.scrollTo(0, mLlAllMeal.getTop());
                        scrollToPosition(0, mLlAllMeal.getTop(), mNsvCanteen);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件

                    startY[0] = motionEvent.getY();
                } else {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件
                }
                return false;
            }
        });

        peroidView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(false);
                    float endY = motionEvent.getY();
                    LogUtil.e("startY == " + startY[0] + ", endY == " + endY);
                    if (startY[0] - endY > 0) {
//                        mNsvCanteen.scrollTo(0, mLlAllMeal.getTop());
                        scrollToPosition(0, mLlAllMeal.getTop(), mNsvCanteen);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件

                    startY[0] = motionEvent.getY();
                } else {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件
                }
                return false;
            }
        });

        flavoursView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(false);
                    float endY = motionEvent.getY();
                    LogUtil.e("startY == " + startY[0] + ", endY == " + endY);
                    if (startY[0] - endY > 0) {
//                        mNsvCanteen.scrollTo(0, mLlAllMeal.getTop());
                        scrollToPosition(0, mLlAllMeal.getTop(), mNsvCanteen);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件

                    startY[0] = motionEvent.getY();
                } else {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件
                }
                return false;
            }
        });

        priceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(false);
                    float endY = motionEvent.getY();
                    LogUtil.e("startY == " + startY[0] + ", endY == " + endY);
                    if (startY[0] - endY > 0) {
//                        mNsvCanteen.scrollTo(0, mLlAllMeal.getTop());
                        scrollToPosition(0, mLlAllMeal.getTop(), mNsvCanteen);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件

                    startY[0] = motionEvent.getY();
                } else {
                    mNsvCanteen.requestDisallowInterceptTouchEvent(true);//屏蔽父控件的拦截事件
                }
                return false;
            }
        });
        //触摸外部关掉menu
        mNsvCanteen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mDdmAllMealFilter.closeMenu();
                return false;
            }
        });

        //add item click event
        canteenView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                canteensAdapter.setCheckItem(position);
                mDdmAllMealFilter.setTabText(position == 0 ? headers[0] : houseType[position]);
                mDdmAllMealFilter.closeMenu();
            }
        });

        peroidView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                peroidAdapter.setCheckItem(position);
                mDdmAllMealFilter.setTabText(position == 0 ? headers[1] : way[position]);
                mDdmAllMealFilter.closeMenu();
            }
        });

        flavoursView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flavoursAdapter.setCheckItem(position);
                mDdmAllMealFilter.setTabText(position == 0 ? headers[2] : duration[position]);
                mDdmAllMealFilter.closeMenu();
            }
        });

        priceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                priceAdapter.setCheckItem(position);
                mDdmAllMealFilter.setTabText(position == 0 ? headers[3] : price[position]);
                mDdmAllMealFilter.closeMenu();
            }
        });

        //init dropdownmenu content view
        mLlDropDownMenuContent.removeView(mRvAllMeal);
        //防止removeView后再添加子View会自动获取焦点
        mRvAllMeal.setFocusable(false);

        LogUtil.e("popViews.size（） == " + popupViews.size() + ", headers.length == " + headers
                .length);

        mDdmAllMealFilter.setDropDownMenu(Arrays.asList(headers), popupViews, mRvAllMeal);
        mRvAllMeal.setFocusable(true);
    }

    private void initSwipeRefreshLayout() {
        mSrlMeal.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color
                .holo_red_light, android.R.color.holo_orange_light, android.R.color
                .holo_green_light);
        //刷新的弹力，越大阻力越大
        mSrlMeal.setDistanceToTriggerSync(450);
        //第一个参数scale是否自动缩放，第二个参数能下拉的最大位置，越大下拉越长
        mSrlMeal.setProgressViewEndTarget(true, 230);
        mSrlMeal.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mIRentedHousePresenter.getAllHousesInfo();
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSrlMeal.setRefreshing(false);
                            }
                        });
                    }
                }, 1500);
            }
        });
    }

    //使scrollview平滑滚动
    private void scrollToPosition(int x, int y, NestedScrollView scrollViewContainer) {

        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollViewContainer, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollViewContainer, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(1000L);
        animators.playTogether(xTranslate, yTranslate);
        animators.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });
        animators.start();
    }

    private void initDrawerlayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                mDrawerLayout, mTbCanteen, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mTbCanteen);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private void initRvAllMeal() {
//        mRvAllMeal.setLayoutManager(new CustomLinearLayoutManager(mContext,
//                LinearLayoutManager.VERTICAL, false));
        final List<House> houses = GsonUtil.jsonList2BeanList(SpUtils.getInstance().getString
                (RentedHousePresenterImpl.CACHED_HOUSES_INFO, ""), House.class);
        if (houses == null) {
            return;
        }
        mRvAllMeal.setLayoutManager(new LinearLayoutManager(mContext));
//        List<String> rvAllMealImgs = new ArrayList<>();
//        for (int i = 0; i < houses.size(); i++) {
//            rvAllMealImgs.add(HttpConfig.PIC_BASE_URL + houses.get(i).getPic1Url());
//        }
        RvAllMealAdapter rvAllMealAdapter = new RvAllMealAdapter(mContext, houses);

        //设置item点击事件监听
        mRvAllMeal.setAdapter(rvAllMealAdapter);
        rvAllMealAdapter.setOnRvItemClickListener(new RvAllMealAdapter.OnRvItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, int position) {
                Intent intent = new Intent(mContext, AtyMealDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FgRentedHouse.HOUSE_BUNDLE_KEY, houses.get(position));
                intent.putExtras(bundle);
                TransitionsHeleper.startActivity(getActivity(), intent, itemView.findViewById(R
                        .id.iv_all_meal), HttpConfig.PIC_BASE_URL + houses.get(position)
                        .getPic1Url());
            }
        });
    }

    private void initRvGuessLove() {
        final List<House> houses = GsonUtil.jsonList2BeanList(SpUtils.getInstance().getString
                (RentedHousePresenterImpl.CACHED_HOUSES_INFO, ""), House.class);
        if (houses == null) {
            return;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false);
        mRvGuessLove.setLayoutManager(linearLayoutManager);
        List<String> rvGuessLoveImgs = new ArrayList<>();
        for (int i = 0; i < houses.size(); i++) {
            rvGuessLoveImgs.add(houses.get(i).getPic1Url());
        }
        RvGuessLoveAdapter rvGuessLoveAdapter = new RvGuessLoveAdapter(rvGuessLoveImgs, mContext);
        mRvGuessLove.setAdapter(rvGuessLoveAdapter);
        linearLayoutManager.scrollToPositionWithOffset(1, DensityUtil.dip2px(mContext, 87));

        //设置itemview的点击事件
        rvGuessLoveAdapter.setOnRvItemClickListener(new RvGuessLoveAdapter.OnRvItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, int position) {
                Intent intent = new Intent(mContext, AtyMealDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(FgRentedHouse.HOUSE_BUNDLE_KEY, houses.get(position));
                intent.putExtras(bundle);
                TransitionsHeleper.startActivity(getActivity(), intent, itemView.findViewById(R
                        .id.iv_guesslove_meal), HttpConfig.PIC_BASE_URL + houses.get(position)
                        .getPic1Url());
            }
        });
    }

    private void initBanner() {
        final List<House> houses = GsonUtil.jsonList2BeanList(SpUtils.getInstance().getString
                (RentedHousePresenterImpl.CACHED_HOUSES_INFO, ""), House.class);
        if (houses == null) {
            return;
        }
        LogUtil.e(houses.toString());

//        List<Integer> bannerImgs = new ArrayList<>();
//        bannerImgs.add(R.drawable.meal1);
//        bannerImgs.add(R.drawable.meal2);
//        bannerImgs.add(R.drawable.meal3);
//        bannerImgs.add(R.drawable.meal4);

        final List<String> bannerImgs = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (houses.size() < 5) {
            for (int i = houses.size() - 1; i >= 0; i--) {
                bannerImgs.add(HttpConfig.PIC_BASE_URL + houses.get(i).getPic1Url());
                titles.add(houses.get(i).getHouseTitle());
            }
        } else {
            for (int i = houses.size() - 1; i >= houses.size() - 5; i--) {
                bannerImgs.add(HttpConfig.PIC_BASE_URL + houses.get(i).getPic1Url());
                titles.add(houses.get(i).getHouseTitle());
            }
        }

//        List<String> titles = new ArrayList<>();
//        titles.add("三室一厅，特价出租啦！");
//        titles.add("情侣入住，房租减半，水电全免！");
//        titles.add("这是你从没有体验过的全新装修房~");
//        titles.add("限时特价，过了这个村就没有这个房啦！");

        mBanner.setBannerAnimation(Transformer.RotateDown);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setImages(bannerImgs);
        mBanner.setBannerTitles(titles);

        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });


        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                AtyMealDetail.actionStart(mContext);
                Intent intent = new Intent(mContext, AtyMealDetail.class);
                Bundle bundle = new Bundle();
                for (int i = 0; i < houses.size(); i++) {
                    if ((HttpConfig.PIC_BASE_URL + houses.get(i).getPic1Url()).equals(bannerImgs
                            .get(position))) {
                        bundle.putSerializable(FgRentedHouse.HOUSE_BUNDLE_KEY, houses.get(i));
                    }
                }
                intent.putExtras(bundle);
                TransitionsHeleper.startActivity(getActivity(), intent, mBanner, bannerImgs.get
                        (position));
            }
        });
        mBanner.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }

    @OnClick({R.id.ll_menu_item_favorite, R.id.ll_menu_item_house_owner})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_menu_item_favorite:
                break;
            case R.id.ll_menu_item_house_owner:
                if ("null".equals(SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                        "null"))) {
                    ToastUtil.showShort(mContext, "请先登录");
                    return;
                }
                mIRentedHousePresenter.isHouseOwner(SpUtils.getInstance().getString(AtyLogin
                        .SESSION_USERNAME, "null"));
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void startAtyHouseManage() {
        AtyHouseManage.actionStart(mContext);
    }

    @Override
    public void setData() {
        initView();
    }
}
