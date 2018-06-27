package cn.hhit.canteen.main.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.SettingService;

import java.util.List;

import cn.hhit.canteen.app.CanteenApplication;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.app.utils.network.NetworkUtil;
import cn.hhit.canteen.main.model.bean.AppVersion;
import cn.hhit.canteen.main.model.dao.IVersionCheckModel;
import cn.hhit.canteen.main.model.dao.VersionCheckModelImpl;
import cn.hhit.canteen.main.view.IVersionCheckView;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public class VersionCheckBizImpl implements IVersionCheckBiz {
    private IVersionCheckModel mIVersionCheckModel;
    private IVersionCheckView mIVersionCheckView;
    private String mDownloadUrl;
    private Context mContext;

    public VersionCheckBizImpl(IVersionCheckView IVersionCheckView, Context context) {
        this.mIVersionCheckView = IVersionCheckView;
        this.mContext = context;
        mIVersionCheckModel = new VersionCheckModelImpl();
    }

    @Override
    public void checkVersion() {
        mIVersionCheckModel.getLatestVersion(
                "canteen",
                new IVersionCheckModel.Callback() {
                    @Override
                    public void onSuccess(AppVersion latestAppVersion) {
                        LogUtil.e("success!");
                        if (latestAppVersion == null) {
                            //返回为空，可能是服务器异常
                            LogUtil.e("latestAppVerion == null");
                            return;
                        }
                        LogUtil.e("得到最新版本" + latestAppVersion.toString());
                        String latestVersionName = latestAppVersion.getVersionName();
                        String currentVersionName = CanteenApplication.getVersionName();
                        mDownloadUrl = latestAppVersion.getDownloadUrl();

                        String content = new String();
                        String[] strs = latestAppVersion.getDescription().split(";");
                        for (int i = 0; i < strs.length; i++) {
                            content += strs[i] + "\n";
                        }
                        if (!latestVersionName.equals(currentVersionName)) {
                            mIVersionCheckView.showUpdateDialog(
                                    latestAppVersion.getVersionName(),
                                    content
                            );
                        }
                    }

                    @Override
                    public void onFailed() {
                        //可能是网络错误，服务器异常等，但用户可以不需要知道
                        LogUtil.e("no result");
                        ToastUtil.showShort(mContext, "服务器异常");
                    }
                }
        );


    }

    @Override
    public void checkNet() {
        if (NetworkUtil.isConnected(CanteenApplication.getGlobalApplication()) && NetworkUtil.isAvailable(CanteenApplication.getGlobalApplication())) {
            //网络连接上并且可用
            if (NetworkUtil.isWifiConnected(CanteenApplication.getGlobalApplication())) {
                //是WiFi环境
                startDownload();
            } else {
                //非WiFi环境
                mIVersionCheckView.showConfirmDownloadWithNoWiFiDialog();
            }
        } else {
            //网络没连接或者网络不可用
            mIVersionCheckView.promptNoNetwork();
        }

    }

    @Override
    public void startDownload() {
        AndPermission.with(CanteenApplication.getGlobalApplication())
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        LogUtil.e("同意");
                        mIVersionCheckView.startDownload(mDownloadUrl);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(CanteenApplication.getGlobalApplication(), permissions)) {
                            // 这些权限被用户总是拒绝。
                            final SettingService settingService = AndPermission.permissionSetting(CanteenApplication.getGlobalApplication());

                            new AlertDialog.Builder(mContext)
                                    .setTitle("前往设置")
                                    .setMessage("是否前往设置？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // 如果用户同意去设置：
                                            settingService.execute();
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 如果用户不同意去设置：
                                    settingService.cancel();
                                }
                            }).show();

                        }
                    }
                })
                .start();
    }

    private Rationale mRationale = new Rationale() {
        @Override
        public void showRationale(Context context, List<String> permissions,
                                  final RequestExecutor executor) {
            // 这里使用一个Dialog询问用户是否继续授权。
            new AlertDialog.Builder(mContext)
                    .setTitle("下面我们将获取权限")
                    .setMessage("请求获取权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 如果用户继续：
                            executor.execute();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // 如果用户中断：
                    executor.cancel();
                }
            }).show();

        }
    };
}
