package com.songcream.basecode.activity;

import android.os.Bundle;

import com.songcream.basecode.presenter.ExamplePresenter;
import com.songcream.basecode.view.IExampleView;
import com.songcream.basecore.R;
import com.songcream.basecore.activity.BaseActivity;
import com.songcream.basecore.bean.BaseRequestBean;
import com.songcream.basecore.bean.DataBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ExampleActivity extends BaseActivity<ExamplePresenter> implements IExampleView {
    @Override
    public Integer getEmptyAttachViewId() {
        return R.id.title_bar;
    }

    @Override
    public int getLayouId() {
        return R.layout.layout_example;
    }

    @Override
    public void getData(List<DataBean> data) {
        //对data进行处理，显示到界面上
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //网络请求
        mPresenter.getData(new BaseRequestBean());
    }
}
