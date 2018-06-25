package cn.hhit.canteen.app.utils.http;


import java.util.concurrent.TimeUnit;

import cn.hhit.canteen.app.utils.LogUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 写自己的代码, 让别人说去吧!
 */

public class RetrofitFactory1 {

    private static RetrofitFactory1 mRetrofitFactory;
    private static VersionCheckAPIFunction mVersionCheckAPIFunction;

    private RetrofitFactory1() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(HttpConfig
                .HTTP_TIME, TimeUnit.SECONDS).readTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME, TimeUnit.SECONDS)
//                .addInterceptor(InterceptorUtil.tokenInterceptor())
//                .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                .addNetworkInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return null;
//                    }
//                })
                .build();
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(HttpConfig.VERSION_CHECK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                .client(mOkHttpClient).build();
        mVersionCheckAPIFunction = mRetrofit.create(VersionCheckAPIFunction.class);
        LogUtil.e("初始化RetrofitFactory1");
    }

    public static RetrofitFactory1 getInstence() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory1.class) {
                if (mRetrofitFactory == null) mRetrofitFactory = new RetrofitFactory1();
            }

        }
        return mRetrofitFactory;
    }

    public VersionCheckAPIFunction API() {
        return mVersionCheckAPIFunction;
    }
}
