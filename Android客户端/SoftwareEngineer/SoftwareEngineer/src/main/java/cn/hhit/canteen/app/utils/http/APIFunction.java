package cn.hhit.canteen.app.utils.http;

import java.util.List;
import java.util.Map;

import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.house_manage.model.bean.House;
import cn.hhit.canteen.meal_detail.model.bean.Comment;
import cn.hhit.canteen.page_user.model.bean.User;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public interface APIFunction {
    @POST("{register}")
    Observable<StringResponse> register(@Path("register") String path, @QueryMap Map<String,
            String> queryOptions);

    @POST("{login}")
    Observable<StringResponse> login(@Path("login") String path, @QueryMap Map<String, String>
            queryOptions);

    @Multipart
    @POST("{upload}")
    Observable<StringResponse> updateAvatar(@Path("upload") String path, @Part MultipartBody.Part
            photo, @Part("userName") RequestBody userName);

    @GET("{user_info}")
    Observable<User> getAvatarUrl(@Path("user_info") String path, @QueryMap Map<String, String>
            queryOptions);

//    @Multipart
//    @POST("{upload_house_info}")
//    Observable<StringResponse> uploadHouseInfo(@Path("upload_house_info") String path, @QueryMap()
//            Map<String, String> params, @PartMap() Map<String, RequestBody> files);

    @Multipart
    @POST("{upload_house_info}")
    Observable<StringResponse> uploadHouseInfo(@Path("upload_house_info") String path, @PartMap()
            Map<String, RequestBody> files);

    @GET("{get_all_houses_info}")
    Observable<List<House>> getAllHousesInfi(@Path("get_all_houses_info") String path);

    @GET("{get_house_by_username}")
    Observable<List<House>> getHouseByUsername(@Path("get_house_by_username") String path,
                                               @QueryMap Map<String, String> queryOptions);

    @FormUrlEncoded
    @POST("{add_history}")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        //添加
    Observable<StringResponse> addHistory(@Path("add_history") String path, @FieldMap Map<String,
            String> names);

    @FormUrlEncoded
    @POST("{add_collect}")
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
        //添加
    Observable<StringResponse> addCollect(@Path("add_collect") String path, @FieldMap Map<String,
            String> names);

    @GET("{get_houses_by_history}")
    Observable<List<House>> getHousesByHistory(@Path("get_houses_by_history") String path,
                                               @QueryMap Map<String, String> queryOptions);

    @GET("{get_houses_by_collect}")
    Observable<List<House>> getHousesByCollect(@Path("get_houses_by_collect") String path,
                                               @QueryMap Map<String, String> queryOptions);

    @FormUrlEncoded
    @POST("{add_comment}")
    Observable<StringResponse> addComment(@Path("add_comment") String path, @FieldMap Map<String,
            String> fields);

    @GET("{comments}")
    Observable<List<Comment>> getComments(@Path("comments") String path, @QueryMap Map<String,
            String> queryOptions);
}