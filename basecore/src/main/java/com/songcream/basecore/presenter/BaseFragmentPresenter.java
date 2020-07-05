package com.songcream.basecore.presenter;

import com.songcream.basecore.fragment.BaseFragment;
import com.songcream.basecore.view.IBaseView;
import com.trello.rxlifecycle4.android.FragmentEvent;
import com.trello.rxlifecycle4.components.support.RxFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseFragmentPresenter<T extends IBaseView> {
    private BaseFragment baseFragment;
    public T mView;

    public BaseFragmentPresenter(BaseFragment baseFragment,T mView) {
        this.baseFragment = baseFragment;
        this.mView=mView;
    }

    public <E> ObservableTransformer<E, E> baseTranformer(){
        return new ObservableTransformer<E, E>() {
            @Override
            public @NonNull ObservableSource<E> apply(@NonNull Observable<E> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(baseFragment.<E>bindUntilEvent(FragmentEvent.DESTROY_VIEW));
            }
        };
    }
}
