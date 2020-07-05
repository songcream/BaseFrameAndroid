# BaseFrameAndroid
这是一个基础的安卓原生app框架库，里面用retrofit + rxjava + MVP的方式构建了基础的类，可以很方便的开始一个app的开发

以下是这个库的使用方式
#### 集成
项目不提供gradle的方式集成，建议直接下载源码导入项目使用，这样可以自己修改项目的底层框架

#### 定义网络请求
网络的主请求地址是定义在HttpManager这个类里面的，可以修改baseUrl("http://www.xxx.com/")来更换请求域名
在RetrofitService中定义用到的网络请求，这个就是Retrofit的规则，不多说
```
public interface RetrofitService {
    @POST("/getData")
    public Observable<BaseResponseBean<List<DataBean>>> getData(@Body BaseRequestBean requestBean);
}
```

#### 定义View层
创建每个Activity或者Fragment的View层，继承自IBaseView，在里面定义每个数据获取回调到Activity的接口
```java
public interface IExampleView extends IBaseView {
    public void getData(List<DataBean> data);
}
```

#### 定义Pretenter层
创建每个Activity或者Fragment的Presenter层（数据获取逻辑层），继承自BaseActivityPresenter或者BaseFragmentPresenter， 继承的时候，要求传入Activity想对应的view层的类型,
这里我们传入刚才定义好的IExampleView，构造函数让ide自动生成就好了，我们不需要关注。实现getData()获取调用网络获取数据的方法，调用的接口就是我们上面RetrofitService定义的
网络接口，HttpManager是框架内已经实现好了的，重点关注compose(this.<BaseResponseBean<List<DataBean>>>baseTranformer())方法，这个方法可以快捷的将请求切换到子线程，回调订阅时
切回主线程，并且会将请求和当前presenter的Activity生命周期绑定在一起，不会造成内存溢出

值得注意的还有presenter层里也可以调用mView.showLoading()和hideLoading()来实现加载动画和取消加载动画
```java
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
```

#### Activity实现
1、Activity继承自BaseActivity，继承的时候，需要将我们上面定义的ExamplePresenter作为泛型类型传入，并且Activity实现我们的View层的数据回调接口IExampleView，并且实现以下方法：
```java
public class ExampleActivity extends BaseActivity<ExamplePresenter> implements IExampleView {
      /**
       * 项目可以很方便的实现空布局和加载布局
       * 只需要在这个方法返回需要实现空布局和加载布局的view的id即可。
       * 在activity、presenter需要显示空布局的地方调用emptyView.showEmptyView(); 
       * 在需要显示加载布局的地方调用emptyView.showEmptyLoading();
       * 隐藏空布局调用emptyView.hideEmptyView().
       */
      @Override
      Integer getEmptyAttachViewId() {
          return R.id.title_bar;
      }

      /**
       * 返回当前Activity的布局ID
       */
      @Override
      int getLayouId() {
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
```
在需要网络请求的地方调用mPresenter里定义的数据获取方法即可进行网络请求或者是数据库查询。

以上我们会发现，我们只需要定义好我们的presenter和view类，然后在新增activity的时候，继承自BaseActivity，定义好他们各自的泛型就好了，我们并不需要去关注presenter的创建
。因为presenter在底层是通过反射的方式，获取到你定义的泛型类型并且自动创建实例的。

