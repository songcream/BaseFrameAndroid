package com.songcream.basecore.view;

public interface IBaseView {
    public void showLoading();
    public void showErrorView(String msg);
    public void showEmptyView();
    public void hideLoading();
}
