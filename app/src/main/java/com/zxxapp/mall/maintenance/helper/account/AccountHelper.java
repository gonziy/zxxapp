package com.zxxapp.mall.maintenance.helper.account;

import android.app.Application;

import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.config.AppConfig;

/**
 * Created by Thinten on 2017-12-14
 * www.thinten.com
 * 9486@163.com.
 */

public final class AccountHelper {
    private static final String TAG = AccountHelper.class.getSimpleName();
    private Application application;
    private static AccountHelper instances;
    private User user;

    private AccountHelper(Application application) {
        this.application = application;
    }

    public static void init(Application application) {
        if (instances == null)
            instances = new AccountHelper(application);
        else {
            // reload from source
//            instances.user =
        }
    }

    public static User getUser() {
        if (instances == null) {
            return null;
        }else {
            instances.user = BaseApplication.getInstance().getUser();

            if (instances.user == null || instances.user.getToken()==null || instances.user.getToken().isEmpty()) {
                return null;
            } else {
                return instances.user;
            }
        }
    }

    public static boolean isLogin() {
        User user = getUser();
        if(user!=null &&user.getToken()!=null && !user.getToken().isEmpty()){
            return true;
        }else {
            return false;
        }
//        if (user==null || user.equals(null) || user.getUserID()==null || user.getUserID().equals(null) || user.getUserID().equals(0)) {
//            return false;
//        } else {
//            return true;
//        }
    }
}
