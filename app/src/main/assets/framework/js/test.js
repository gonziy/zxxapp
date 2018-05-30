function getChildren(id,layer){
    var newLayer = layer * 1 + 1;
    if(newLayer<=3) {
        var html = "";
        if (id != '') {
            $.post("", {"user_id": id}, function (data) {
                var jsonObject = eval("(" + data + ")");
                for (var i = 0; i < jsonObject.data; i++) {
                    var item = jsonObject.data[i];
                    html += "<li class=\"detail-persion\"><div class=\"close_menu\"><span onclick=\"getChildren('" + item.id + "','" + newLayer + "')\"></span><a href=\"#/user/inviter/{dr[id]}.aspx\" class=\"item-content\"><div class=\"item-inner\"><div class=\"item-title-row\"><div class=\"item-title\">" + item.nick_name + "</div><div class=\"item-after\">ID:" + item.id + "</div></div><div class=\"item-subtitle\" style=\"font-size:.55rem\">等级：" + item.group_name + "</div><div class=\"item-text\" style=\"font-size:.55rem\">注册时间：" + item.reg_time + "</div></div></a></div></li>";
                }
            })
        }
    }
}
