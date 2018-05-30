package com.zxxapp.mall.maintenance.config;

import android.content.Context;

import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;

/**
 * Created by Thinten on 2017-12-14
 * www.thinten.com
 * 9486@163.com.
 */

public class AppConfig {

    private String KEY_SETTING_NOTIFICATION = "key_setting_notification";
    private String KEY_SETTING_SOUND = "key_setting_sound";
    private String KEY_UUID = "key_uuid";
    private String KEY_TOKEN = "key_token";

    private String KEY_USER = "key_user";

    //微信
    public static String WX_APPID = "wx66c002d980b4441d";
    public static String WX_APPSECRET = "03740676f8c9cde20f1fe524e8800545";
    public static String WX_PARTNERID = "1495410242";

    //支付宝
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAY_APPID = "2017121700928665";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String ALIPAY_PID = "2088921229041093";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String ALIPAY_TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String ALIPAY_RSA2_PRIVATE = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDmwwd8uQoaih/4eZb509IqftoN4X97vPjoo9DjOAQ9O7ZSBRpJsOucyay1dzPLoI9Lc/BYML5RsK5FrmPMeckZyoji08558LxzylQcAlF7DErtqfB/FdHSBzNE2r9BOWMEPTztMH8w3XmVJKYyCWi/dIbkis3J491m8Gi6mxgLEorH3TZ27bjU25iRGinTWAEWqJbGaHofUJY3PwGepK++aDiV0/DY7c2ErAs2gN0PIv/t045MP2M4VA2XUni7m4qyuRfesd7iVAvsv3QavAuf9gCmgIuR31lr579oFgI8DcRBFtW2oqiZSAtTi5VCUfDu45vkywutvdgS6VwVQZbVAgMBAAECggEBAKJz4Dx8o9FFNfad2pxr84O5I9crzayVv4E/ehnL4Kge9l5Ne1EvfN821yA1F8CdeuN6blQXlJQ71qP1fgqAHgPLIozU4YHU6UZ2xLmnJCeE4ubngQhmmvzJ1tz9RjErrjpYxiCQyKnk8Of/EgUdvKEz7pbL5M3hXYdPbdsYvldj0sNuo4DI+BwxC0QG3Da/VJWKv555pFP8EJ09TykjnFInlxLXCFMrcA3WAZFGzwcPlDs2A/ToCC75J8RumCdeuE0XTPf9x338VeOqJB/Fpj+mbMVUBtilHWRBLkmrUcLTBwxW1IpK8KcMdGdHu7xzbCrkoPEI8KhID0ISrJACtwECgYEA/huBohVEgkTiYJNMY8gLbzAst8JW8QXEzKaGb3IF8KZWhMo/eDNI7PahRHnYarz1b0UntOQHfhnrsKS882x29I8Ppg38vvLWf+e4u+cn8MGB17rmwUFB/KWODNrB9boe4nml/mdlgNrNExdfucAYv1ERwQXO4qiY01KBbvKXvxUCgYEA6HsCz99JSyFoz7yVco+Jvg66CEIRcTs8SyGR7BDwXuc4DbneMiIV/Gs6sUz/5GtjTAc8fG8D+x6LZLZmpGM4AjwDAuDu7DvuHpgIesn+ZUULmn9zyrjGY6RciQ9NlQ30nriZRP1mCGNyqpO3wxfqRuIDqTN1fVkPHWjDxpzSaMECgYEA4RT2T97sz0M7DnaLLLtuvW97vM3M+zOz7d73p9HlRxPgAUGw1MUArL2Nfb08INf9wzELieSOanmFVvOlVy3VdK0gHGO5ZF5v994GuZJ4w350h+XLLpWiYKs411QSiJnzb4J3sZBezbOCAY4rwbpRcW3a/xIo/oZ+0Hj9o4wJND0CgYEApbPH9VI5c4uEoUzRWK5mh0kH4Ar3U7Xc69ApqhEIwxwbH+1zKliRQzFKzEUNv0mrpO0jd63JMJw8zrQTxOfTbARKQSsCIO82cif7I0oGQThNZPmjFsNM5IiqTCbULYGc8eSxmjLjHueDO4G+YRgSrCM8aMV84LMxysx5fn8YmgECgYEA1zg/zin+aM1uun2BqbTJyoX2VkWKMNLj0HDdll1KxJD+JiYjMTyn9R9IZCtvb2Dvk8omi59aaukjDiWXevq2H5bbDdPJpPyVn+mthG2bDZv3nxFoEy7sB0+zw7TLGRCNqJl9B4nRelmgL4x2E+gS326UxZ1hW67aNMA4LYVjmqo=";
    public static final String ALIPAY_RSA_PRIVATE = "";

    public static final int ALIPAY_SDK_PAY_FLAG = 1;
    public static final int ALIPAY_SDK_AUTH_FLAG = 2;




    public AppConfig(Context context) {
        SharedPreferencesHelper.init(context);
    }




    /*
    是否推送
     */
    public void setSettingNotification(boolean bFlag) {
        SharedPreferencesHelper.getInstance().saveData(
                KEY_SETTING_NOTIFICATION, bFlag);
    }

    public boolean getSettingNotification() {
        return (Boolean) SharedPreferencesHelper.getInstance().getData(
                KEY_SETTING_NOTIFICATION, false);
    }

    /*
    是否有声音
     */
    public void setSettingSound(boolean bFlag) {
        SharedPreferencesHelper.getInstance().saveData(
                KEY_SETTING_SOUND, bFlag);
    }

    public boolean getSettingSound() {
        return (Boolean) SharedPreferencesHelper.getInstance().getData(
                KEY_SETTING_SOUND, false);
    }

    /*
    用户ID
     */
    public void setToken(String bFlag) {
        SharedPreferencesHelper.getInstance().saveData(
                KEY_TOKEN, bFlag);
    }

    public String getToken() {
        return (String)SharedPreferencesHelper.getInstance().getData(KEY_TOKEN,"");
    }

    /*
    用户Token（临时用加密后密码）
     */
    public void setUUID(String bFlag) {
        SharedPreferencesHelper.getInstance().saveData(
                KEY_UUID, bFlag);
    }

    public String getUUID() {
        return (String)SharedPreferencesHelper.getInstance().getData(KEY_UUID,"");
    }
    /*
    用户信息
     */
    public void setUser(User user) {
        SharedPreferencesHelper.getInstance().saveData(KEY_USER +"_token", user.getToken());
    }

    public User getUser() {
        User user = new User();
        user.setToken((String) SharedPreferencesHelper.getInstance().getData(KEY_USER +"_token", ""));
        return user;
    }


}
