package com.example.shopcar_moni.utils;

import android.os.Handler;
import android.util.Log;

import com.example.shopcar_moni.inter.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpUtils {

    private  static  volatile  HttpUtils  instance;
    private OkHttpClient client;
    private Handler handler=new Handler();

    private  HttpUtils(){
       HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;

       //创建一个日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印消息
                Log.i("http信息打印:", "log: " + message);
            }
        });
        //为日志拦截器设置打印级别
        httpLoggingInterceptor.setLevel(level);
        client=new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    //双重锁
    public  static  HttpUtils getInstance(){

        if (instance==null){
            synchronized (HttpUtils.class){
                if (instance==null){
                    instance=new HttpUtils();
                }
            }
        }
        return  instance;

    }

    public  void  get(String  url, final ICallBack callBack, final Type type){
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                final Object o = gson.fromJson(string, type);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(o);
                    }
                });
            }
        });
    }
}
