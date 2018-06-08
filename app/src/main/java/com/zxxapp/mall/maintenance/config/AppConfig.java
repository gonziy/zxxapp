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
    public static String WX_APPID = "wxcdf286664ebabde6";
    public static String WX_APPSECRET = "451feabc0eeb7c04a068e7dfd4417806";
    public static String WX_PARTNERID = "1503558781";

    //支付宝
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAY_APPID = "2018030902342032";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String ALIPAY_PID = "2088122752620465";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String ALIPAY_TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String ALIPAY_RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDos5q34uIUQUeG0qfcUbCdl9yDVOzIAm8+z1+vhIOXp3yB6FKDqYKdJrn3XxV3eCrOKxD/sq7XMfk03UuOf4q6hzzbvrlCHWzEiqLbpMgddWKtHC6cAc4Pf0o7UCBUpHYzhmecrDRqtiTvju0OEcq1KejhP9wcElE41qa3ypNmunItbXFETll8VO8IVmRRY6wIPhdEwtpzw0fu9MOmw968i73ESnUQsldMNf4B9KsCou9OHcitAyJB4SgC2Z3Hj2CQv4tujs4p3gzLWoSWWw2uHTc/b2Xb7FApwt4bE7MvCHabcNxZ+zyJsWsGRPssCDaQBOzyHIiYzi5FdpuREWZtAgMBAAECggEBAMKlMT6tjlRJAm55GKYGwsgftfk4+VKYDruoNK6ZFoWtOE1lqletW67eLOIJa+hh4NRRW8e2i98/UufwT3ojn0RFSRe6YjY2bLu+B1nYbPePL/oDP82JCPcXj10ruc9zPdf9jf+UX8f/Ecg8cne5VCjTpJN9mjz06XG0wwGrdj6PEA1vEIaMH4+ip4iP9DBYts89uVTFoveDU1weFt86VqewGn2zMPzW1JsjLbBjXHQGUih6xAbV4PWnQWhSXIu+JY2dolHjuNv963KLhaN/3DObLXqeRdx/NJ1LXsVfCNeWFMVkEwu+K8sLxBGkc0bFu7SvRIGEkxIZEhl0gxwjcZUCgYEA9TWLzJg0mYvEMo6R4JeplMA6VZrMXwR+uE8ZJTjuZVywVLZGr3SpNF4vTVfuYVfJ2hvVx7pXvNl7GjFfZL9Xvi5tzyLA5GMYOTEjXj6zPeEc1yBxlKRDgFWToXJFx0aHNrZxLP2j7ESc3XKJIykv2Cp791vnv0ZcIwmhQTqIJjMCgYEA8vEmynVWkMeFP1lWZPyZvK9oo1I4T3xCFimfdzukAszwI6f0i3bazKrTu5k0C1h3kvtBivgWSWeOm3MLI+GuwMp9e8woH4KDf2udUAfW9ee0+YKPUyncCRzCfQV3OTwDPCbXMd8jwVUFUIeybMnyaR3mDZwamqg5+dmPwlpHYN8CgYEA1WvKhsN/HJlypZJDU5iNR+PbogB+msDdzmtB9ozLqbt32fNgKHxAfUzwnL/Iwj7XeBe8BP+iN8OOiqioX5bOtr/QYXXdL6s0PSsQnomyFkylfnRFh1tFSSCywtUj+KLhMvSuIruMfirl4TQ8Ojcx5lJr3UrHtUp3DSPnj09zitMCgYEA123WyyOf/sSeuR08ilKg9XvKW0F2o615YPPJ785YUw9uRwARz3Y28dTt6uYhC4qIhjL93QC/qpoFEH3EvC3NbDJjcdsJHV/7wYspNaWuDeNJTRRkigeNkhsJsBmfroY0DCwZGaVYrID7NH0QUH4H0wTrUthBkzFoL9GoNF0f/JsCgYBu0ztYvD+5cRYJrkM5yb+Wi38/Ir96f3x5Z/UnMO78nOKmei0Aky+cl9InU6KF+k0W8M798QWuNo1S+cMHwhPRhhQcm2c7Ab8Fdd1bWG6isS68soaqX3LrTrLiTxSqCxcZc6m3bSf8B95K4FAN9IX3MCg+AsStJIzpc7URPRiP2g==";
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
        SharedPreferencesHelper.getInstance().saveData(KEY_USER +"_user_name", user.getUserName());
        SharedPreferencesHelper.getInstance().saveData(KEY_USER +"_user_id", user.getUserID());
    }

    public User getUser() {
        User user = new User();
        user.setToken((String) SharedPreferencesHelper.getInstance().getData(KEY_USER +"_token", ""));
        user.setUserName((String) SharedPreferencesHelper.getInstance().getData(KEY_USER +"_user_name", ""));
        user.setUserID((Integer) SharedPreferencesHelper.getInstance().getData(KEY_USER +"_user_id", 0));
        return user;
    }


}
