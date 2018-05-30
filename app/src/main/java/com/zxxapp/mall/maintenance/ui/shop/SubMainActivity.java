package com.zxxapp.mall.maintenance.ui.shop;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivitySubMainBinding;
import com.zxxapp.mall.maintenance.view.statusbar.StatusBarUtil;


public class SubMainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ActivitySubMainBinding subMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_sub_main);
        initStatusView();
    }

    private void initStatusView() {
        ViewGroup.LayoutParams layoutParams = subMainBinding.include.viewStatus.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        subMainBinding.include.viewStatus.setLayoutParams(layoutParams);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

    }
}
