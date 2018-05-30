package com.zxxapp.mall.maintenance.bean.account;

import android.databinding.ViewDataBinding;

import java.util.List;

/**
 * Created by Thinten on 2018-01-12
 * www.thinten.com
 * 9486@163.com.
 */

public class UserAddressBean{
    /**
     * msg : 验证成功
     * error : 0
     * data : [{"id":20960,"user_id":5902,"user_name":"13148453860","accept_name":"订单","area":"河北省,石家庄市,长安区","address":"的说法的","mobile":"13145644564","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-11-03T19:46:34.847","area_code":"130000,130100,130102","area_id":"130102"},{"id":25424,"user_id":5902,"user_name":"13148453860","accept_name":"ss","area":"山东省,青岛市,莱西市","address":"烟台路万福鑫○49号楼1单元1602","mobile":"15512341231","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-05T18:26:47.403","area_code":"370000,370200,370285","area_id":"370285"},{"id":28158,"user_id":5902,"user_name":"13148453860","accept_name":"sdsd","area":"山东省,潍坊市,寿光市","address":"滨海经济开发区大家洼街道办事处","mobile":"13312341234","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-13T20:44:12.583","area_code":"370000,370700,370783","area_id":"370783"},{"id":30094,"user_id":5902,"user_name":"13148453860","accept_name":"dd","area":"山东省,东营市,东营区","address":"垦利中兴路122号","mobile":"13311111111","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-28T21:41:59.41","area_code":"370000,370500,370502","area_id":"370502"},{"id":30095,"user_id":5902,"user_name":"13148453860","accept_name":"444","area":"天津,天津市,和平区","address":"44444w1234124","mobile":"13312351544","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-28T21:59:27.027","area_code":"120000,120100,120101","area_id":"120101"},{"id":30096,"user_id":5902,"user_name":"13148453860","accept_name":"234234","area":"天津,天津市,南开区","address":"23423dsfgsdfgasfg","mobile":"13366665555","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-28T22:00:13.08","area_code":"120000,120100,120104","area_id":"120104"},{"id":30097,"user_id":5902,"user_name":"13148453860","accept_name":"孙冰","area":"山东省,东营市,东营区","address":"黄河路1000-1号","mobile":"13144445555","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2017-12-28T22:56:20.817","area_code":"370000,370500,370502","area_id":"370502"},{"id":30846,"user_id":5902,"user_name":"13148453860","accept_name":"士大夫","area":"内蒙古自治区,包头市,东河区","address":"撒旦法啥的服务器额","mobile":"13314568754","telphone":"","email":"","post_code":"","is_default":0,"add_time":"2018-01-04T17:31:04.13","area_code":"150000,150200,150202","area_id":"150202"}]
     */

    private String msg;
    private String error;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 20960
         * user_id : 5902
         * user_name : 13148453860
         * accept_name : 订单
         * area : 河北省,石家庄市,长安区
         * address : 的说法的
         * mobile : 13145644564
         * telphone :
         * email :
         * post_code :
         * is_default : 0
         * add_time : 2017-11-03T19:46:34.847
         * area_code : 130000,130100,130102
         * area_id : 130102
         */

        private int id;
        private int user_id;
        private String user_name;
        private String accept_name;
        private String area;
        private String address;
        private String mobile;
        private String telphone;
        private String email;
        private String post_code;
        private int is_default;
        private String add_time;
        private String area_code;
        private String area_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAccept_name() {
            return accept_name;
        }

        public void setAccept_name(String accept_name) {
            this.accept_name = accept_name;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public int getIs_default() {
            return is_default;
        }

        public void setIs_default(int is_default) {
            this.is_default = is_default;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }
}
