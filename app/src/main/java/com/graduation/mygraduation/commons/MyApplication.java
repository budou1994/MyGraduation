package com.graduation.mygraduation.commons;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 *  初始化一些APP参数
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
    }

    /**
     * 配置一下okHttp的参数
     */
    private void initHttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .cookieJar(cookieJar)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)

                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
