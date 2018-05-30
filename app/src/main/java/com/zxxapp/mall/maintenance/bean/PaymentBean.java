package com.zxxapp.mall.maintenance.bean;

/**
 * Created by Thinten on 2017-12-19
 * www.thinten.com
 * 9486@163.com.
 */

public class PaymentBean {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 支付名称(weixin/alipay)
     */
    private String name;
    /**
     * 图标
     */
    private int icon;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String desc;
}
