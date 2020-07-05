package com.songcream.basecore.presenter;

import com.songcream.basecore.activity.BaseActivity;
import com.songcream.basecore.bean.BaseRequestBean;
import com.songcream.basecore.bean.BaseResponseBean;
import com.songcream.basecore.bean.DataBean;
import com.songcream.basecore.net.HttpManager;
import com.songcream.basecore.net.RxObserver;
import com.songcream.basecore.view.IExampleView;

import java.util.List;

public class ExamplePresenter extends BaseActivityPresenter<IExampleView> {

    public ExamplePresenter(BaseActivity baseActivity, IExampleView mView) {
        super(baseActivity, mView);
    }

    public void getData(BaseRequestBean baseRequestBean){
        HttpManager.getRetrofitService().getData(baseRequestBean)
                .compose(this.<BaseResponseBean<List<DataBean>>>baseTranformer())
                .subscribe(new RxObserver<List<DataBean>>() {
                    @Override
                    public void onSuccess(List<DataBean> data) {
                        mView.getData(data);
                    }

                    @Override
                    public void onFail(Throwable e, String errorMsg) {
                        mView.showErrorView(errorMsg);
                    }
                });
    }
}
