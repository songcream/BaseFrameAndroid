package com.songcream.basecore.activity;

import com.songcream.basecore.R;
import com.songcream.basecore.bean.DataBean;
import com.songcream.basecore.presenter.ExamplePresenter;
import com.songcream.basecore.view.IExampleView;

import java.util.List;

public class ExampleActivity extends BaseActivity<ExamplePresenter> implements IExampleView {
    @Override
    Integer getEmptyAttachViewId() {
        return R.id.title_bar;
    }

    @Override
    int getLayouId() {
        return R.layout.layout_example;
    }

    @Override
    public void getData(List<DataBean> data) {

    }
}
