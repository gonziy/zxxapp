package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2018-06-09
 * www.thinten.com
 * 9486@163.com.
 */
public class SendCodeBean {

    /**
     * msg : 短信发送失败，请联系客服人员！！
     * code : 101
     */

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
