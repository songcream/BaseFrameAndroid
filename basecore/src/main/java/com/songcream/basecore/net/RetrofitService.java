package com.songcream.basecore.net;

import com.songcream.basecore.bean.BaseRequestBean;
import com.songcream.basecore.bean.BaseResponseBean;
import com.songcream.basecore.bean.DataBean;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("getData")
    public Observable<BaseResponseBean<List<DataBean>>> getData(@Body BaseRequestBean requestBean);
}
