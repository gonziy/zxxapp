package com.zxxapp.mall.maintenance.config;

/**
 * Created by Thinten on 2018-01-01
 * www.thinten.com
 * 9486@163.com.
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by zhaoyong on 2016/1/26.
 * 增加图片清晰度
 */
public class GlideConfiguration implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}