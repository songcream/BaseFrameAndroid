package com.songcream.basecode.view;

import com.songcream.basecore.bean.DataBean;
import com.songcream.basecore.view.IBaseView;

import java.util.List;

public interface IExampleView extends IBaseView {
    public void getData(List<DataBean> data);
}
