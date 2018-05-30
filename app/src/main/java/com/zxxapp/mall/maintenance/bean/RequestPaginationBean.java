package com.zxxapp.mall.maintenance.bean;

import java.util.List;

public class RequestPaginationBean<T> extends RequestBaseBean {
    private T[] dataRows;
    private int total;

    public T[] getDataRows() {
        return dataRows;
    }

    public void setDataRows(T[] dataRows) {
        this.dataRows = dataRows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
