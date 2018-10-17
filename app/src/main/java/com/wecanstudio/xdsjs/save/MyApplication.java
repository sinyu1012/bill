package com.wecanstudio.xdsjs.save;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.wecanstudio.xdsjs.save.Model.net.LoggingInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by xdsjs on 2015/11/17.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private Activity mCurrentActivity;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private static final String GANK_DATA = "http://121.42.209.19:8080";
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        okHttpClient = new OkHttpClient();
        //OKHttp的使用
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder()
                        .header("Accept-Language", buildAcceptLanguage())
                        .header("User-Agent", "")
                        .build());
            }
        });
        okHttpClient.networkInterceptors().add(new LoggingInterceptor());
        //初始化Gson
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat(DATE_FORMAT_PATTERN)
                .create();
        //初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(GANK_DATA)
                .build();
    }

    public static MyApplication getContext() {
        return instance;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(@NonNull Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private final HashMap<Class, Object> apis = new HashMap<>();

    //返回Retrofit的API
    public <T> T createApi(Class<T> service) {
        if (!apis.containsKey(service)) {
            T instance = retrofit.create(service);
            apis.put(service, instance);
        }
        //noinspection unchecked
        return (T) apis.get(service);
    }

    //返回支持的语言,格式化返回
    private String buildAcceptLanguage() {
        Locale locale = Locale.getDefault();
        return String.format("%s-%s,%s;q=0.8,en-US;q=0.6,en;q=0.4",
                locale.getLanguage(), locale.getCountry(), locale.getLanguage());
    }
}
