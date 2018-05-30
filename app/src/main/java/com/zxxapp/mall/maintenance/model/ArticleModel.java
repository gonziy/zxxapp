package com.zxxapp.mall.maintenance.model;

import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
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

public class ArticleModel {

    private int page_size;
    private int page_index;

    public void setData(int page_size, int page_index) {

        this.page_size = page_size;
        this.page_index = page_index;
    }

    public void getArticleData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getArticleList("articlelist","news","2064", String.valueOf(page_index), String.valueOf(page_size))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        listener.loadSuccess(articleBean);

                    }
                });
        listener.addSubscription(subscription);
    }

    public void getIndexData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getArticleList("articlelist","news","2065", String.valueOf(page_index), String.valueOf(page_size))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        listener.loadSuccess(articleBean);

                    }
                });
        listener.addSubscription(subscription);
    }
    public void getADData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getArticleList("articlelist","news","2069", "1", "1")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        listener.loadSuccess(articleBean);

                    }
                });
        listener.addSubscription(subscription);
    }
    public void getBannerData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZMServer().getArticleList("articlelist","news","2068", "1", "5")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        listener.loadSuccess(articleBean);

                    }
                });
        listener.addSubscription(subscription);
    }
}
