package com.zxxapp.mall.maintenance.bean;

import java.util.List;

public class DataListBean<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
