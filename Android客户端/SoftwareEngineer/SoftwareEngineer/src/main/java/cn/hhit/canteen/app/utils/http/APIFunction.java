package cn.hhit.canteen.app.utils.http;

import java.util.Map;

import cn.hhit.canteen.app.utils.bean.StringResponse;
import cn.hhit.canteen.page_user.model.bean.User;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
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

}
