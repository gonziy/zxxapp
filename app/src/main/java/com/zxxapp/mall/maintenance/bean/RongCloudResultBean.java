package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2018-06-16
 * www.thinten.com
 * 9486@163.com.
 */
public class RongCloudResultBean {
    /**
     * msg : 获取成功
     * data : {"code":200,"userId":"1","token":"7Pw+pHj0Qq7gTnM6beytdcEGX3M0bXI0TC2+QY9D/BJApzigCJUeciZYtvYaSDTr9Jlp0yBAAAd1E4+Qp+a2bg=="}
     * success : true
     */

    private String msg;
    private String data;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
