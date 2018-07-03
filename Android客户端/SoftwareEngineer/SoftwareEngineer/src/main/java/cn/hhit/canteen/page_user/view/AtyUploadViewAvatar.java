package cn.hhit.canteen.page_user.view;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.SpUtils;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.bean.MessageEvent;
import cn.hhit.canteen.login.view.AtyLogin;
import cn.hhit.canteen.page_user.presenter.IUserInfoPresenter;
import cn.hhit.canteen.page_user.presenter.UserInfoPresenterImpl;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AtyUploadViewAvatar extends AppCompatActivity implements IUserInfoUploadView {

    public static final String UPDATE_AVATAR = "update avatar";
    private List<LocalMedia> mLocalMediaList = new ArrayList<>();
    private IUserInfoPresenter mIUserInfoPresenter;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyUploadViewAvatar.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_upload_avatar);
        mIUserInfoPresenter = new UserInfoPresenterImpl(this, this);

        PictureSelector.create(AtyUploadViewAvatar.this).openGallery(PictureMimeType.ofImage())// 全部
                // .PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_selector_default_style)// 主题样式设置 具体参考 values/styles
                // 用法：R.style.picture.white.style
                .maxSelectNum(6)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(2 == 1 ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)// 多选
                // or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.setOutputCameraPath(Environment.getExternalStorageDirectory() +
                // "/183/TestPicSelector/customPic")// 自定义拍照保存路径
                //直接写/后面的，不用写Environment.getExternalStorageDirectory()，不然会在根目录创建一个storage目录..
                .setOutputCameraPath("/1rent/pic/luban_cameraPic").enableCrop(true)// 是否裁剪
                .freeStyleCropEnabled(true).circleDimmedLayer(true)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .hideBottomControls(false)//是否显示ucrop菜单栏，默认不显示，单选才有效
                .compress(false)// 是否压缩
//                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //如果该目录不存在会报错，所以首先创建该目录
//                        .compressSavePath(Environment.getExternalStorageDirectory() +
//                                "/183/TestPicSelector/customCompressedPic")//压缩图片保存地址
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .openClickSound(true)// 是否开启点击声音
                .selectionMedia(mLocalMediaList)// 是否传入已选图片
                .minimumCompressSize(200)// 小于200kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

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


                    File compressedDir = new File(Environment.getExternalStorageDirectory() +
                            "/1rent/pic/luban_compressedPic");
                    if (!compressedDir.exists()) {
                        LogUtil.e("创建目录 == " + compressedDir.getAbsolutePath());
                        compressedDir.mkdirs();
                    }
                    compressPic(mLocalMediaList.get(0).getCutPath(), compressedDir
                            .getAbsolutePath());
                    break;
            }
        }
        //不管什么情况，临时回退，拍照回退，成功保存等，都完结这个Activity.
        finish();
    }

    private void uploadAvatar(String avatarFilePath) {
        //先上传文件
        mIUserInfoPresenter.uploadAvatarPicture(avatarFilePath, SpUtils.getInstance().getString
                (AtyLogin.SESSION_USERNAME, "null"));
    }

    private void compressPic(String compressFrom, final String compressTo) {
        Luban.with(this).load(compressFrom)                               // 传人要压缩的图片列表
                .ignoreBy(100)                                              // 忽略不压缩图片的大小
                .setTargetDir(compressTo)                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        LogUtil.e("开始图片压缩，这里可以启动loading UI");
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        LogUtil.e("压缩成功，返回压缩后图片文件，文件大小：" + file.length() / 1024 + "KB");
                        File oldAvatarFile = new File(compressTo, "compressAvatar.jpg");
                        if (oldAvatarFile.exists()) {
                            oldAvatarFile.delete();
                        }
                        if (file.renameTo(new File(compressTo, "compressAvatar.jpg"))) {
                            LogUtil.e("压缩位置：" + compressTo + "/compressAvatar.jpg");
                            uploadAvatar(compressTo + "/compressAvatar.jpg");
                        } else {
                            ToastUtil.showShort(AtyUploadViewAvatar.this, "图片保存失败！");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        LogUtil.e("压缩出现问题：" + e.getMessage());
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void startUpload() {
        LogUtil.e("start upload" + System.currentTimeMillis());
    }

    @Override
    public void stopUpload() {
        LogUtil.e("stop upload" + System.currentTimeMillis());
    }

    @Override
    public void updateAvatar() {
        LogUtil.e("该发送消息通知更新头像了！" + System.currentTimeMillis());
        MessageEvent messageEvent = new MessageEvent(UPDATE_AVATAR);
        EventBus.getDefault().post(messageEvent);
    }
}
