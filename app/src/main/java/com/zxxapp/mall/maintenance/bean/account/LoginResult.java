package com.zxxapp.mall.maintenance.bean.account;

/**
 * Created by Thinten on 2017-12-14
 * www.thinten.com
 * 9486@163.com.
 */

public class LoginResult {


    /**
     * code : 100
     * data : {"msg":"验证通过!","userName":"admin","userId":1,"token":"0e4ef13d-c079-49dd-bb01-3213c709626f"}
     */

    private String code;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * msg : 验证通过!
         * userName : admin
         * userId : 1
         * token : 0e4ef13d-c079-49dd-bb01-3213c709626f
         */

        private String msg;
        private String userName;
        private int userId;
        private String token;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
