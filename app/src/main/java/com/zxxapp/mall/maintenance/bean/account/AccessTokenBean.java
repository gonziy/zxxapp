package com.zxxapp.mall.maintenance.bean.account;

/**
 * Created by Thinten on 2017-12-27
 * www.thinten.com
 * 9486@163.com.
 */

public class AccessTokenBean {

    /**
     * access_token : 5_2QbhHttBSMDs3E_2xyItJcWfNr-tyRO4TwGee3vxuGfjOIZb9B2ZjfdK9QWs1j9ap25gS6pW6jMtSY8hjNSwAPKvRsr61VatWD39-uzxdig
     * expires_in : 7200
     * refresh_token : 5_pYaq_jaPwAhx86UE-9fjtrt3mzjLo3ThF023ZwjtzKQhLDA1vvx6TzdNGFgZ0BLAGGmXI39zEQPHVmoQKjW0sslI7Q7PNBhaUwLK_s0q4OM
     * openid : oyeNM1jQyYj0EfwKbdXWT7MpeLi0
     * scope : snsapi_userinfo
     * unionid : oNjkz0q_nsipoDjKT1uO7zYDu-R4
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
