package com.zxxapp.mall.maintenance.helper.TencentIM;

import android.content.Context;
import android.content.SharedPreferences;

import com.zxxapp.mall.maintenance.app.BaseApplication;


/**
 * Created by gabriel on 2017/2/7.
 */

public class SpUtil {

    private SpUtil() {

    }

    private static final SharedPreferences sp = BaseApplication.getContext()
            .getSharedPreferences("config", Context.MODE_PRIVATE);

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }
}
