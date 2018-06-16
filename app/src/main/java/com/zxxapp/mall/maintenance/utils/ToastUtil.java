package com.zxxapp.mall.maintenance.utils;

import android.widget.Toast;

import com.zxxapp.mall.maintenance.app.BaseApplication;

/**
 * Created by jingbin on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (BaseApplication.getInstance() != null) {
            mToast = Toast.makeText(BaseApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(text);
            mToast.show();
        }
    }
}
