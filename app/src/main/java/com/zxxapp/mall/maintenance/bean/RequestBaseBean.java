package com.zxxapp.mall.maintenance.bean;

/**
 * 请求结果的基础类
 */
public class RequestBaseBean {
    private String code;
    private String msg;
    private String success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
