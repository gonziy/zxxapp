package com.zxxapp.mall.maintenance.bean.payment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thinten on 2018-05-15
 * www.thinten.com
 * 9486@163.com.
 */
public class PayBean {

    /**
     * msg : 微信支付提交成功
     * code : 200
     * data : {"appid":"wxcdf286664ebabde6","noncestr":"b84UDRmYVTDmwdEE","package":"Sign=WXPay","partnerid":"1503558781","prepayid":"wx150600131632645e0764c43e0822168587","sign":"1E12A2F08B15BD822858754333A7F7A0","timestamp":1526335213}
     */

    private String msg;
    private String code;
    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wxcdf286664ebabde6
         * noncestr : b84UDRmYVTDmwdEE
         * package : Sign=WXPay
         * partnerid : 1503558781
         * prepayid : wx150600131632645e0764c43e0822168587
         * sign : 1E12A2F08B15BD822858754333A7F7A0
         * timestamp : 1526335213
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private int timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }
    }
}
