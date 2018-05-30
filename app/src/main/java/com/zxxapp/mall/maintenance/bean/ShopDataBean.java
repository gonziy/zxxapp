package com.zxxapp.mall.maintenance.bean;

import com.example.http.ParamNames;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jingbin on 2016/11/24.
 */

public class ShopDataBean implements Serializable {

    @ParamNames("error")
    private Integer error;
    @ParamNames("msg")
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @ParamNames("count")
    private Integer count;
    /**
     * _id : 5832662b421aa929b0f34e99
     * createdAt : 2016-11-21T11:12:43.567Z
     * desc :  深入Android渲染机制
     * publishedAt : 2016-11-24T11:40:53.615Z
     * source : web
     * type : Android
     * url : http://blog.csdn.net/ccj659/article/details/53219288
     * used : true
     * who : Chauncey
     */

    @ParamNames("results")
    private List<ResultBean> results;

    public static class ResultBean implements Serializable {

        @ParamNames("id")
        private String id;
        @ParamNames("title")
        private String title;

        @ParamNames("subtitle")
        private String subtitle;
        @ParamNames("stock_quantity")
        private int stock_quantity;
        @ParamNames("market_price")
        private String market_price;
        @ParamNames("sell_price")
        private String sell_price;
        @ParamNames("img_url")
        private String img_url;
        @ParamNames("is_hot")
        private String is_hot;
        @ParamNames("is_slide")
        private String is_slide;
        @ParamNames("is_top")
        private String is_top;

        public String getShow_view() {
            return show_view;
        }

        public void setShow_view(String show_view) {
            this.show_view = show_view;
        }

        @ParamNames("show_view")
        private String show_view;



        @ParamNames("text_color")
        private String text_color;
        public String getText_color() {
            return text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }



        public int getStock_quantity() {
            return stock_quantity;
        }

        public void setStock_quantity(int stock_quantity) {
            this.stock_quantity = stock_quantity;
        }
        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getIs_slide() {
            return is_slide;
        }

        public void setIs_slide(String is_slide) {
            this.is_slide = is_slide;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }


        public String getMarketPrice() {
            return market_price;
        }

        public String getSellPrice() {
            return sell_price;
        }

        public String getImgUrl() {
            return img_url;
        }


        @Override
        public String toString() {
            return "ResultsBean{" +
                    "is_slide='" + is_slide + '\'' +
                    ",is_hot='" + is_hot + '\'' +
                    ",img_url='" + img_url + '\'' +
                    ", sell_price='" + sell_price + '\'' +
                    ", market_price='" + market_price + '\'' +
                    ", stock_quantity='" + stock_quantity + '\'' +
                    ", title='" + title + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

    }

    public Integer isError() {
        return error;
    }

    public List<ResultBean> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ShopDataBean{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
