package com.zxxapp.mall.maintenance.bean.account;

/**
 * Created by Thinten on 2017-12-14
 * www.thinten.com
 * 9486@163.com.
 */

public class LoginResult {


    /**
     * data : {"msg":"验证通过!","userName":"18654643210","userId":10,"token":"9b25d676-0ae4-4f65-82f4-419ba34e8fe0"}
     */

    private DataBean data;
    private String code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * msg : 验证通过!
         * userName : 18654643210
         * userId : 10
         * token : 9b25d676-0ae4-4f65-82f4-419ba34e8fe0
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
