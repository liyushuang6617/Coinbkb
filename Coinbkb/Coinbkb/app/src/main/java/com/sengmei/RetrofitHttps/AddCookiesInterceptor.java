package com.sengmei.RetrofitHttps;

import android.content.Context;
import android.content.SharedPreferences;

import com.sengmei.meililian.MyApplication;
import com.sengmei.meililian.UserBean;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by chengwenlong on 2016/12/19.
 */

public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;

    }

    /**
     * 向请求中添加cookie,代码如下:
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        SharedPreferences sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
        //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可
        Observable.just(sharedPreferences.getString("cookie", ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        builder.addHeader("Cookie", cookie);
                        builder.addHeader("Authorization", MyApplication.Authori);
                        builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
                        //builder.addHeader("Authorization","123");
                    }
                });
        return chain.proceed(builder.build());
    }
}
