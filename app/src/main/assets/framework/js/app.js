var appId="d2FuZ2NoYW5ncWluZw..";
var secret="U2h4eWdsMTM3Ny4.";
var unitUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getSourceUnitCount.action";
var penaltysUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getPenaltys.action";
var penaltyByIdUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getPenaltyById.action";
var licensesUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getLicenses.action";
var licenseByIdUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getLicenseById.action";
var newsUrl = "http://www.xinyongkenli.gov.cn/seam/resource/rest/xykl/article/";
var catalogUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getMobileCatalog.action";
var getCorporationUrl = "http://218.58.213.49:8080/CreditManager/Mobile/mobile!getCorporation.action";
var imgNewsUrl = "http://www.xinyongkenli.gov.cn/seam/resource/rest/xykl/pictureArticle/8/1"

function getMobileCatalog() {
    $.get(catalogUrl, {
        "eap_username": appId,
        "eap_password": secret
    }, function (data) {
        var jsonObject = eval("(" + data + ")");
        $("#unitList").html("");
        var html = "";
        for(var i=0;i<jsonObject.length;i++){
            var itemHtml = "";
            var unitName = jsonObject[i].unitName;
            var catalogCount = jsonObject[i].catalogCount;

            itemHtml += "<li class=\"item-content block detail\" onclick='showorhide(this)'><div class=\"item-inner\"><div class=\"item-title\">"+unitName+"</div><div class=\"item-after\"><span class=\"badge\">"+catalogCount+"</span></div></div>";
            var catalogs = jsonObject[i].catalogs;
            if(catalogs.length>0){
                itemHtml += "<div class=\"item-content block detaillist\" style=\"display:none\"><i class=\"arrow\"></i>";
                for(var ci = 0; ci < catalogs.length; ci++){
                    itemHtml += "<div class=\"item-inner\"><div class=\"item-title\">"+ catalogs[ci].catalogName +"</div><div class=\"item-after\"><span class=\"badge\">"+catalogs[ci].count+"</span></div></div><div class=\"item-text\" style='font-size: .55rem'>更新时间："+catalogs[ci].updateDate+"</div>";
                }
                itemHtml += "</div>";
            }
            itemHtml += "</li>";
            html += itemHtml;
        }
        $("#unitList").append(html);

    })
}
function showorhide(obj) {
    var isshow = $(obj).children(".detaillist").css("display");
    if(isshow=="block"){
        $(obj).children(".detaillist").hide();
    }else{
        $(obj).children(".detaillist").show();
    }
}
function getCorporation(keyword){
    if(keyword.length>0) {
        var kw = encodeURI(keyword);
        $("#companyList").html("");
        $.get(getCorporationUrl, {
            "eap_username": appId,
            "eap_password": secret,
            "condition":kw
        }, function (data) {
            var jsonObject = eval("(" + data + ")");
            var corporationArr = jsonObject.corporationArr;
            for (var i = 0; i < corporationArr.length; i++) {
                var itemCorporation = corporationArr[i];
                $("#companyList").append("<li onclick=\"showCompanyUrl('"+itemCorporation.detailLink+"')\"><a href=\"javascript:;\" class=\"item-link item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">"+itemCorporation.cnname+"</div></div><div class=\"item-subtitle\">社会统一信用代码："+itemCorporation.serialnumber+"</div><div class=\"item-text\">法定代表人："+itemCorporation.principalcnname+"</div></div></a></li>");
            }
        })
    }
}
function showCompanyUrl(url) {
    location.href = url;
    // $(".popup-detail").fadeIn();
    // $(".popup-detail").css("transform", "translate3d(0, 0, 0)");
    // //alert("<iframe src='"+ decodeURI(decodeURI(url))+"' width='" + ($(document.body).width() - 0) + "' height='" + ($(document.body).height() - 75 ) + "' frameborder='0' scrolling='auto'></iframe>");
    // $(".popup-detail .content #detail-info").html("");
    // $(".popup-detail .content #detail-info").html("<iframe src='"+ decodeURI(decodeURI(url))+"' width='" + ($(document.body).width() - 0) + "' height='" + ($(document.body).height() - 75 ) + "' frameborder='0' scrolling='auto'></iframe>");
}
function getSourceUnitCount() {
    var cnName = "";
    var pageIndex = $(".kl-more").attr("page");
    var pageCount = $(".kl-more").attr("pagecount");
    $.get(unitUrl, {
        "eap_username": appId,
        "eap_password": secret,
        "pageNumber": pageIndex,
        "cnname": cnName
    }, function (data) {
        var jsonObject = eval("(" + data + ")");
        var unitArr = jsonObject.unitArr;
        $("#unitList").html("");
        for (var i = 0; i < unitArr.length; i++) {
            var itemUnit = unitArr[i];
            $("#unitList").append("<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title\">" + itemUnit.unitName + "</div><div class=\"item-after\"><span class=\"badge\">" + itemUnit.count + "</span></div></div></li>");
        }
        $(".kl-more").attr("pagecount", (parseInt(jsonObject.totalCount / 10) + 1).toString());
        pageCount = $(".kl-more").attr("pagecount");
        if(pageIndex >= pageCount){
            $(".kl-more").text("没有更多了");
        }else {
            $(".kl-more").text("查看更多");
        }
    })
}
function getSourceUnitCountNext() {
    var keyword = $("#unitKeyword").val();
    var cnName = "";
    if(keyword.length > 0){
        cnName = encodeURI(keyword);
    }
    var pageIndex = parseInt($(".kl-more").attr("page")) + 1;
    var pageCount = $(".kl-more").attr("pagecount");
    if (pageIndex <= pageCount) {
        $.get(unitUrl, {
            "eap_username": appId,
            "eap_password": secret,
            "pageNumber": pageIndex,
            "cnname": cnName
        }, function (data) {
            var jsonObject = eval("(" + data + ")");
            var unitArr = jsonObject.unitArr;
            for (var i = 0; i < unitArr.length; i++) {
                var itemUnit = unitArr[i];
                $("#unitList").append("<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title\">" + itemUnit.unitName + "</div><div class=\"item-after\"><span class=\"badge\">" + itemUnit.count + "</span></div></div></li>");
            }
            $(".kl-more").attr("page", pageIndex);
            if(pageIndex >= pageCount){
                $(".kl-more").text("没有更多了");
            }else {
                $(".kl-more").text("查看更多");
            }
        })
    }
}
function searchSourceUnit() {
    var keyword = $("#unitKeyword").val();
    var cnName = "";
    if (keyword.length > 0) {
        cnName = encodeURI(keyword);
    }
    var pageIndex = 1;
    $(".kl-more").attr("page", "1");
    var pageCount = $(".kl-more").attr("pagecount");
    $("#unitList").html("");
    $.get(unitUrl, {
        "eap_username": appId,
        "eap_password": secret,
        "pageNumber": pageIndex,
        "cnname": cnName
    }, function (data) {
        var jsonObject = eval("(" + data + ")");
        var unitArr = jsonObject.unitArr;
        for (var i = 0; i < unitArr.length; i++) {
            var itemUnit = unitArr[i];
            $("#unitList").append("<li class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title\">" + itemUnit.unitName + "</div><div class=\"item-after\"><span class=\"badge\">" + itemUnit.count + "</span></div></div></li>");
        }
        $(".kl-more").attr("pagecount", (parseInt(jsonObject.totalCount / 10) + 1).toString());
        pageCount = $(".kl-more").attr("pagecount");
        if (pageIndex >= pageCount) {
            $(".kl-more").text("没有更多了");
        } else {
            $(".kl-more").text("查看更多");
        }
    })
}
function searchPenaltys() {
    var keyword = $("#penaltysKeyword").val();
    var subjectName = "";
    if (keyword.length > 0) {
        subjectName = encodeURI(keyword);
    }
    var pageIndex = 1;
    $(".kl-more").attr("page","1");
    var pageCount = $(".kl-more").attr("pagecount");
    $("#penaltysList").html("");
    $.get(penaltysUrl, {
        "eap_username": appId,
        "eap_password": secret,
        "pageNumber": pageIndex,
        "subjectName": subjectName
    }, function (data) {
        var jsonObject = eval("(" + data + ")");
        var penaltyArr = jsonObject.penaltyArr;
        for (var i = 0; i < penaltyArr.length; i++) {
            var itemPenalty = penaltyArr[i];
            $("#penaltysList").append("<li class=\"item\" onclick=\"showPenalty('"+itemPenalty.id+"')\"><a href=\"javascript:;\" class=\"item-link item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">"+itemPenalty.creditSubjectName+"</div></div><div class=\"item-subtitle\">处罚机关:"+itemPenalty.govName+"</div><div class=\"item-text\"><!--更新时间:暂无--></div></div></a></li>")
        }
        $(".kl-more").show();
        $(".kl-more").attr("pagecount", (parseInt(jsonObject.totalCount / 10) + 1).toString());
        pageCount = $(".kl-more").attr("pagecount");
        if(pageIndex >= pageCount){
            $(".kl-more").text("没有更多了");
        }else {
            $(".kl-more").text("查看更多");
        }
    })
}
function getPenaltysNext() {
    var keyword = $("#penaltysKeyword").val();
    var subjectName = "";
    if(keyword.length > 0){
        subjectName = encodeURI(keyword);
    }
    var pageIndex = parseInt($(".kl-more").attr("page")) + 1;
    var pageCount = $(".kl-more").attr("pagecount");
    if (pageIndex <= pageCount) {
        $.get(penaltysUrl, {
            "eap_username": appId,
            "eap_password": secret,
            "pageNumber": pageIndex,
            "subjectName": subjectName
        }, function (data) {
            var jsonObject = eval("(" + data + ")");
            var penaltyArr = jsonObject.penaltyArr;
            for (var i = 0; i < penaltyArr.length; i++) {
                var itemPenalty = penaltyArr[i];
                $("#penaltysList").append("<li class=\"item\" onclick=\"showPenalty('"+itemPenalty.id+"')\"><a href=\"javascript:;\" class=\"item-link item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">"+itemPenalty.creditSubjectName+"</div></div><div class=\"item-subtitle\">处罚机关:"+itemPenalty.govName+"</div><div class=\"item-text\"><!--更新时间:暂无--></div></div></a></li>")
            }
            $(".kl-more").attr("page", pageIndex);
            if(pageIndex >= pageCount){
                $(".kl-more").text("没有更多了");
            }else {
                $(".kl-more").text("查看更多");
            }
        })
    }
}
function showPenalty(id) {
    $(".popup-detail").fadeIn();
    $(".popup-detail").css("transform","translate3d(0, 0, 0)");
    $(".popup-detail .content .content-block #detail-info").html("");
    if(id.length>0){
        $.get(penaltyByIdUrl,{
            "eap_username": appId,
            "eap_password": secret,
            "id": id
        },function (data) {
            var jsonObject = eval("(" + data + ")");
            for(var item in jsonObject){
                if(item!="id"&& item!="isdel") {
                    var key = "";
                    switch (item) {
                        case "admimdocno":
                            key = "行政许可决定书文号";
                            break;
                        case "projectname":
                            key = "处罚名称";
                            break;
                        case "type":
                            key = "处罚类别1";
                            break;
                        case "typeremark":
                            key = "处罚类别2";
                            break;
                        case "content":
                            key = "处罚事由";
                            break;
                        case "reason":
                            key = "处罚依据";
                            break;
                        case "creditsubjectname":
                            key = "行政相对人名称";
                            break;
                        case "creditsubjectcode1":
                            key = "统一社会信用代码";
                            break;
                        case "creditsubjectcode2":
                            key = "组织机构代码";
                            break;
                        case "creditsubjectcode3":
                            key = "工商登记码";
                            break;
                        case "creditsubjectcode4":
                            key = "税务登记号";
                            break;
                        case "creditsubjectcode5":
                            key = "居民身份证号";
                            break;
                        case "legalpersonname":
                            key = "法定代表人姓名";
                            break;
                        case "handleresult":
                            key = "处罚结果";
                            break;
                        case "handledate":
                            key = "处罚决定日期";
                            break;
                        case "govname":
                            key = "处罚机关";
                            break;
                        case "stat":
                            key = "当前状态";
                            break;
                        case "regioncode":
                            key = "地方编码";
                            break;
                        case "updatetime":
                            key = "数据更新时间";
                            break;
                        case "remark":
                            key = "备注";
                            break;
                        case "chargedepartment":
                            key = "主管部门";
                            break;
                        default:
                            key = "";
                            break;
                    }
                    var value = "";
                    if(key!="") {
                        if (item == "updatetime") {
                            if (jsonObject[item]["time"] != "") {
                                value = timetrans(jsonObject[item]["time"]);
                            }
                        } else if (item == "handledate") {
                            if (jsonObject[item]["time"] != "") {
                                value = timetrans(jsonObject[item]["time"]);
                            }
                        } else if (item == "stat") {
                            switch (value) {
                                case 1:
                                    value = "撤销";
                                    break;
                                case 2:
                                    value = "异议";
                                    break;
                                case 3:
                                    value = "其他";
                                    break;
                                default:
                                    value = "正常";
                                    break;
                            }
                        }
                        else {
                            value = jsonObject[item];

                        }
                        if(value==null){
                            value ="";
                        }
                        if (value.length >= 0) {
                            $(".popup-detail .content .content-block #detail-info").append("<li><div class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title label\">" + key + "</div><div class=\"item-input\">" + value + "</div></div></div></li>");
                        }
                    }
                }
            }
        })
    }
}
function closeDetail() {
    $(".popup-detail").fadeOut();
    $(".popup-detail .content .content-block #detail-info").html("");
}
function searchLicenses() {
    var keyword = $("#licensesKeyword").val();
    var subjectName = "";
    if (keyword.length > 0) {
        subjectName = encodeURI(keyword);
    }
    var pageIndex = 1;
    $(".kl-more").attr("page","1");
    var pageCount = $(".kl-more").attr("pagecount");
    $("#licensesList").html("");
    $.get(licensesUrl, {
        "eap_username": appId,
        "eap_password": secret,
        "pageNumber": pageIndex,
        "subjectName": subjectName
    }, function (data) {
        var jsonObject = eval("(" + data + ")");
        var licenseArr = jsonObject.licenseArr;
        for (var i = 0; i < licenseArr.length; i++) {
            var itemLicense = licenseArr[i];
            $("#licensesList").append("<li class=\"item\" onclick=\"showLicense('"+itemLicense.id+"')\"><a href=\"javascript:;\" class=\"item-link item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">"+itemLicense.creditSubjectName+"</div></div><div class=\"item-subtitle\">许可机关:"+itemLicense.govName+"</div><div class=\"item-text\"><!--更新时间:暂无--></div></div></a></li>")
        }
        $(".kl-more").show();
        $(".kl-more").attr("pagecount", (parseInt(jsonObject.totalCount / 10) + 1).toString());
        pageCount = $(".kl-more").attr("pagecount");
        if(pageIndex >= pageCount){
            $(".kl-more").text("没有更多了");
        }else {
            $(".kl-more").text("查看更多");
        }
    })
}
function getLicensesNext() {
    var keyword = $("#licensesKeyword").val();
    var subjectName = "";
    if(keyword.length > 0){
        subjectName = encodeURI(keyword);
    }
    var pageIndex = parseInt($(".kl-more").attr("page")) + 1;
    var pageCount = $(".kl-more").attr("pagecount");
    if (pageIndex <= pageCount) {
        $.get(licensesUrl, {
            "eap_username": appId,
            "eap_password": secret,
            "pageNumber": pageIndex,
            "subjectName": subjectName
        }, function (data) {
            var jsonObject = eval("(" + data + ")");
            var licenseArr = jsonObject.licenseArr;
            for (var i = 0; i < licenseArr.length; i++) {
                var itemLicense = licenseArr[i];
                $("#licensesList").append("<li class=\"item\" onclick=\"showLicense('"+itemLicense.id+"')\"><a href=\"javascript:;\" class=\"item-link item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">"+itemLicense.creditSubjectName+"</div></div><div class=\"item-subtitle\">许可机关:"+itemLicense.govName+"</div><div class=\"item-text\"><!--更新时间:暂无--></div></div></a></li>")
            }
            $(".kl-more").attr("page", pageIndex);
            if(pageIndex >= pageCount){
                $(".kl-more").text("没有更多了");
            }else {
                $(".kl-more").text("查看更多");
            }
        })
    }
}
function showLicense(id) {
    $(".popup-detail").fadeIn();
    $(".popup-detail").css("transform", "translate3d(0, 0, 0)");
    $(".popup-detail .content .content-block #detail-info").html("");
    if (id.length > 0) {
        $.get(licenseByIdUrl, {
            "eap_username": appId,
            "eap_password": secret,
            "id": id
        }, function (data) {
            var jsonObject = eval("(" + data + ")");
            for (var item in jsonObject) {
                if (item != "id" && item != "isdel") {
                    var key = "";
                    switch (item) {
                        case "admimdocno":
                            key = "行政许可决定书文号";
                            break;
                        case "projectname":
                            key = "项目名称";
                            break;
                        case "type":
                            key = "审批类别";
                            break;
                        case "content":
                            key = "许可内容";
                            break;
                        case "creditsubjectname":
                            key = "行政相对人名称";
                            break;
                        case "creditsubjectcode1":
                            key = "统一社会信用代码";
                            break;
                        case "creditsubjectcode2":
                            key = "组织机构代码";
                            break;
                        case "creditsubjectcode3":
                            key = "工商登记码";
                            break;
                        case "creditsubjectcode4":
                            key = "税务登记号";
                            break;
                        case "creditsubjectcode5":
                            key = "居民身份证号";
                            break;
                        case "legalpersonname":
                            key = "法定代表人姓名";
                            break;
                        case "handledate":
                            key = "许可决定日期";
                            break;
                        case "expirationdate":
                            key = "许可截止期";
                            break;
                        case "govname":
                            key = "许可机关";
                            break;
                        case "stat":
                            key = "状态";
                            break;
                        case "regioncode":
                            key = "地方编码";
                            break;
                        case "updatetime":
                            key = "数据更新时间";
                            break;
                        case "remark":
                            key = "备注";
                            break;
                        case "chargedepartment":
                            key = "主管部门";
                            break;
                        case "typeremark":
                            key = "类别备注";
                            break;
                        case "chargedepartment":
                            key = "主管部门";
                            break;
                        default:
                            key = "";
                            break;
                    }
                    if(key!="") {
                        var value = "";
                        if (item == "updatetime") {
                            if (jsonObject[item]["time"] != "") {
                                value = timetrans(jsonObject[item]["time"]);
                            }
                        }else if(item=="expirationdate"){
                            if (jsonObject[item] != "") {
                                value = timetrans(jsonObject[item]);
                            }
                        }else if (item == "handledate") {
                            if (jsonObject[item]["time"] != "") {
                                value = timetrans(jsonObject[item]["time"]);
                            }
                        } else if (item == "stat") {
                            switch (value) {
                                case 1:
                                    value = "撤销";
                                    break;
                                case 2:
                                    value = "异议";
                                    break;
                                case 3:
                                    value = "其他";
                                    break;
                                default:
                                    value = "正常";
                                    break;
                            }
                        }
                        else {
                            value = jsonObject[item];
                        }
                        if(value==null){
                            value ="";
                        }
                        if (key.length >= 0) {
                            $(".popup-detail .content .content-block #detail-info").append("<li><div class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title label\">" + key + "</div><div class=\"item-input\">" + value + "</div></div></div></li>");
                        }
                    }
                }
            }
        })
    }
}

function getNews(title,list_id) {
    var cagegoryName = encodeURI(encodeURI(title));
    var pageIndex = $("#"+list_id+"more").attr("page");
    var pageCount = $("#"+list_id+"more").attr("pagecount");
    var pageSize = 10;
    $.get(newsUrl + cagegoryName + "/"+pageSize+"/"+pageIndex, function (data) {
        var jsonObject = eval("(" + data + ")");
        var articleArr = jsonObject.articleArr;
        $("#"+list_id).html("");
        for (var i = 0; i < articleArr.length; i++) {
            var itemArticle = articleArr[i];
            $("#"+list_id).append("<li onclick=\"showNews('"+itemArticle.articleLink+"','"+decodeURI(itemArticle.title)+"','"+itemArticle.publicDate+"')\" class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title\">" + decodeURI(itemArticle.title) + "</div><div class=\"item-after\">" + itemArticle.publicDate + "</div></div></li>");
        }
        $("#"+list_id+"more").attr("pagecount", (parseInt(jsonObject.totalCount / 10) + 1).toString());
        pageCount = $("#"+list_id+"more").attr("pagecount");
        if(pageIndex >= pageCount){
            $("#"+list_id+"more").text("没有更多了");
        }else {
            $("#"+list_id+"more").text("查看更多");
        }
    })
}
function getNewsNext(title,list_id) {
    var cagegoryName = encodeURI(encodeURI(title));
    var pageSize = 10;
    var pageIndex = parseInt($("#"+list_id+"more").attr("page")) + 1;
    var pageCount = $("#"+list_id+"more").attr("pagecount");
    if (pageIndex <= pageCount) {
        $.get(newsUrl + cagegoryName + "/"+pageSize+"/"+pageIndex, function (data) {
            var jsonObject = eval("(" + data + ")");
            var articleArr = jsonObject.articleArr;
            for (var i = 0; i < articleArr.length; i++) {
                var itemArticle = articleArr[i];
                $("#"+list_id).append("<li onclick=\"showNews('"+itemArticle.articleLink+"','"+decodeURI(itemArticle.title)+"','"+itemArticle.publicDate+"')\" class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title\">" + decodeURI(itemArticle.title) + "</div><div class=\"item-after\">" + itemArticle.publicDate + "</div></div></li>");
            }
            $("#"+list_id+"more").attr("page", pageIndex);
            if(pageIndex >= pageCount){
                $("#"+list_id+"more").text("没有更多了");
            }else {
                $("#"+list_id+"more").text("查看更多");
            }
        })
    }
}
function showNews(url,title,public_time) {
    $(".popup-detail").fadeIn();
    $(".popup-detail").css("transform", "translate3d(0, 0, 0)");
    $(".popup-detail .content #detail-info").html("");
    $(".popup-detail .bar-nav .title").text("新闻资讯");
    $(".popup-detail .content #detail-info").append("<h3 style='line-height: 1.5; padding:0 .5rem 0rem; font-size:.8rem; text-align: center'>"+title+"</h3>");
    $(".popup-detail .content #detail-info").append("<p style='line-height: 1; padding:0 .5rem .5rem; font-size:.65rem; text-align: center'>发布日期："+public_time+"</p>");
    if (url.length > 0){
        $.get(url,function (data) {
            var regimg = new RegExp("src=\"","g");
            var regscript = new RegExp("<script","g");
            var regscript2 = new RegExp("</script>","g");
            var regsrc = new RegExp("src=\"http://www.xinyongkenli.gov.cn/http","g");
            var regstyle = new RegExp("style=","g");
            data = data.replace(regscript,"<!--");
            data = data.replace(regscript2,"-->");
            data = data.replace(regimg,"onerror=\"this.style.display='none'\" src=\"http://www.xinyongkenli.gov.cn/");
            data = data.replace(regsrc,"src=\"http");
            data = data.replace(regstyle,"old-style=");

            $(".popup-detail .content #detail-info").append("<div class='kl-content'>"+data+"</div>");
            $(".popup-detail .content #detail-loading").hide();
        });
    }
}
function showAbout() {
    $(".popup-detail").fadeIn();
    $(".popup-detail").css("transform", "translate3d(0, 0, 0)");
    $(".popup-detail .content #detail-info").html("");
    $(".popup-detail .bar-nav .title").text("关于我们");
    $(".popup-detail .content #detail-info").append("<div class='kl-content'>&nbsp;&nbsp;&nbsp;&nbsp;信用垦利”官网网站是“信用山东”官方网站的重要组成部分，是垦利区开展社会信用体系建设的唯一官方网站。既是展现我区信用建设成果的窗口，更是面向社会提供信用服务的名片，还肩负着弘扬诚信精神的使命。<br />" +
        "&nbsp;&nbsp;&nbsp;&nbsp;网站分为WEB版和手机版。<br />" +
        "&nbsp;&nbsp;&nbsp;&nbsp;网站由东营市垦利区社会信用管理中心建设，由山东卓智软件有限公司提供技术支持和运维。<br /><br />" +
        "联系我们：<br />" +
        "电话： 0546-2771377<br />" +
        "邮箱：xyklbgs@163.com</div>");
    $(".popup-detail .content #detail-loading").hide();

}
function getImgNews() {
    $.get(imgNewsUrl, function (data) {
        var jsonObject = eval("(" + data + ")");
        var articleArr = jsonObject.articleArr;
        $("#banner .swiper-wrapper").html("");
        var firstItemArticle = "";
        for (var i = 0; i < articleArr.length; i++) {
            var itemArticle = articleArr[i];
            $("#banner .swiper-wrapper").append("<div style='width: 100%; height: 7rem' onclick=\"showNews('"+itemArticle.articleLink+"','"+decodeURI(itemArticle.title)+"','"+itemArticle.publicDate+"')\" class=\"swiper-slide\"><img src=\""+itemArticle.imgPath+"\" /><h2 style='text-indent: 2em;'>"+decodeURI(itemArticle.title)+"</h2></div>")
            if(i==0){
                firstItemArticle = "<div style='width: 100%; height: 7rem' onclick=\"showNews('"+itemArticle.articleLink+"','"+decodeURI(itemArticle.title)+"','"+itemArticle.publicDate+"')\" class=\"swiper-slide\"><img src=\""+itemArticle.imgPath+"\" /><h2 style='text-indent: 2em;'>"+decodeURI(itemArticle.title)+"</h2></div>";
            }
        }
        $("#banner .swiper-wrapper").append(firstItemArticle);
    })
}
function timetrans(timestamp) {
    if (timestamp > 0 && timestamp!=null) {
        var tdate = new Date(timestamp * 1);
        var Y = tdate.getFullYear() + '/';
        var M = (tdate.getMonth() + 1 < 10 ? '0' + (tdate.getMonth() + 1) : tdate.getMonth() + 1) + '/';
        var D = (tdate.getDate() < 10 ? '0' + (tdate.getDate()) : tdate.getDate());
        return Y + M + D;
    }else {
        return "";
    }
}