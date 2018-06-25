package cn.hhit.canteen.main.presenter.download.listener;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
