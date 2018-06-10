package com.zxxapp.mall.maintenance.model;
import com.zxxapp.mall.maintenance.bean.account.RegisterBean;
import com.zxxapp.mall.maintenance.bean.account.UserCenterBean;
import com.zxxapp.mall.maintenance.bean.shopping.PreOrderBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserModel {

    private String username = "";
    private String password = "";
    private String valCode = "";




    public void setData(String username,
                        String password
    ) {
        this.username = username;
        this.password = password;
    }

    public void getUserCenterInfo(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getUserCenterInfo("getusercenterinfo", username, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserCenterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(UserCenterBean userCenterBean) {
                        listener.loadSuccess(userCenterBean);
                    }
                });
        listener.addSubscription(subscription);
    }

    public void setRegisterData(String username,
                        String password,String valCode
    ) {
        this.username = username;
        this.password = password;
        this.valCode = valCode;
    }

    public void Register(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().registByTelphone(this.username, this.password,this.valCode)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        listener.loadSuccess(registerBean);
                    }
                });
        listener.addSubscription(subscription);
    }
}
