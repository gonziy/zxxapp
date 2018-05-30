package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2018-04-16
 * www.thinten.com
 * 9486@163.com.
 */
public class UserLoginBean {

    /**
     * data : {"phone":"13344445555","nickName":"g999","avatarImg":null,"email":"9486@163.com","token":"f0f86f4c-8635-4c90-b3a0-0bdcc6e07d0e","username":"gonziy"}
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
         * phone : 13344445555
         * nickName : g999
         * avatarImg : null
         * email : 9486@163.com
         * token : f0f86f4c-8635-4c90-b3a0-0bdcc6e07d0e
         * username : gonziy
         */

        private String phone;
        private String nickName;
        private Object avatarImg;
        private String email;
        private String token;
        private String username;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getAvatarImg() {
            return avatarImg;
        }

        public void setAvatarImg(Object avatarImg) {
            this.avatarImg = avatarImg;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

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
