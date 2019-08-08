package com.example.wanandroid2.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.wanandroid2.app.Globle;
import com.example.wanandroid2.app.MyApp;
import com.example.wanandroid2.utils.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static HttpManager httpManager;

    private Context context;
    private final SharedPreferences sharedPreferences;

    private HttpManager() {
        sharedPreferences = context.getSharedPreferences("status", Context.MODE_PRIVATE);
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Globle.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClicent())
                .build();
    }

    public <T> T getApiService(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    private OkHttpClient getOkHttpClicent() {


        //日志过滤器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Cache cache = new Cache(new File(MyApp.getInstance().getCacheDir(), "Cache"), 1024 * 1024 * 10);
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .addInterceptor(new MyCacheinterceptor())
                .addNetworkInterceptor(new MyCacheinterceptor())
                .addNetworkInterceptor(new CookieInterceptor())
                .build();
    }

    //post 不可以做缓存
    private class MyCacheinterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if (!HttpUtils.isNetworkAvailable(MyApp.getInstance())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            //利用拦截器发送出去
            Response originalResponse = chain.proceed(request);
            if (HttpUtils.isNetworkAvailable(MyApp.getInstance())) {
                int maxAge = 0;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 15;
                return originalResponse.newBuilder()
                        // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

        }
    }

    class CookieInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            List<String> headers = response.headers("Set-Cookie");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < headers.size(); i++) {
                String cookidGet = headers.get(i);
                String substring = cookidGet.substring(0, cookidGet.indexOf(";"));
                stringBuilder.append(substring);
                if (!(i == headers.size() - 1)) {
                    stringBuilder.append(";");
                }
            }
            sharedPreferences.edit().putString("cookie", stringBuilder.toString()).apply();
            return response;
        }
    }

}
