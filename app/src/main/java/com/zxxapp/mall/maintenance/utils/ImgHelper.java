package com.zxxapp.mall.maintenance.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Thinten on 2018-01-13
 * www.thinten.com
 * 9486@163.com.
 */

public class ImgHelper {
    public static int[] getImageWidthHeight(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }
}
