package com.zxxapp.mall.maintenance.bean.account;

/**
 * Created by Thinten on 2018-01-19
 * www.thinten.com
 * 9486@163.com.
 */

public class UserCenterBean {

    /**
     * msg : 获取成功
     * error : 0
     * income : 629
     * money : 3922.42
     * ordercount : 39
     */

    private String msg;
    private int error;
    private double income;
    private double money;
    private int ordercount;

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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getOrdercount() {
        return ordercount;
    }

    public void setOrdercount(int ordercount) {
        this.ordercount = ordercount;
    }
}
