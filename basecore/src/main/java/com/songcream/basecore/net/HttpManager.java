package com.songcream.basecore.net;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static RetrofitService retrofitService;
    private static Retrofit retrofit;

    public synchronized static RetrofitService getRetrofitService(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("http://www.baidu.com/")
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if(retrofitService==null){
            retrofitService=retrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }
}
