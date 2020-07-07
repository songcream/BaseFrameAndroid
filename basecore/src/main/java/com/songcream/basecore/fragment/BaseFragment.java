package com.songcream.basecore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songcream.basecore.presenter.BaseFragmentPresenter;
import com.songcream.basecore.utils.SystemUtils;
import com.songcream.basecore.view.IBaseView;
import com.songcream.simpleviewutil2.view.EmptyView;
import com.songcream.simpleviewutil2.view.LoadingDialog;
import com.trello.rxlifecycle4.components.support.RxFragment;

import java.lang.reflect.Constructor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseFragment<T extends BaseFragmentPresenter> extends RxFragment implements IBaseView {
    public EmptyView emptyView;
    public T mPresenter;
    public LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        createPresenter();

        View view=LayoutInflater.from(getContext()).inflate(getLayouId(),null);
        if(getEmptyAttachViewId()!=null) {
            emptyView =new EmptyView(getContext());
            emptyView.attachView(view.findViewById(getEmptyAttachViewId()));
        }
        loadingDialog=new LoadingDialog(getContext());
        initView(view);
        return view;
    }

    private void createPresenter() {
        //利用反射的方法，创建presenter类，那么上层就不用重复关注presenter的创建和入参，只需定义泛型类型就好了
        try {
            Class c= SystemUtils.getSuperClassGenricType(getClass());
            Constructor constructor=c.getConstructor(BaseFragment.class,SystemUtils.getSuperClassGenricType(c));
            mPresenter= (T) constructor.newInstance(this,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoadingDialog(){
        loadingDialog.show();
    }

    public void dismissLoadingDialog(){
        loadingDialog.dismiss();
    }

    public abstract void initView(View view);

    public abstract Integer getEmptyAttachViewId();

    public abstract int getLayouId();

    @Override
    public void showLoading() {
        emptyView.showEmptyLoading();
    }

    @Override
    public void showErrorView(String msg) {
        emptyView.showErrorView(msg);
    }

    @Override
    public void showEmptyView() {
        emptyView.showEmptyView();
    }

    @Override
    public void hideLoading() {
        emptyView.hideEmptyView();
    }
}
