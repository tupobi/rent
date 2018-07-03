package cn.hhit.canteen.page_user.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.bean.MessageEvent;
import cn.hhit.canteen.app.utils.blurutil.BlurTransformation;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.diyview.ZoomInScrollView;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.page_user.model.bean.User;
import cn.hhit.canteen.page_user.presenter.IUserPresenter;
import cn.hhit.canteen.page_user.presenter.UserPresenterImpl;
import cn.hhit.canteen.page_user.view.adapter.RvUserSettingsAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/4/22/022.
 */

@SuppressLint("ValidFragment")
public class FgUser extends Fragment implements IUserView {

    @BindView(R.id.iv_avatar_blur_bg)
    ImageView mIvAvatarBlurBg;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    Unbinder unbinder;
    @BindView(R.id.rv_user_settings)
    RecyclerView mRvUserSettings;
    @BindView(R.id.rvnsv_user_settings)
    ZoomInScrollView mZoomInScrollView;
    @BindView(R.id.rl_user_header)
    RelativeLayout mRlUserHeader;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;

    private final Context mContext;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private IUserPresenter mIUserPresenter;

    public FgUser(Context context) {
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_user, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        mIUserPresenter = new UserPresenterImpl(this, getActivity());
        initRvUserSetting();
        mIUserPresenter.getUserInfo(SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                "null"));//会调用updateUserInfo()方法

        mCivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("null".equals(SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                        "null"))) {
                    ToastUtil.showShort(mContext, "尚未登录，无法修改头像！");
                } else {
                    AtyUploadViewAvatar.actionStart(getActivity());
                }
            }
        });
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (AtyUploadViewAvatar.UPDATE_AVATAR.equals(messageEvent.getMessage())) {
            mIUserPresenter.getUserInfo(SpUtils.getInstance().getString(AtyLogin
                    .SESSION_USERNAME, "null"));
        }
    }

    private void initRvUserSetting() {
        mRvUserSettings.setLayoutManager(new LinearLayoutManager(mContext));
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        RvUserSettingsAdapter rvUserSettingsAdapter = new RvUserSettingsAdapter(mContext, data);
        mRvUserSettings.setAdapter(rvUserSettingsAdapter);
        rvUserSettingsAdapter.setOnRvItemClickListener(new RvUserSettingsAdapter
                .OnRvItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, int position) {
                ToastUtil.showShort(mContext, "position == " + position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unbinder.unbind();
    }

    @Override
    public void updateUserInfo(User user) {
        if (user == null) {
            ToastUtil.showShort(mContext, "获取用户信息失败");
            return;
        }
        if (user.getAvatarUrl().isEmpty()) {
            LogUtil.e("获取到用户信息了，但是没有头像");

            //        “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error
                    (R.mipmap.ic_launcher).transform(new BlurTransformation(15, 4));

            Glide.with(this).load(R.drawable.avatar).apply(options)
//                .transition(new DrawableTransitionOptions().crossFade(2000))//淡入淡出
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable, @Nullable
                                Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                            mIvAvatarBlurBg.setImageBitmap(bitmap);

                            Palette.Builder builder = Palette.from(bitmap);
                            builder.generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {

                                    Palette.Swatch drakVibrantSwatch = palette
                                            .getDarkVibrantSwatch();//获取有活力 暗色的样本
                                    Palette.Swatch lightVibrantSwatch = palette
                                            .getLightVibrantSwatch();//获取有活力 亮色的样本
                                    //获取适合标题文字的颜色
                                    if (drakVibrantSwatch != null) {
                                        int titleTextColor = drakVibrantSwatch.getTitleTextColor();
                                        mTvNickname.setTextColor(titleTextColor);
                                    }
                                }
                            });
                        }
                    });

            Glide.with(this).load(R.drawable.avatar)
//                .transition(new DrawableTransitionOptions().crossFade(2000))//淡入淡出
                    .into(mCivAvatar);
        } else {
            LogUtil.e("有头像，获得头像URL == " + user.getAvatarUrl());
            LogUtil.e("真实头像URL == " + HttpConfig.PIC_BASE_URL + user.getAvatarUrl());

            //        “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
            RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error
                    (R.mipmap.ic_launcher).transform(new BlurTransformation(25, 4));

            Glide.with(this).load(HttpConfig.PIC_BASE_URL + user.getAvatarUrl()).apply(options)
//                .transition(new DrawableTransitionOptions().crossFade(2000))//淡入淡出
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable drawable, @Nullable
                                Transition<? super Drawable> transition) {
                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                            mIvAvatarBlurBg.setImageBitmap(bitmap);

                            Palette.Builder builder = Palette.from(bitmap);
                            builder.generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {

                                    Palette.Swatch drakVibrantSwatch = palette
                                            .getDarkVibrantSwatch();//获取有活力 暗色的样本
                                    Palette.Swatch lightVibrantSwatch = palette
                                            .getLightVibrantSwatch();//获取有活力 亮色的样本
                                    //获取适合标题文字的颜色
                                    if (drakVibrantSwatch != null) {
                                        int titleTextColor = drakVibrantSwatch.getTitleTextColor();
                                        mTvNickname.setTextColor(titleTextColor);
                                    }
//                                int titleTextColor = lightVibrantSwatch.getTitleTextColor();
                                }
                            });
                        }
                    });

            Glide.with(this).load(HttpConfig.PIC_BASE_URL + user.getAvatarUrl())
//                .transition(new DrawableTransitionOptions().crossFade(2000))//淡入淡出
                    .into(mCivAvatar);
        }
        mTvNickname.setText(user.getUserName());
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        AtyLogin.actionStart(mContext);
        SpUtils.getInstance().save(AtyLogin.SESSION_USERNAME, "");
        getActivity().finish();
    }
}
