package cn.hhit.canteen.page_user.model;

import java.io.File;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.app.utils.bean.StringResponse;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/6/24/024.
 */

public class UserInfoModelImpl implements IUserInfoModel {

    @Override
    public void updateAvatar(String avatarPath, String userName, final UploadAvatarCallback
            uploadAvatarCallback) {
        File avatar = new File(avatarPath);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/jpg"), avatar);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("photos", "icon.png",
                photoRequestBody);
        RetrofitFactory.getInstence().API().updateAvatar(HttpConfig.UPLOAD_AVATAR, photo,
                RequestBody.create(null, userName)).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Observer<StringResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse stringResponse) {
                uploadAvatarCallback.onSuccess(stringResponse);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                uploadAvatarCallback.onFailed();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
