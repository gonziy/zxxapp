package com.zxxapp.mall.maintenance.bean;

public class RequestDataBean<T> extends RequestBaseBean {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
