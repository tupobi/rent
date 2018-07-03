package cn.hhit.canteen.meal_detail.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/3/003.
 */

public class CommentModelImpl implements ICommentModel {
    @Override
    public void getCommentsByHouseName(String houseName, final GetCommentsByHouseNameCallback
            getCommentsByHouseNameCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("houseName", houseName);
        RetrofitFactory.getInstence().API().getComments(HttpConfig.GET_COMMENTS, queryOptions)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<List<Comment>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Comment> comments) {
                getCommentsByHouseNameCallback.onSuccess(comments);
            }

            @Override
            public void onError(Throwable e) {
                getCommentsByHouseNameCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
