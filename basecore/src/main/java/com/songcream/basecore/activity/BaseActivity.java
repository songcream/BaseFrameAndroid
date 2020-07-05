package com.songcream.basecore.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import com.gyf.immersionbar.ImmersionBar;
import com.songcream.basecore.presenter.BaseActivityPresenter;
import com.songcream.basecore.utils.SystemUtils;
import com.songcream.basecore.view.IBaseView;
import com.songcream.simpleviewutil2.view.EmptyView;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.Nullable;

public abstract class BaseActivity<T extends BaseActivityPresenter> extends RxAppCompatActivity implements IBaseView {
    ImmersionBar mImmersionBar;
    EmptyView emptyView;
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true)
                .keyboardEnable(false)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);  //单独指定软键盘模式
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayouId());
        getSupportActionBar().hide();
//        ImmersionBar.setTitleBar(this,findViewById(R.id.titleBar));
        if(getEmptyAttachViewId()!=null) {
            emptyView=new EmptyView(this);
            emptyView.attachView(findViewById(getEmptyAttachViewId()));
        }
        //利用反射的方法，创建presenter类，那么上层就不用重复关注presenter的创建和入参，只需定义泛型类型就好了
        try {
            Class c=SystemUtils.getSuperClassGenricType(getClass());
            Constructor constructor=c.getConstructor(BaseActivity.class,SystemUtils.getSuperClassGenricType(c));
            mPresenter= (T) constructor.newInstance(this,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    abstract Integer getEmptyAttachViewId();

    abstract int getLayouId();

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
