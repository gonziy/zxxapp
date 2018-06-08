package com.zxxapp.mall.maintenance.bean.shop;

import com.zxxapp.mall.maintenance.bean.RequestBaseBean;

public class WalletBean extends RequestBaseBean {
    private Float totalAmount;
    private Float deposit;
    private Float wallet;
    private Float dayAmount;
    private Float totalDeposit;
    private Float money;

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    public Float getWallet() {
        return wallet;
    }

    public void setWallet(Float wallet) {
        this.wallet = wallet;
    }

    public Float getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(Float dayAmount) {
        this.dayAmount = dayAmount;
    }

    public Float getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Float totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
