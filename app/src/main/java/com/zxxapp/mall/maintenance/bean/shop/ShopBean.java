package com.zxxapp.mall.maintenance.bean.shop;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShopBean {
    private Integer shopId;
    private String shopName;
    private String logoImg;
    private Date openingTime;
    private Integer beginPrice;
    private String address;
    private String intro;
    private String notice;
    private String location;
    private String status;
    private Integer accountShopId;
    private Float wallet;
    private Float deposit;
    private Integer evaluate;
    private String flag;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
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

    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }

    public Integer getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(Integer beginPrice) {
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

    public Integer getAccountShopId() {
        return accountShopId;
    }

    public void setAccountShopId(Integer accountShopId) {
        this.accountShopId = accountShopId;
    }

    public Float getWallet() {
        return wallet;
    }

    public void setWallet(Float wallet) {
        this.wallet = wallet;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    public Integer getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Integer evaluate) {
        this.evaluate = evaluate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}