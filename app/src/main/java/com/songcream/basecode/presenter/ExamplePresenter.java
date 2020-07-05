package com.songcream.basecode.presenter;

import com.songcream.basecode.view.IExampleView;
import com.songcream.basecore.activity.BaseActivity;
import com.songcream.basecore.bean.BaseRequestBean;
import com.songcream.basecore.bean.BaseResponseBean;
import com.songcream.basecore.bean.DataBean;
import com.songcream.basecore.net.HttpManager;
import com.songcream.basecore.net.RxObserver;
import com.songcream.basecore.presenter.BaseActivityPresenter;

import java.util.List;

public class ExamplePresenter extends BaseActivityPresenter<IExampleView> {

    public ExamplePresenter(BaseActivity baseActivity, IExampleView mView) {
        super(baseActivity, mView);
    }

    //实现调用网络获取数据，并且回调界面逻辑
    public void getData(BaseRequestBean baseRequestBean){
        mView.showLoading();
        HttpManager.getRetrofitService().getData(baseRequestBean)
                .compose(this.<BaseResponseBean<List<DataBean>>>baseTranformer())
                .subscribe(new RxObserver<List<DataBean>>() {
                    @Override
                    public void onSuccess(List<DataBean> data) {
                        mView.hideLoading();
                        mView.getData(data);
                    }

                    @Override
                    public void onFail(Throwable e, String errorMsg) {
                        mView.hideLoading();
                        mView.showErrorView(errorMsg);
                    }
                });
    }
}
