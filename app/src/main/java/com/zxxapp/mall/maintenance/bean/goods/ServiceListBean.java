package com.zxxapp.mall.maintenance.bean.goods;

import java.util.List;

/**
 * Created by Thinten on 2018-04-15
 * www.thinten.com
 * 9486@163.com.
 */
public class ServiceListBean {

    /**
     * data : {"list":[{"serviceId":3,"categoryId":1,"serviceName":"马桶","description":"马桶2333","picture":"http://localhost:8080/zxxapp/upload/productImgs/1523679409272.jpg","flag":"1","categoryType":"w","categoryName":null}]}
     * success : true
     * token : null
     */

    private DataBean data;
    private boolean success;
    private Object token;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * serviceId : 3
             * categoryId : 1
             * serviceName : 马桶
             * description : 马桶2333
             * picture : http://localhost:8080/zxxapp/upload/productImgs/1523679409272.jpg
             * flag : 1
             * categoryType : w
             * categoryName : null
             */

            private int serviceId;
            private int categoryId;
            private String serviceName;
            private String description;
            private String picture;
            private String flag;
            private String categoryType;
            private Object categoryName;

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getCategoryType() {
                return categoryType;
            }

            public void setCategoryType(String categoryType) {
                this.categoryType = categoryType;
            }

            public Object getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(Object categoryName) {
                this.categoryName = categoryName;
            }
        }
    }
}
