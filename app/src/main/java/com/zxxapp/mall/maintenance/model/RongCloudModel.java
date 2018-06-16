package com.zxxapp.mall.maintenance.model;
import com.zxxapp.mall.maintenance.bean.RongCloudResultBean;
import com.zxxapp.mall.maintenance.bean.account.RegisterBean;
import com.zxxapp.mall.maintenance.bean.account.UserCenterBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RongCloudModel {

    private String username = "";
    private String nickname = "";




    public void setData(String username,
                        String nickname
    ) {
        this.username = username;
        this.nickname = nickname;
    }

    public void get(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().getRongCloudResult(this.username,this.nickname).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RongCloudResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(RongCloudResultBean rongCloudResultBean) {
                        listener.loadSuccess(rongCloudResultBean);
                    }
                });
        listener.addSubscription(subscription);
    }
}
