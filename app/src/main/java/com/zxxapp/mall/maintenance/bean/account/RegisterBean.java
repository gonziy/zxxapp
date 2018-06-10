package com.zxxapp.mall.maintenance.bean.account;

/**
 * Created by Thinten on 2018-06-09
 * www.thinten.com
 * 9486@163.com.
 */
public class RegisterBean {

    /**
     * msg : 恭喜您,注册【智修】成功！
     * code : 100
     * token : 051d86f4-264f-40c4-9f44-1458d2b3cff2
     */

    private String msg;
    private String code;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
