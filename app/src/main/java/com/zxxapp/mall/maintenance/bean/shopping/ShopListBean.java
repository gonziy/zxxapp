package com.zxxapp.mall.maintenance.bean.shopping;

import java.util.List;

/**
 * Created by Thinten on 2018-04-17
 * www.thinten.com
 * 9486@163.com.
 */
public class ShopListBean {

    /**
     * msg : 获取商铺成功
     * lsit : [{"shopId":1,"shopName":"维修商店","logoImg":"http://localhost:8080/zxxapp/upload/shopImgs/1519717833040.jpg","openingTime":null,"beginPrice":null,"address":"禹城市禹王街566号","intro":"工程维修","notice":"维修","location":"禹城市","status":"s","accountShopId":1,"wallet":0,"deposit":0,"evaluate":12,"flag":"0"}]
     * success : true
     */

    private String msg;
    private boolean success;
    private List<LsitBean> lsit;

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

    public List<LsitBean> getLsit() {
        return lsit;
    }

    public void setLsit(List<LsitBean> lsit) {
        this.lsit = lsit;
    }

    public static class LsitBean {
        /**
         * shopId : 1
         * shopName : 维修商店
         * logoImg : http://localhost:8080/zxxapp/upload/shopImgs/1519717833040.jpg
         * openingTime : null
         * beginPrice : null
         * address : 禹城市禹王街566号
         * intro : 工程维修
         * notice : 维修
         * location : 禹城市
         * status : s
         * accountShopId : 1
         * wallet : 0.0
         * deposit : 0.0
         * evaluate : 12
         * flag : 0
         */

        private int shopId;
        private String shopName;
        private String logoImg;
        private Object openingTime;
        private Object beginPrice;
        private String address;
        private String intro;
        private String notice;
        private String location;
        private String status;
        private int accountShopId;
        private double wallet;
        private double deposit;
        private int evaluate;
        private String flag;

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLogoImg() {
            return logoImg;
        }

        public void setLogoImg(String logoImg) {
            this.logoImg = logoImg;
        }

        public Object getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(Object openingTime) {
            this.openingTime = openingTime;
        }

        public Object getBeginPrice() {
            return beginPrice;
        }

        public void setBeginPrice(Object beginPrice) {
            this.beginPrice = beginPrice;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getAccountShopId() {
            return accountShopId;
        }

        public void setAccountShopId(int accountShopId) {
            this.accountShopId = accountShopId;
        }

        public double getWallet() {
            return wallet;
        }

        public void setWallet(double wallet) {
            this.wallet = wallet;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public int getEvaluate() {
            return evaluate;
        }

        public void setEvaluate(int evaluate) {
            this.evaluate = evaluate;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }
    }
}
