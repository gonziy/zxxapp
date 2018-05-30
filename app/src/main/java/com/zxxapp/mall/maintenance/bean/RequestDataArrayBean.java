package com.zxxapp.mall.maintenance.bean;

import java.util.List;

public class RequestDataArrayBean<T> extends RequestBaseBean {
    private T[] data;

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }
}
