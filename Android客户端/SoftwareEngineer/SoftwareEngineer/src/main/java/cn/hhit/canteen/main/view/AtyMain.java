package cn.hhit.canteen.main.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import cn.hhit.canteen.R;
import cn.hhit.canteen.app.utils.LogUtil;
import cn.hhit.canteen.app.utils.ToastUtil;
import cn.hhit.canteen.main.presenter.IVersionCheckBiz;
import cn.hhit.canteen.main.presenter.VersionCheckBizImpl;
import cn.hhit.canteen.main.presenter.download.service.DownloadService;
import cn.hhit.canteen.page_history.view.FgHistory;
import cn.hhit.canteen.page_rentedhouse.view.FgRentedHouse;
import cn.hhit.canteen.page_user.view.FgUser;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class AtyMain extends AppCompatActivity implements IVersionCheckView {

    private List<Fragment> fragments;
    private NavigationController mNavigationController;
    private PageNavigationView pageBottomTabLayout;

    private Resources mResources;
    private IVersionCheckBiz mIVersionCheckBiz;
    int[] testColors = new int[]{-16753253, -16740718, -10794681, -689152};

    //add1111
    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (DownloadService.DownloadBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    //add1111

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AtyMain.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_main);
        mResources = getResources();
        mIVersionCheckBiz = new VersionCheckBizImpl(AtyMain.this, AtyMain.this);

        startDownloadService();
        checkUpdate();

        initView();
    }

    private void initView() {
        this.pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);
//        this.mNavigationview = (NavigationView) findViewById(R.id.navigationview);
//        mTbHome = mAtyMainBinding.tbHome;

        this.mNavigationController = this.pageBottomTabLayout.material().addItem((int) R.drawable
                .ic_sign_in_normal, (int) R.drawable.ic_sign_in_selected, "房源", this.testColors[1]).addItem((int) R.drawable
                .ic_history_normal, (int) R.drawable.ic_history_selected, "历史", this.testColors[1]).addItem((int) R.drawable
                .ic_user_info_normal, (int) R.drawable.ic_user_info_selected, mResources
                .getString(R.string.bottom_tag_user), this.testColors[1]).setDefaultColor
                (-1979711489).setMode(3).build();

//        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.fragments = new ArrayList();
        this.fragments.add(new FgRentedHouse(this));
        this.fragments.add(new FgHistory(this));
        this.fragments.add(new FgUser(this));
//        MyViewPagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),
//                this.mNavigationController.getItemCount(), this.fragments);
//        //设置当前page左右两侧应该被保持的page数量，超过这个限制，page会被销毁重建（只是销毁视图）
//        viewPager.setOffscreenPageLimit(2);
//        viewPager.setAdapter(pagerAdapter);
//        this.mNavigationController.setupWithViewPager(viewPager);

        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        final FragmentTransaction transaction = fm.beginTransaction();
        if (!fragments.get(0).isAdded()) {
            transaction.add(R.id.fl_content, fragments.get(0));
        }
        transaction.hide(fragments.get(1)).hide(fragments.get(2)).show(fragments.get(0));
        transaction.commit();
        this.mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            public void onSelected(int index, int old) {
                Log.i("asd", "selected: " + index + " old: " + old);
                if (index == 1) {
                    //number置为0就可以清除消息了
                    AtyMain.this.mNavigationController.setMessageNumber(1, 1);
                }
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                if (!fragments.get(index).isAdded()) {
                    transaction.add(R.id.fl_content, fragments.get(index));
                }
                transaction.hide(fragments.get(old)).show(fragments.get(index));
                transaction.commit();
            }

            public void onRepeat(int index) {
                Log.i("asd", "onRepeat selected: " + index);
            }
        });

    }


    /**
     * 监听列表的滑动来控制底部导航栏的显示与隐藏
     * recyclerView.addOnScrollListener(new ListScrollListener());
     */
//    private static class ListScrollListener extends RecyclerView.OnScrollListener{
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            if(dy > 8){//列表向上滑动
//                mNavigationController.hideBottomLayout();
//            } else if(dy < -8){//列表向下滑动
//                mNavigationController.showBottomLayout();
//            }
//        }
//    }
    private void startDownloadService() {
        //add
        Intent intent = new Intent(AtyMain.this, DownloadService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
        //add
    }

    private void checkUpdate() {
        mIVersionCheckBiz.checkVersion();
    }

    @Override
    public void showUpdateDialog(String updateVersionName, String updateVersionDescription) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AtyMain.this);
        builder.setTitle(mResources.getString(R.string.find_new_version) + updateVersionName);
        builder.setMessage(mResources.getString(R.string.version_update_des) +
                updateVersionDescription);
        builder.setPositiveButton(mResources.getString(R.string.dialog_btn_update), new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIVersionCheckBiz.checkNet();

            }
        });
        builder.setNegativeButton(mResources.getString(R.string.dialog_btn_cancel), new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void startDownload(String downloadUrl) {
        if (downloadBinder == null) {
            LogUtil.e("downloadBindeer == null");
            return;
        }
        downloadBinder.startDownload(downloadUrl);
    }

    @Override
    public void promptNoNetwork() {
        ToastUtil.showShort(AtyMain.this, "当前网络不可用");
    }

    @Override
    public void showConfirmDownloadWithNoWiFiDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AtyMain.this);
        builder.setTitle(mResources.getString(R.string.no_wifi_prompt_title));
        builder.setMessage(mResources.getString(R.string.no_wifi_prompt_message));
        builder.setPositiveButton(mResources.getString(R.string.no_wifi_prompt_confirm), new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mIVersionCheckBiz.startDownload();
            }
        });
        builder.setNegativeButton(mResources.getString(R.string.no_wifi_prompt_cancel), new
                DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /**
     * 1.绑定服务后需要在onDestory中解绑
     * 2.服务需要在manifest中注册！！！
     * 3.DownloadService最后选择要安装的apk，根据需要肯定会改，这里是canteen.apk
     * 4.下载的位置和安装访问本地文件的位置需要对应起来
     * 5.没有文件夹先创建一个文件夹！
     * 6.android N 访问并安装apk本地文件适配。
     * Uri contentUri = FileProvider.getUriForFile
     * (DownloadService.this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
     * 这句的fileProvider和manifest中的authority属性一致
     * 7.适配android_O(android 8.0)通知栏设置channel
     * 8.适配android_O安装应用权限，只需要在manifest中添加请求install权限就好了，会在设置中打开请求权限，不需要动态申请（申请也没用）
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
