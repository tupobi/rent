package cn.hhit.canteen.main.view;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public interface IVersionCheckView {
    void showUpdateDialog(String updateVersionName, String updateVersionDescription);

    void startDownload(String downloadUrl);

    void promptNoNetwork();

    void showConfirmDownloadWithNoWiFiDialog();
}
