package com.zxxapp.mall.maintenance.model;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.payment.PayBean;
import com.zxxapp.mall.maintenance.bean.shopping.PreOrderBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PaymentModel {

    private String orderNo = "";
    private String payment = "";
    private String coupon = "";



    public void setData(String orderNo,
                        String payment,
                        String coupon
    ) {
        this.orderNo = orderNo;
        this.payment = payment;
        this.coupon = coupon;
    }

    public void Payment(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().payment(orderNo,payment,coupon)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(PayBean payBean) {
                        listener.loadSuccess(payBean);
                    }
                });
        listener.addSubscription(subscription);
    }
}
