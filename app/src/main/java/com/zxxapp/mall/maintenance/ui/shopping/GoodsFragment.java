package com.zxxapp.mall.maintenance.ui.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.databinding.FragmentShopBinding;
import com.zxxapp.mall.maintenance.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by jingbin on 16/12/14.
 * 展示书籍的页面
 */
public class GoodsFragment extends BaseFragment<FragmentShopBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(1);
    private ArrayList<Fragment> mFragments = new ArrayList<>(1);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻2个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpGoods.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGoods.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
//        bindingView.tabBook.setTabMode(TabLayout.MODE_FIXED);
//        bindingView.tabBook.setupWithViewPager(bindingView.vpBook);
        showContentView();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_shop;
    }

    private void initFragmentList() {
//        mTitleList.add("甄美至秀");
        mFragments.add(GoodsCustomFragment.newInstance());
    }
}
