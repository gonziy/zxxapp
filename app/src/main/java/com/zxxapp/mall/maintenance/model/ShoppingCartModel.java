package com.zxxapp.mall.maintenance.model;

import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.shopping.CartCount;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShoppingCartModel {

    private String username = "";
    private String password = "";
    private Integer article_id = 0;
    private Integer goods_id = 0;
    private Integer quantity = 0;
    private Integer selected = 0;



    public void setData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void getGoodsData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getShoppingCartData("cartlist",username,password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(CartResult cartResult) {
                        listener.loadSuccess(cartResult);

                    }
                });
        listener.addSubscription(subscription);
    }
    public void getGoodsCount(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getShoppingCartCount("cartcount",username,password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartCount>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(CartCount cartCount) {
                        listener.loadSuccess(cartCount);

                    }
                });
        listener.addSubscription(subscription);
    }

    public void setSelectedData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void getSelectedGoodsData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getShoppingCartSelectedData("cartselectedlist",username,password)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(CartResult cartResult) {
                        listener.loadSuccess(cartResult);

                    }
                });
        listener.addSubscription(subscription);
    }

    public void setQuantityData(String username, String password,Integer article_id,Integer goods_id,Integer quantity) {
        this.username = username;
        this.password = password;
        this.article_id = article_id;
        this.goods_id = goods_id;
        this.quantity =quantity;
    }
    public void setQuantity(final RequestImpl listener){
        Subscription subscription = HttpClient.Builder.getZMServer().setShoppingCartQuantity("cartquantityupdate",username,password,article_id,goods_id,quantity)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void setSelectedData(String username, String password,Integer article_id,Integer goods_id,Integer selected) {
        this.username = username;
        this.password = password;
        this.article_id = article_id;
        this.goods_id = goods_id;
        this.selected = selected;
    }
    public void setSelected(final RequestImpl listener){
        Subscription subscription = HttpClient.Builder.getZMServer().setShoppingCartSelect("selectgoodsincart",username,password,article_id,goods_id,selected)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void setDeleteData(String username, String password,Integer article_id,Integer goods_id) {
        this.username = username;
        this.password = password;
        this.article_id = article_id;
        this.goods_id = goods_id;
    }
    public void setDelete(final RequestImpl listener){
        Subscription subscription = HttpClient.Builder.getZMServer().setShoppingCartDelete("deletegoodsincart",username,password,article_id,goods_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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

    public void setIntoCartData(String username, String password,Integer article_id,Integer goods_id,Integer quantity) {
        this.username = username;
        this.password = password;
        this.article_id = article_id;
        this.goods_id = goods_id;
        this.quantity =quantity;
    }
    public void setIntoCart(final RequestImpl listener){
        Subscription subscription = HttpClient.Builder.getZMServer().setIntoShoppingCart("addtocart",username,password,article_id,goods_id,quantity)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
