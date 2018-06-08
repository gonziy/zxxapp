package com.zxxapp.mall.maintenance.bean.shopping;

/**
 * Created by Thinten on 2018-06-04
 * www.thinten.com
 * 9486@163.com.
 */
public class CouponBean {

    /**
     * msg : 获取成功
     * success : true
     * list : {"couponId":1,"couponTitle":"","couponAmount":1,"couponPhone":null,"couponUseDate":"2018-03-30 20:17:36","couponShopBusinessHours":null,"couponShopId":1,"couponAccountId":1,"shopName":null,"type":0}
     */

    private String msg;
    private boolean success;
    private ListBean list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * couponId : 1
         * couponTitle :
         * couponAmount : 1.0
         * couponPhone : null
         * couponUseDate : 2018-03-30 20:17:36
         * couponShopBusinessHours : null
         * couponShopId : 1
         * couponAccountId : 1
         * shopName : null
         * type : 0
         */

        private int couponId;
        private String couponTitle;
        private double couponAmount;
        private Object couponPhone;
        private String couponUseDate;
        private Object couponShopBusinessHours;
        private int couponShopId;
        private int couponAccountId;
        private Object shopName;
        private int type;

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public String getCouponTitle() {
            return couponTitle;
        }

        public void setCouponTitle(String couponTitle) {
            this.couponTitle = couponTitle;
        }

        public double getCouponAmount() {
            return couponAmount;
        }

        public void setCouponAmount(double couponAmount) {
            this.couponAmount = couponAmount;
        }

        public Object getCouponPhone() {
            return couponPhone;
        }

        public void setCouponPhone(Object couponPhone) {
            this.couponPhone = couponPhone;
        }

        public String getCouponUseDate() {
            return couponUseDate;
        }

        public void setCouponUseDate(String couponUseDate) {
            this.couponUseDate = couponUseDate;
        }

        public Object getCouponShopBusinessHours() {
            return couponShopBusinessHours;
        }

        public void setCouponShopBusinessHours(Object couponShopBusinessHours) {
            this.couponShopBusinessHours = couponShopBusinessHours;
        }

        public int getCouponShopId() {
            return couponShopId;
        }

        public void setCouponShopId(int couponShopId) {
            this.couponShopId = couponShopId;
        }

        public int getCouponAccountId() {
            return couponAccountId;
        }

        public void setCouponAccountId(int couponAccountId) {
            this.couponAccountId = couponAccountId;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
