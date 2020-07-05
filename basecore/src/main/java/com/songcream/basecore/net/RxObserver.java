package com.songcream.basecore.net;

import com.songcream.basecore.bean.BaseResponseBean;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class RxObserver<T> implements Observer<BaseResponseBean<T>> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull BaseResponseBean<T> value) {
        if(value==null){
            onFail(new Exception("数据异常"),"数据异常");
        }
        else if(value.getCode()!=200){
            onFail(new Exception(value.getMessage()),value.getMessage());
        }else{
            onSuccess(value.getData());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFail(e,"网络异常");
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T data);
    public abstract void onFail(Throwable e,String errorMsg);
}
