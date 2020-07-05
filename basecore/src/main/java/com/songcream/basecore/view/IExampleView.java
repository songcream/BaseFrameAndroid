package com.songcream.basecore.view;

import com.songcream.basecore.bean.DataBean;

import java.util.List;

public interface IExampleView extends IBaseView {
    public void getData(List<DataBean> data);
}
