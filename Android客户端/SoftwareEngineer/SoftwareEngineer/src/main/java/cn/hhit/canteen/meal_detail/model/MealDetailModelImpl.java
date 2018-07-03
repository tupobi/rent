package cn.hhit.canteen.meal_detail.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.app.utils.http.HttpConfig;
import cn.hhit.canteen.app.utils.http.RetrofitFactory;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/7/1/001.
 */

public class MealDetailModelImpl implements IMealDetailModel {
    @Override
    public void insertHistory(String userName, String houseName, final InsertHistoryCallback
            insertHistoryCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);
        queryOptions.put("houseName", houseName);

        RetrofitFactory.getInstence().API().addHistory(HttpConfig.ADD_HISTORY, queryOptions)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<StringResponse>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse stringResponse) {
                insertHistoryCallback.onSuccess(stringResponse);
            }

            @Override
            public void onError(Throwable e) {
                insertHistoryCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void addCollect(String userName, String houseName, final InsertHistoryCallback
            insertHistoryCallback) {
        Map<String, String> queryOptions = new HashMap<>();
        queryOptions.put("userName", userName);
        queryOptions.put("houseName", houseName);

        RetrofitFactory.getInstence().API().addCollect(HttpConfig.ADD_COLLECT, queryOptions)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<StringResponse>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse stringResponse) {
                insertHistoryCallback.onSuccess(stringResponse);
            }

            @Override
            public void onError(Throwable e) {
                insertHistoryCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void addComment(Comment comment, final InsertHistoryCallback insertHistoryCallback) {
        Map<String, String> fields = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        fields.put("userName", comment.getUserName());
        fields.put("houseName", comment.getHouseName());
        fields.put("content", comment.getContent());
        fields.put("date", date);
        fields.put("avatarUrl", comment.getAvatarUrl());
        RetrofitFactory.getInstence().API().addComment(HttpConfig.ADD_COMMENT, fields)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe
                (new Observer<StringResponse>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(StringResponse stringResponse) {
                insertHistoryCallback.onSuccess(stringResponse);
            }

            @Override
            public void onError(Throwable e) {
                insertHistoryCallback.onFailed(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
