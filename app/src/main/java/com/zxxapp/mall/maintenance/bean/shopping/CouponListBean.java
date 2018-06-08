package com.zxxapp.mall.maintenance.bean.shopping;

import java.util.List;

/**
 * Created by Thinten on 2018-06-04
 * www.thinten.com
 * 9486@163.com.
 */
public class CouponListBean {

    /**
     * msg : 优惠劵获取成功
     * data : {"pageTotal":1,"list":[{"couponId":1,"couponTitle":"","couponAmount":1,"couponPhone":null,"couponUseDate":"2018-03-30 20:17:36","couponShopBusinessHours":null,"couponShopId":1,"couponAccountId":1,"shopName":"维修商店","type":0}]}
     * success : true
     * token : 71fe2e78-7927-4266-b814-8ac4e5a45177
     */

    private String msg;
    private DataBean data;
    private String success;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * pageTotal : 1
         * list : [{"couponId":1,"couponTitle":"","couponAmount":1,"couponPhone":null,"couponUseDate":"2018-03-30 20:17:36","couponShopBusinessHours":null,"couponShopId":1,"couponAccountId":1,"shopName":"维修商店","type":0}]
         */

        private int pageTotal;
        private List<ListBean> list;

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
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
             * shopName : 维修商店
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
            private String shopName;
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

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
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
}
