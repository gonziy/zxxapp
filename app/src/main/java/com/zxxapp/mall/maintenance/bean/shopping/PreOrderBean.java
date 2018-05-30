package com.zxxapp.mall.maintenance.bean.shopping;

import java.util.List;

/**
 * Created by Thinten on 2017-12-28
 * www.thinten.com
 * 9486@163.com.
 */

public class PreOrderBean {

    /**
     * msg : 订单保存成功
     * error : 1
     * data : {"id":"P17122822562079200015902","str_orders":"B59021712282256209960002","orders":["B59021712282256209960002"],"create_time":"2017-12-28T22:56:21","amount":32.8}
     */

    private String msg;
    private int error;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : P17122822562079200015902
         * str_orders : B59021712282256209960002
         * orders : ["B59021712282256209960002"]
         * create_time : 2017-12-28T22:56:21
         * amount : 32.8
         */

        private String id;
        private String str_orders;
        private String create_time;
        private double amount;
        private List<String> orders;
        private String wx_prepay_id;
        private String wx_nonce_str;
        private String wx_sign;
        private String wx_timestamp;

        public String getWx_prepay_id() {
            return wx_prepay_id;
        }

        public void setWx_prepay_id(String wx_prepay_id) {
            this.wx_prepay_id = wx_prepay_id;
        }

        public String getWx_nonce_str() {
            return wx_nonce_str;
        }

        public void setWx_nonce_str(String wx_nonce_str) {
            this.wx_nonce_str = wx_nonce_str;
        }

        public String getWx_sign() {
            return wx_sign;
        }

        public void setWx_sign(String wx_sign) {
            this.wx_sign = wx_sign;
        }

        public String getWx_timestamp() {
            return wx_timestamp;
        }

        public void setWx_timestamp(String wx_timestamp) {
            this.wx_timestamp = wx_timestamp;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStr_orders() {
            return str_orders;
        }

        public void setStr_orders(String str_orders) {
            this.str_orders = str_orders;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }
    }
}
