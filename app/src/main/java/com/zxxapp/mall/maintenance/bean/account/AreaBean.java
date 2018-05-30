package com.zxxapp.mall.maintenance.bean.account;

import java.util.List;

/**
 * Created by Thinten on 2017-12-28
 * www.thinten.com
 * 9486@163.com.
 */

public class AreaBean {

    /**
     * msg : 获取
     * error : 1
     * data : [{"AreaId":"110000","ParentId":"0","AreaCode":"110000","AreaName":"北京","QuickQuery":"BeiJing","SimpleSpelling":"bj","Layer":1,"SortCode":110000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"120000","ParentId":"0","AreaCode":"120000","AreaName":"天津","QuickQuery":"TianJin","SimpleSpelling":"tj","Layer":1,"SortCode":120000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"130000","ParentId":"0","AreaCode":"130000","AreaName":"河北省","QuickQuery":"HeBeiSheng","SimpleSpelling":"hbs","Layer":1,"SortCode":130000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"140000","ParentId":"0","AreaCode":"140000","AreaName":"山西省","QuickQuery":"ShanXiSheng","SimpleSpelling":"sxs","Layer":1,"SortCode":140000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"150000","ParentId":"0","AreaCode":"150000","AreaName":"内蒙古自治区","QuickQuery":"NeiMengGuZiZhiQu","SimpleSpelling":"nmgzzq","Layer":1,"SortCode":150000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"210000","ParentId":"0","AreaCode":"210000","AreaName":"辽宁省","QuickQuery":"LiaoNingSheng","SimpleSpelling":"lns","Layer":1,"SortCode":210000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"220000","ParentId":"0","AreaCode":"220000","AreaName":"吉林省","QuickQuery":"JiLinSheng","SimpleSpelling":"jls","Layer":1,"SortCode":220000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"230000","ParentId":"0","AreaCode":"230000","AreaName":"黑龙江省","QuickQuery":"HeiLongJiangSheng","SimpleSpelling":"hljs","Layer":1,"SortCode":230000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"310000","ParentId":"0","AreaCode":"310000","AreaName":"上海","QuickQuery":"ShangHai","SimpleSpelling":"sh","Layer":1,"SortCode":310000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"320000","ParentId":"0","AreaCode":"320000","AreaName":"江苏省","QuickQuery":"JiangSuSheng","SimpleSpelling":"jss","Layer":1,"SortCode":320000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"330000","ParentId":"0","AreaCode":"330000","AreaName":"浙江省","QuickQuery":"ZheJiangSheng","SimpleSpelling":"zjs","Layer":1,"SortCode":330000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"340000","ParentId":"0","AreaCode":"340000","AreaName":"安徽省","QuickQuery":"AnHuiSheng","SimpleSpelling":"ahs","Layer":1,"SortCode":340000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"350000","ParentId":"0","AreaCode":"350000","AreaName":"福建省","QuickQuery":"FuJianSheng","SimpleSpelling":"fjs","Layer":1,"SortCode":350000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"360000","ParentId":"0","AreaCode":"360000","AreaName":"江西省","QuickQuery":"JiangXiSheng","SimpleSpelling":"jxs","Layer":1,"SortCode":360000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"370000","ParentId":"0","AreaCode":"370000","AreaName":"山东省","QuickQuery":"ShanDongSheng","SimpleSpelling":"sds","Layer":1,"SortCode":370000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"410000","ParentId":"0","AreaCode":"410000","AreaName":"河南省","QuickQuery":"HeNanSheng","SimpleSpelling":"hns","Layer":1,"SortCode":410000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"420000","ParentId":"0","AreaCode":"420000","AreaName":"湖北省","QuickQuery":"HuBeiSheng","SimpleSpelling":"hbs","Layer":1,"SortCode":420000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"430000","ParentId":"0","AreaCode":"430000","AreaName":"湖南省","QuickQuery":"HuNanSheng","SimpleSpelling":"hns","Layer":1,"SortCode":430000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"440000","ParentId":"0","AreaCode":"440000","AreaName":"广东省","QuickQuery":"GuangDongSheng","SimpleSpelling":"gds","Layer":1,"SortCode":440000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"450000","ParentId":"0","AreaCode":"450000","AreaName":"广西壮族自治区","QuickQuery":"GuangXiZhuangZuZiZhiQu","SimpleSpelling":"gxzzzzq","Layer":1,"SortCode":450000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"460000","ParentId":"0","AreaCode":"460000","AreaName":"海南省","QuickQuery":"HaiNanSheng","SimpleSpelling":"hns","Layer":1,"SortCode":460000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"500000","ParentId":"0","AreaCode":"500000","AreaName":"重庆","QuickQuery":"ZhongQing","SimpleSpelling":"zq","Layer":1,"SortCode":500000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"510000","ParentId":"0","AreaCode":"510000","AreaName":"四川省","QuickQuery":"SiChuanSheng","SimpleSpelling":"scs","Layer":1,"SortCode":510000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"520000","ParentId":"0","AreaCode":"520000","AreaName":"贵州省","QuickQuery":"GuiZhouSheng","SimpleSpelling":"gzs","Layer":1,"SortCode":520000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"530000","ParentId":"0","AreaCode":"530000","AreaName":"云南省","QuickQuery":"YunNanSheng","SimpleSpelling":"yns","Layer":1,"SortCode":530000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"540000","ParentId":"0","AreaCode":"540000","AreaName":"西藏自治区","QuickQuery":"XiCangZiZhiQu","SimpleSpelling":"xzzzq","Layer":1,"SortCode":540000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"610000","ParentId":"0","AreaCode":"610000","AreaName":"陕西省","QuickQuery":"ShanXiSheng","SimpleSpelling":"sxs","Layer":1,"SortCode":610000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"620000","ParentId":"0","AreaCode":"620000","AreaName":"甘肃省","QuickQuery":"GanSuSheng","SimpleSpelling":"gss","Layer":1,"SortCode":620000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"630000","ParentId":"0","AreaCode":"630000","AreaName":"青海省","QuickQuery":"QingHaiSheng","SimpleSpelling":"qhs","Layer":1,"SortCode":630000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"640000","ParentId":"0","AreaCode":"640000","AreaName":"宁夏回族自治区","QuickQuery":"NingXiaHuiZuZiZhiQu","SimpleSpelling":"nxhzzzq","Layer":1,"SortCode":640000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"650000","ParentId":"0","AreaCode":"650000","AreaName":"新疆维吾尔自治区","QuickQuery":"XinJiangWeiWuErZiZhiQu","SimpleSpelling":"xjwwezzq","Layer":1,"SortCode":650000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"810000","ParentId":"0","AreaCode":"810000","AreaName":"香港特别行政区","QuickQuery":"XiangGangTeBieXingZhengQu","SimpleSpelling":"xgtbxzq","Layer":1,"SortCode":810000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"820000","ParentId":"0","AreaCode":"820000","AreaName":"澳门特别行政区","QuickQuery":"AoMenTeBieXingZhengQu","SimpleSpelling":"amtbxzq","Layer":1,"SortCode":820000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null},{"AreaId":"830000","ParentId":"0","AreaCode":"830000","AreaName":"台湾省","QuickQuery":"TaiWanSheng","SimpleSpelling":"tws","Layer":1,"SortCode":830000,"DeleteMark":0,"EnabledMark":1,"Description":"","PCode":null,"CCode":null,"ACode":null,"PName":null,"CName":null,"AName":null}]
     */

    private String msg;
    private int error;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * AreaId : 110000
         * ParentId : 0
         * AreaCode : 110000
         * AreaName : 北京
         * QuickQuery : BeiJing
         * SimpleSpelling : bj
         * Layer : 1
         * SortCode : 110000
         * DeleteMark : 0
         * EnabledMark : 1
         * Description :
         * PCode : null
         * CCode : null
         * ACode : null
         * PName : null
         * CName : null
         * AName : null
         */

        private String AreaId;
        private String ParentId;
        private String AreaCode;
        private String AreaName;
        private String QuickQuery;
        private String SimpleSpelling;
        private int Layer;
        private int SortCode;
        private int DeleteMark;
        private int EnabledMark;
        private String Description;
        private Object PCode;
        private Object CCode;
        private Object ACode;
        private Object PName;
        private Object CName;
        private Object AName;

        public String getAreaId() {
            return AreaId;
        }

        public void setAreaId(String AreaId) {
            this.AreaId = AreaId;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getAreaCode() {
            return AreaCode;
        }

        public void setAreaCode(String AreaCode) {
            this.AreaCode = AreaCode;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getQuickQuery() {
            return QuickQuery;
        }

        public void setQuickQuery(String QuickQuery) {
            this.QuickQuery = QuickQuery;
        }

        public String getSimpleSpelling() {
            return SimpleSpelling;
        }

        public void setSimpleSpelling(String SimpleSpelling) {
            this.SimpleSpelling = SimpleSpelling;
        }

        public int getLayer() {
            return Layer;
        }

        public void setLayer(int Layer) {
            this.Layer = Layer;
        }

        public int getSortCode() {
            return SortCode;
        }

        public void setSortCode(int SortCode) {
            this.SortCode = SortCode;
        }

        public int getDeleteMark() {
            return DeleteMark;
        }

        public void setDeleteMark(int DeleteMark) {
            this.DeleteMark = DeleteMark;
        }

        public int getEnabledMark() {
            return EnabledMark;
        }

        public void setEnabledMark(int EnabledMark) {
            this.EnabledMark = EnabledMark;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public Object getPCode() {
            return PCode;
        }

        public void setPCode(Object PCode) {
            this.PCode = PCode;
        }

        public Object getCCode() {
            return CCode;
        }

        public void setCCode(Object CCode) {
            this.CCode = CCode;
        }

        public Object getACode() {
            return ACode;
        }

        public void setACode(Object ACode) {
            this.ACode = ACode;
        }

        public Object getPName() {
            return PName;
        }

        public void setPName(Object PName) {
            this.PName = PName;
        }

        public Object getCName() {
            return CName;
        }

        public void setCName(Object CName) {
            this.CName = CName;
        }

        public Object getAName() {
            return AName;
        }

        public void setAName(Object AName) {
            this.AName = AName;
        }
    }
}
