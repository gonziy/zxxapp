package com.zxxapp.mall.maintenance.bean;

import java.util.List;

public class RequestListArrayBean<T> extends RequestBaseBean {
    private T[] list;

    public T[] getList() {
        return list;
    }

    public void setList(T[] list) {
        this.list = list;
    }
}
