package com.songcream.basecore.presenter;

import com.songcream.basecore.activity.BaseActivity;
import com.songcream.basecore.view.IBaseView;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.components.support.RxFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseActivityPresenter<T extends IBaseView> {
    private BaseActivity baseActivity;
    public T mView;

    public BaseActivityPresenter(BaseActivity baseActivity, T mView) {
        this.baseActivity = baseActivity;
        this.mView=mView;
    }

    /**
     * 将请求转成子线程请求，订阅时切回UI线程，并且当activity销毁时，订阅取消
     */
    public <E> ObservableTransformer<E, E> baseTranformer(){
        return new ObservableTransformer<E, E>() {
            @Override
            public @NonNull ObservableSource<E> apply(@NonNull Observable<E> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(baseActivity.<E>bindUntilEvent(ActivityEvent.DESTROY));
            }
        };
    }
}
