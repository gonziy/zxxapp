package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2017-12-17
 * www.thinten.com
 * 9486@163.com.
 */

public class ResultBean {

    /**
     * msg : 用户信息验证失败
     * error : -1
     */

    private String msg;
    private int error;

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
}
