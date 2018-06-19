package com.zxxapp.mall.maintenance.model;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.RegisterBean;
import com.zxxapp.mall.maintenance.bean.account.UserCenterBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserInfoModel {

    private String token = "";
    private String nickname = "";
    private String phone = "";
    private String aratar_img = "";




    public void setData(String token,
                        String username,
                        String phone
    ) {
        this.token = token;
        this.nickname = nickname;
        this.phone = phone;
        this.aratar_img = "";
    }

    public void editUserInfo(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().editAccountAPI(this.token, this.nickname, this.phone,this.aratar_img)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        listener.loadSuccess(resultBean);
                    }
                });
        listener.addSubscription(subscription);
    }

}
