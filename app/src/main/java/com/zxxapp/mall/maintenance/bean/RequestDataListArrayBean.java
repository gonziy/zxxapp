package com.zxxapp.mall.maintenance.bean;

public class RequestDataListArrayBean<T> extends RequestBaseBean {
    private DataListBean<T> data;

    public DataListBean<T> getData() {
        return data;
    }

    public void setData(DataListBean<T> data) {
        this.data = data;
    }
}
