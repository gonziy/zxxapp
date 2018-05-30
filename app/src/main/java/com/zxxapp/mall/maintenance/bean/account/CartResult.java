package com.zxxapp.mall.maintenance.bean.account;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Thinten on 2017-12-16
 * www.thinten.com
 * 9486@163.com.
 */

public class CartResult implements Serializable {

    /**
     * msg : 验证成功
     * error : -1
     * data : [{"ID":347,"selected":0,"article_id":2137,"goods_id":0,"goods_no":"","goods_type":0,"title":"雪肌多肽焕彩修颜洁面慕斯","spec_text":"","img_url":"/upload/201710/30/201710301213465214.jpg","sell_price":47.6,"user_price":47.6,"point":0,"quantity":1,"user_id":5902,"add_time":"2017-12-16T21:16:57","goods_last_version":"2017-01-01T00:00:00","invalid":true,"stock_quantity":0,"seller_id":0},{"ID":346,"selected":0,"article_id":2135,"goods_id":0,"goods_no":"","goods_type":0,"title":"雪肌多肽焕彩修颜乳","spec_text":"","img_url":"/upload/201710/30/201710301213336987.jpg","sell_price":76.6,"user_price":76.6,"point":0,"quantity":1,"user_id":5902,"add_time":"2017-12-16T21:16:56","goods_last_version":"2017-01-01T00:00:00","invalid":true,"stock_quantity":0,"seller_id":0}]
     */

    private String msg;
    private String error;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * ID : 347
         * selected : 0
         * article_id : 2137
         * goods_id : 0
         * goods_no :
         * goods_type : 0
         * title : 雪肌多肽焕彩修颜洁面慕斯
         * spec_text :
         * img_url : /upload/201710/30/201710301213465214.jpg
         * sell_price : 47.6
         * user_price : 47.6
         * point : 0
         * quantity : 1
         * user_id : 5902
         * add_time : 2017-12-16T21:16:57
         * goods_last_version : 2017-01-01T00:00:00
         * invalid : true
         * stock_quantity : 0
         * seller_id : 0
         */

        private int ID;
        private int selected;
        private int article_id;
        private int goods_id;
        private String goods_no;
        private int goods_type;
        private String title;
        private String spec_text;
        private String img_url;
        private double sell_price;
        private double user_price;
        private int point;
        private int quantity;
        private int user_id;
        private String add_time;
        private String goods_last_version;
        private boolean invalid;
        private int stock_quantity;
        private int seller_id;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getSelected() {
            return selected;
        }

        public void setSelected(int selected) {
            this.selected = selected;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public int getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(int goods_type) {
            this.goods_type = goods_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSpec_text() {
            return spec_text;
        }

        public void setSpec_text(String spec_text) {
            this.spec_text = spec_text;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public double getSell_price() {
            return sell_price;
        }

        public void setSell_price(double sell_price) {
            this.sell_price = sell_price;
        }

        public double getUser_price() {
            return user_price;
        }

        public void setUser_price(double user_price) {
            this.user_price = user_price;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getGoods_last_version() {
            return goods_last_version;
        }

        public void setGoods_last_version(String goods_last_version) {
            this.goods_last_version = goods_last_version;
        }

        public boolean isInvalid() {
            return invalid;
        }

        public void setInvalid(boolean invalid) {
            this.invalid = invalid;
        }

        public int getStock_quantity() {
            return stock_quantity;
        }

        public void setStock_quantity(int stock_quantity) {
            this.stock_quantity = stock_quantity;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }
    }
}
