package com.zxxapp.mall.maintenance.model;
import com.zxxapp.mall.maintenance.bean.shopping.PreOrderBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShoppingModel {

    private String username = "";
    private String password = "";

    private String book_id = "";
    private Integer bag_quantity = 0;
    private Integer payment_id = 0;
    private Integer express_id = 0;
    private String accept_name = "";
    private String hid_province = "";
    private String hid_city = "";
    private String hid_area = "";
    private String address = "";
    private String mobile = "";
    private String txt_temp_inviter = "";



    public void setData(String username,
                        String password,
                        String book_id,
                        Integer bag_quantity,
                        Integer payment_id,
                        Integer express_id,
                        String accept_name,
                        String hid_province,
                        String hid_city,
                        String hid_area,
                        String address,
                        String mobile,
                        String txt_temp_inviter
    ) {
        this.username = username;
        this.password = password;
        this.book_id = book_id;
        this.bag_quantity = bag_quantity;
        this.payment_id = payment_id;
        this.express_id = express_id;
        this.accept_name = accept_name;
        this.hid_province = hid_province;
        this.hid_city = hid_city;
        this.hid_area = hid_area;
        this.address = address;
        this.mobile = mobile;
        this.txt_temp_inviter = txt_temp_inviter;
    }

    public void submitOrderData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().submitOrder("submitorder",
                username,
                password,
                book_id,
                bag_quantity,
                payment_id,
                express_id,
                accept_name,
                hid_province,
                hid_city,
                hid_area,
                address,
                mobile,
                txt_temp_inviter
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PreOrderBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(PreOrderBean preOrderBean) {
                        listener.loadSuccess(preOrderBean);
                    }
                });
        listener.addSubscription(subscription);
    }
}
