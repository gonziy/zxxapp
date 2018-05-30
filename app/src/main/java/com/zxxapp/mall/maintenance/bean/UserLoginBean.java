package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2018-04-16
 * www.thinten.com
 * 9486@163.com.
 */
public class UserLoginBean {


    /**
     * data : {"phone":null,"nickName":null,"avatarImg":null,"email":null,"token":"886e187b-b5a7-41c5-869f-98ddfdb2bdae","username":"admin"}
     * success : true
     */

    private DataBean data;
    private String success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * phone : null
         * nickName : null
         * avatarImg : null
         * email : null
         * token : 886e187b-b5a7-41c5-869f-98ddfdb2bdae
         * username : admin
         */

        private String token;
        private String username;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
