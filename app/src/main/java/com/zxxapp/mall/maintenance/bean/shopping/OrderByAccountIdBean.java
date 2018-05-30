package com.zxxapp.mall.maintenance.bean.shopping;

import java.util.List;

/**
 * Created by Thinten on 2018-05-14
 * www.thinten.com
 * 9486@163.com.
 */
public class OrderByAccountIdBean {

    /**
     * dataRows : [{"orderId":1,"serviceId":1,"unitPrice":34,"discount":4,"content":"马桶","name":"王","phone":"13334995","status":"1","accounId":1,"shopId":1,"orderNo":"123345566","orderDate":"2018-04-07 08:38:56","requiredDate":"2018-04-07 09:10:10","address":"山东禹城","payment":34.23,"paymentType":1,"paymentTime":"2018-04-07 09:10:10","paymentStatus":1,"lat":"232","lng":"22","location":"禹王街","picture":null,"type":0,"userName":"admin","shopName":"维修商店","serviceName":"卫浴","pubDateStart":null,"pubDateEnd":null,"accountId":1}]
     * total : 1
     * success : true
     */

    private int total;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    private boolean success;
    private List<DataRowsBean> dataRows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataRowsBean> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRowsBean> dataRows) {
        this.dataRows = dataRows;
    }

    public static class DataRowsBean {
        /**
         * orderId : 1
         * serviceId : 1
         * unitPrice : 34
         * discount : 4
         * content : 马桶
         * name : 王
         * phone : 13334995
         * status : 1
         * accounId : 1
         * shopId : 1
         * orderNo : 123345566
         * orderDate : 2018-04-07 08:38:56
         * requiredDate : 2018-04-07 09:10:10
         * address : 山东禹城
         * payment : 34.23
         * paymentType : 1
         * paymentTime : 2018-04-07 09:10:10
         * paymentStatus : 1
         * lat : 232
         * lng : 22
         * location : 禹王街
         * picture : null
         * type : 0
         * userName : admin
         * shopName : 维修商店
         * serviceName : 卫浴
         * pubDateStart : null
         * pubDateEnd : null
         * accountId : 1
         */

        private int orderId;
        private int serviceId;
        private Double unitPrice;
        private int discount;
        private String content;
        private String name;
        private String phone;
        private String status;
        private int accounId;
        private int shopId;
        private String orderNo;
        private String orderDate;
        private String requiredDate;
        private String address;
        private Object payment;
        private int paymentType;
        private String paymentTime;
        private int paymentStatus;
        private String lat;
        private String lng;
        private String location;
        private Object picture;
        private int type;
        private String userName;
        private String shopName;
        private String serviceName;
        private Object pubDateStart;
        private Object pubDateEnd;
        private int accountId;

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

        public Double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(Double unitPrice) {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
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

        public int getAccounId() {
            return accounId;
        }

        public void setAccounId(int accounId) {
            this.accounId = accounId;
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

        public String getRequiredDate() {
            return requiredDate;
        }

        public void setRequiredDate(String requiredDate) {
            this.requiredDate = requiredDate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
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

        public String getPaymentTime() {
            return paymentTime;
        }

        public void setPaymentTime(String paymentTime) {
            this.paymentTime = paymentTime;
        }

        public int getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(int paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
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

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }
    }
}
