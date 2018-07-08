package com.zxxapp.mall.maintenance.model;

import com.zxxapp.mall.maintenance.bean.OrderByStatusBean;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.bean.shopping.ShopListBean;
import com.zxxapp.mall.maintenance.bean.shopping.TempOrderListBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jingbin on 2017/1/17.
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页  的Model
 * 好处之一是请求数据接口可以统一，不用每个地方都写请求的接口，更换接口方便。
 * 其实代码量也没有减少多少，但维护起来方便。
 */

public class OrderByAccountModel {

    private String token = "";
    private String pageNo = "";
    private String pageLimit = "";
    private String status = "";

    public void setData(String token,String pageNo,String pageLimit) {

        this.token = token;
        this.pageNo = pageNo;
        this.pageLimit = pageLimit;
    }
    public void setData(String token,String status) {

        this.token = token;
        this.status = status;
    }

    public void getData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().getOrderByAccountId(this.token,this.pageNo,this.pageLimit)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderByAccountIdBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(OrderByAccountIdBean orderByAccountIdBean) {
                        listener.loadSuccess(orderByAccountIdBean);

                    }
                });
        listener.addSubscription(subscription);
    }

    public void getTempOrderData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().getTempOrderByToken(this.token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TempOrderListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(TempOrderListBean tempOrderListBean) {
                        listener.loadSuccess(tempOrderListBean);

                    }
                });
        listener.addSubscription(subscription);
    }
    public void getOrderByTokenAndStatus(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().getOrderByTokenAndStatus(this.token,this.status)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderByStatusBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(OrderByStatusBean orderByStatusBean) {
                        listener.loadSuccess(orderByStatusBean);

                    }
                });
        listener.addSubscription(subscription);
    }
}
