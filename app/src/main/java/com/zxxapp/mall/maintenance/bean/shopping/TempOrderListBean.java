package com.zxxapp.mall.maintenance.bean.shopping;

import java.util.List;

/**
 * Created by Thinten on 2018-06-02
 * www.thinten.com
 * 9486@163.com.
 */
public class TempOrderListBean {

    /**
     * msg : 获取成功
     * succuss : true
     * list : [{"orderId":0,"serviceId":3,"unitPrice":null,"discount":0,"content":"卫浴维修","name":null,"phone":"2","status":"0","accountId":1,"shopId":0,"orderNo":"20180602235049331002","orderDate":"2018-06-02 23:50:49","orverTime":"2018-06-03 23:50:49","requiredDate":null,"address":null,"payment":null,"paymentType":0,"paymentTime":null,"paymentStatus":0,"lat":null,"lng":"0","location":"禹城书法家爱看的房","picture":null,"type":0,"userName":null,"shopName":null,"serviceName":null,"servicePicture":null,"category_type":null,"evaluateType":0,"pubDateStart":null,"pubDateEnd":null}]
     */

    private String msg;
    private boolean succuss;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccuss() {
        return succuss;
    }

    public void setSuccuss(boolean succuss) {
        this.succuss = succuss;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * orderId : 0
         * serviceId : 3
         * unitPrice : null
         * discount : 0
         * content : 卫浴维修
         * name : null
         * phone : 2
         * status : 0
         * accountId : 1
         * shopId : 0
         * orderNo : 20180602235049331002
         * orderDate : 2018-06-02 23:50:49
         * orverTime : 2018-06-03 23:50:49
         * requiredDate : null
         * address : null
         * payment : null
         * paymentType : 0
         * paymentTime : null
         * paymentStatus : 0
         * lat : null
         * lng : 0
         * location : 禹城书法家爱看的房
         * picture : null
         * type : 0
         * userName : null
         * shopName : null
         * serviceName : null
         * servicePicture : null
         * category_type : null
         * evaluateType : 0
         * pubDateStart : null
         * pubDateEnd : null
         */

        private int orderId;
        private int serviceId;
        private Object unitPrice;
        private int discount;
        private String content;
        private Object name;
        private String phone;
        private String status;
        private int accountId;
        private int shopId;
        private String orderNo;
        private String orderDate;
        private String orverTime;
        private Object requiredDate;
        private Object address;
        private Object payment;
        private int paymentType;
        private Object paymentTime;
        private int paymentStatus;
        private Object lat;
        private String lng;
        private String location;
        private Object picture;
        private int type;
        private Object userName;
        private Object shopName;
        private Object serviceName;
        private Object servicePicture;
        private Object category_type;
        private int evaluateType;
        private Object pubDateStart;
        private Object pubDateEnd;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public Object getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Object unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getOrverTime() {
            return orverTime;
        }

        public void setOrverTime(String orverTime) {
            this.orverTime = orverTime;
        }

        public Object getRequiredDate() {
            return requiredDate;
        }

        public void setRequiredDate(Object requiredDate) {
            this.requiredDate = requiredDate;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getPayment() {
            return payment;
        }

        public void setPayment(Object payment) {
            this.payment = payment;
        }

        public int getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(int paymentType) {
            this.paymentType = paymentType;
        }

        public Object getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(Object paymentTime) {
            this.paymentTime = paymentTime;
        }

        public int getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(int paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Object getLat() {
            return lat;
        }

        public void setLat(Object lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Object getPicture() {
            return picture;
        }

        public void setPicture(Object picture) {
            this.picture = picture;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getShopName() {
            return shopName;
        }

        public void setShopName(Object shopName) {
            this.shopName = shopName;
        }

        public Object getServiceName() {
            return serviceName;
        }

        public void setServiceName(Object serviceName) {
            this.serviceName = serviceName;
        }

        public Object getServicePicture() {
            return servicePicture;
        }

        public void setServicePicture(Object servicePicture) {
            this.servicePicture = servicePicture;
        }

        public Object getCategory_type() {
            return category_type;
        }

        public void setCategory_type(Object category_type) {
            this.category_type = category_type;
        }

        public int getEvaluateType() {
            return evaluateType;
        }

        public void setEvaluateType(int evaluateType) {
            this.evaluateType = evaluateType;
        }

        public Object getPubDateStart() {
            return pubDateStart;
        }

        public void setPubDateStart(Object pubDateStart) {
            this.pubDateStart = pubDateStart;
        }

        public Object getPubDateEnd() {
            return pubDateEnd;
        }

        public void setPubDateEnd(Object pubDateEnd) {
            this.pubDateEnd = pubDateEnd;
        }
    }
}
