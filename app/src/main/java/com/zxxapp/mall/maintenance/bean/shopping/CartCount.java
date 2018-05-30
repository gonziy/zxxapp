package com.zxxapp.mall.maintenance.bean.shopping;

/**
 * Created by Thinten on 2018-01-15
 * www.thinten.com
 * 9486@163.com.
 */

public class CartCount {

    /**
     * msg : 获取成功
     * error : 0
     * count : 0
     */

    private String msg;
    private int error;
    private int count;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
