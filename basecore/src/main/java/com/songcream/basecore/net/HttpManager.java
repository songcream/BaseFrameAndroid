package com.songcream.basecore.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static RetrofitService retrofitService;
    private static Retrofit retrofit;

    public synchronized static RetrofitService getRetrofitService(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl("http://www.xxx.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        if(retrofitService==null){
            retrofitService=retrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }
}
