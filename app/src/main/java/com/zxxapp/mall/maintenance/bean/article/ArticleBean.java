package com.zxxapp.mall.maintenance.bean.article;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Thinten on 2017-12-20
 * www.thinten.com
 * 9486@163.com.
 */

public class ArticleBean implements Serializable {

    /**
     * msg : success
     * error : 0
     * results : [{"id":2167,"title":"头部00","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012306298226.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2179,"title":"ddddd","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/13/201801132013018286.jpg","zhaiyao":"","add_time":"2018.01.13","url":"112","app_view":"one_img","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2157,"title":"头部01","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320233640.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2158,"title":"头部02","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320276879.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2159,"title":"脆冬枣","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320324728.jpg","zhaiyao":"2112","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2160,"title":"松花蛋","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320380671.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2161,"title":"黑木耳","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320451780.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2162,"title":"香菇","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320501572.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2163,"title":"紫菜","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012320543766.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""},{"id":2164,"title":"年货组合","img_url":"http://shop.zhenmeizhixiu.com/upload/201801/01/201801012321009670.jpg","zhaiyao":"","add_time":"2018.01.01","url":"","app_view":"","front_img":"","front_img_scaletype":"","bg_img":"","bg_img_scaletype":""}]
     * count : 17
     */

    private String msg;
    private int error;
    private int count;
    private List<ResultsBean> results;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {
        /**
         * id : 2167
         * title : 头部00
         * img_url : http://shop.zhenmeizhixiu.com/upload/201801/01/201801012306298226.jpg
         * zhaiyao :
         * add_time : 2018.01.01
         * url :
         * app_view :
         * front_img :
         * front_img_scaletype :
         * bg_img :
         * bg_img_scaletype :
         */

        private int id;
        private String title;
        private String img_url;
        private String zhaiyao;
        private String add_time;
        private String url;
        private String app_view;
        private String front_img;
        private String front_img_scaletype;
        private String bg_img;
        private String bg_img_scaletype;
        private String show_view;

        public String getShow_view() {
            return show_view;
        }

        public void setShow_view(String show_view) {
            this.show_view = show_view;
        }


        public String getText_color() {
            return text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }

        private String text_color;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getZhaiyao() {
            return zhaiyao;
        }

        public void setZhaiyao(String zhaiyao) {
            this.zhaiyao = zhaiyao;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApp_view() {
            return app_view;
        }

        public void setApp_view(String app_view) {
            this.app_view = app_view;
        }

        public String getFront_img() {
            return front_img;
        }

        public void setFront_img(String front_img) {
            this.front_img = front_img;
        }

        public String getFront_img_scaletype() {
            return front_img_scaletype;
        }

        public void setFront_img_scaletype(String front_img_scaletype) {
            this.front_img_scaletype = front_img_scaletype;
        }

        public String getBg_img() {
            return bg_img;
        }

        public void setBg_img(String bg_img) {
            this.bg_img = bg_img;
        }

        public String getBg_img_scaletype() {
            return bg_img_scaletype;
        }

        public void setBg_img_scaletype(String bg_img_scaletype) {
            this.bg_img_scaletype = bg_img_scaletype;
        }
    }
}
