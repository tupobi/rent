package cn.hhit.canteen.house_manage.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.house_manage.presenter.AddHousePresenterImpl;
import cn.hhit.canteen.house_manage.presenter.IAddHousePresenter;
import cn.hhit.canteen.house_manage.view.adapter.RvSelectPicAdapter;
import cn.hhit.canteen.login.view.AtyLogin;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AtyAddHouse extends AppCompatActivity implements IAddHouseView {

    @BindView(R.id.tb_add_house)
    Toolbar mTbAddHouse;
    @BindView(R.id.et_house_name)
    EditText mEtHouseName;
    @BindView(R.id.et_house_city)
    EditText mEtHouseCity;
    @BindView(R.id.et_house_area)
    EditText mEtHouseArea;
    @BindView(R.id.sp_type)
    Spinner mSpType;
    @BindView(R.id.et_house_title)
    EditText mEtHouseTitle;
    @BindView(R.id.et_house_price)
    EditText mEtHousePrice;
    @BindView(R.id.et_owner_phone)
    EditText mEtOwnerPhone;
    @BindView(R.id.et_house_location)
    EditText mEtHouseLocation;
    @BindView(R.id.et_house_description)
    EditText mEtHouseDescription;
    @BindView(R.id.rv_select_pic)
    RecyclerView mRvSelectPic;
    @BindView(R.id.btn_upload_house)
    Button mBtnUploadHouse;
    @BindView(R.id.pb_uploading)
    ProgressBar mPbUploading;
    @BindView(R.id.tv_uploading)
    TextView mTvUploading;

    private GridLayoutManager mGridLayoutManager;
    private RvSelectPicAdapter mRvSelectPicAdapter;
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private List<String> mCompressedPics = new ArrayList<>();
    private IAddHousePresenter mIAddHousePresenter;
    private Map<String, String> mFieldMap = new HashMap<>();
    private List<String> mOriginalPicturesList = new ArrayList<>();

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyAddHouse.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.aty_add_house);
        ButterKnife.bind(this);
        mIAddHousePresenter = new AddHousePresenterImpl(this, this);

        initToolbar();
        initRecyclerView();
        initPicSelector();
    }

    private void initPicSelector() {
    }

    private void initRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRvSelectPic.setLayoutManager(mGridLayoutManager);
        mRvSelectPicAdapter = new RvSelectPicAdapter(this);
        mRvSelectPic.setAdapter(mRvSelectPicAdapter);
        mRvSelectPicAdapter.setOnItemClickListener(new RvSelectPicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mLocalMediaList.size() > 0) {
                    LocalMedia media = mLocalMediaList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
//                            PictureSelector.create(MainActivity.this).themeStyle(R.style
// .picture_default_style).externalPicturePreview(position, "/custom_file", mLocalMediaList);
                            PictureSelector.create(AtyAddHouse.this).themeStyle(R.style
                                    .picture_selector_default_style).openExternalPreview
                                    (position, mLocalMediaList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AtyAddHouse.this).externalPictureVideo(media
                                    .getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AtyAddHouse.this).externalPictureAudio(media
                                    .getPath());
                            break;
                    }
                }
            }
        });
        mRvSelectPicAdapter.setOnAddPicClickListener(mOnAddPicClickListener);
    }

    private void initToolbar() {
        setSupportActionBar(mTbAddHouse);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mTbAddHouse.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_upload_house)
    public void onViewClicked() {
        String houseName = mEtHouseName.getText().toString().trim();
        String houseCity = mEtHouseCity.getText().toString().trim();
        String houseArea = mEtHouseArea.getText().toString().trim();
        String houseTitle = mEtHouseTitle.getText().toString().trim();
        int monthPrice;
        try {
            monthPrice = Integer.valueOf(mEtHousePrice.getText().toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showShort(AtyAddHouse.this, "月租应当为整数");
            return;
        }
        String houseDescription = mEtHouseDescription.getText().toString().trim();
        String houseLocation = mEtHouseLocation.getText().toString().trim();
        String ownerPhone = mEtOwnerPhone.getText().toString().trim();

        if (houseName.isEmpty() || houseCity.isEmpty() || houseArea.isEmpty() || houseTitle
                .isEmpty() || houseDescription.isEmpty() || houseLocation.isEmpty() || ownerPhone
                .isEmpty()) {
            ToastUtil.showShort(this, "输入不能为空");
            return;
        }
        String houseTyle = (String) mSpType.getSelectedItem();

        if (mLocalMediaList.size() == 0) {
            ToastUtil.showShort(this, "至少上传一张图片");
        }
        //下面是图片上传的目录
        File compressedDir = new File(Environment.getExternalStorageDirectory() +
                "/1rent/pic/luban_compressedPic");
        if (!compressedDir.exists()) {
            LogUtil.e("创建目录 == " + compressedDir.getAbsolutePath());
            compressedDir.mkdirs();
        }
        LogUtil.e("mLocalMediaList.size() == " + mLocalMediaList.size());
        for (int i = 0; i < mLocalMediaList.size(); i++) {
//            compressPic(mLocalMediaList.get(i).getCutPath(), compressedDir.getAbsolutePath());
            mOriginalPicturesList.add(mLocalMediaList.get(i).getCutPath());
        }

        mFieldMap.put("houseName", houseName);
        mFieldMap.put("houseCity", houseCity);
        mFieldMap.put("houseArea", houseArea);
        mFieldMap.put("houseTitle", houseTitle);
        mFieldMap.put("monthPrice", String.valueOf(monthPrice));
        mFieldMap.put("houseDescription", houseDescription);
        mFieldMap.put("houseLocation", houseLocation);
        mFieldMap.put("ownerPhone", ownerPhone);
        mFieldMap.put("houseTyle", houseTyle);
        mFieldMap.put("userName", SpUtils.getInstance().getString(AtyLogin.SESSION_USERNAME,
                "null"));

        compressPicturesList(mOriginalPicturesList, compressedDir.getAbsolutePath());
    }

    private void compressPicturesList(List<String> originalPicturesList, final String compressTo) {
        try {
            startProgress();
            Flowable.just(originalPicturesList).observeOn(Schedulers.io()).map(new Function<List<String>, List<File>>() {
                @Override
                public List<File> apply(@NonNull List<String> list) throws Exception {
                    // 同步方法直接返回压缩后的文件
                    return Luban.with(AtyAddHouse.this).load(list).ignoreBy(100)
                            // 忽略不压缩图片的大小
                            .setTargetDir(compressTo).get();
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<File>>() {
                @Override
                public void accept(List<File> files) throws Exception {
                    for (int i=0; i<files.size(); i++) {
                        LogUtil.e("第" + i + "张被压缩要上传的图片：" + files.get(i).getAbsolutePath());
                        mCompressedPics.add(files.get(i).getAbsolutePath());
                    }
                    LogUtil.e("mFieldMap == " + mFieldMap.toString() + "fileList == " + mCompressedPics
                            .toString());
                    mIAddHousePresenter.uploadHouseInfo(mFieldMap, mCompressedPics);
                    mCompressedPics.clear();
                    mFieldMap.clear();
                    mOriginalPicturesList.clear();
                    stopProgress();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            stopProgress();
        }
    }

    /**
     * 鲁班压缩在这个库中的版本过时了，建议手动用鲁班压缩
     */
    private RvSelectPicAdapter.OnAddPicClickListener mOnAddPicClickListener = new
            RvSelectPicAdapter.OnAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            //更多方法见gayhub，但是不建议使用自带的压缩方法，还是在onActivityResult中得到图片路径后手动用鲁班压缩
            PictureSelector.create(AtyAddHouse.this).openGallery(PictureMimeType.ofImage())// 全部
                    // .PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles
                    // 用法：R.style.picture.white.style
                    .maxSelectNum(6)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(1 == 1 ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选
                    // or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.setOutputCameraPath(Environment.getExternalStorageDirectory() +
                    // "/183/TestPicSelector/customPic")// 自定义拍照保存路径
                    //直接写/后面的，不用写Environment.getExternalStorageDirectory()，不然会在根目录创建一个storage目录..
                    //不用手动创建目录，这个会自动创建目录，而压缩存储的目录不会自动创建，需要手动创建
                    .setOutputCameraPath("/1rent/pic/luban_cameraPic").enableCrop(true)// 是否裁剪
                    .freeStyleCropEnabled(true).circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .isDragFrame(true)// 是否可拖动裁剪框(固定)
                    .hideBottomControls(false)//是否显示ucrop菜单栏，默认不显示，单选才有效
                    .compress(false)// 是否压缩
//                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //如果该目录不存在会报错，所以首先创建该目录
//                    .compressSavePath(Environment.getExternalStorageDirectory() +
//                            "/183/TestPicSelector/customCompressedPic")//压缩图片保存地址
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(mLocalMediaList)// 是否传入已选图片
                    .minimumCompressSize(200)// 小于200kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mLocalMediaList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : mLocalMediaList) {
                        LogUtil.e("所有图片----->" + media.getPath());
                    }
                    LogUtil.e("原图path == " + mLocalMediaList.get(0).getPath());
                    LogUtil.e("裁剪后path == " + mLocalMediaList.get(0).getCutPath());//先裁剪后压缩
                    LogUtil.e("裁剪并压缩后的path == " + mLocalMediaList.get(0).getCompressPath());
                    mRvSelectPicAdapter.setLocalMediaList(mLocalMediaList);
                    mRvSelectPicAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void startProgress() {
        mPbUploading.setVisibility(View.VISIBLE);
        mTvUploading.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgress() {
        mPbUploading.setVisibility(View.GONE);
        mTvUploading.setVisibility(View.GONE);
    }
}
