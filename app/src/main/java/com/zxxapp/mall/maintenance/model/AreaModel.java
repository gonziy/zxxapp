package com.zxxapp.mall.maintenance.model;

import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AreaModel {

    private String parentId = "";


    public void setData(String parentId) {
        this.parentId = parentId;
    }

    public void getProvinceData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getProvince("provincelist")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(AreaBean areaBean) {
                        listener.loadSuccess(areaBean);

                    }
                });
        listener.addSubscription(subscription);
    }

    public void getCityData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getCity("citylist",parentId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(AreaBean areaBean) {
                        listener.loadSuccess(areaBean);

                    }
                });
        listener.addSubscription(subscription);
    }
    public void getAreaData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getArea("arealist",parentId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(AreaBean areaBean) {
                        listener.loadSuccess(areaBean);

                    }
                });
        listener.addSubscription(subscription);
    }
}
