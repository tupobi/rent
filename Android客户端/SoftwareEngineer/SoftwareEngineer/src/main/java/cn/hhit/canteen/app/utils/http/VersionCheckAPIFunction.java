package cn.hhit.canteen.app.utils.http;

import java.util.Map;

import cn.hhit.canteen.main.model.bean.AppVersion;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2018/4/15/015.
 */

public interface VersionCheckAPIFunction {
    @POST("{service}/{versioncheck}")
    Observable<AppVersion> getLatestVersion(@Path("service") String path, @Path("versioncheck")
            String versionCheck, @QueryMap Map<String, String> queryOptions);

}
