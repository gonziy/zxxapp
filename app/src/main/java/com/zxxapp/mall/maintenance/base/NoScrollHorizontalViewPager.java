package com.zxxapp.mall.maintenance.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Thinten on 2018-03-14
 * www.thinten.com
 * 9486@163.com.
 */

public class NoScrollHorizontalViewPager extends ViewPager {

    private boolean DISABLE = false;

    public NoScrollHorizontalViewPager(Context context) {
        super(context);
    }

    public NoScrollHorizontalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return DISABLE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return DISABLE;
    }
}