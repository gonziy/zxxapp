package com.zxxapp.mall.maintenance.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.databinding.FragmentGankBinding;
import com.zxxapp.mall.maintenance.http.rx.RxBus;
import com.zxxapp.mall.maintenance.http.rx.RxCodeConstants;
import com.zxxapp.mall.maintenance.ui.gank.child.IndexFragment;
import com.zxxapp.mall.maintenance.ui.shopping.AllCategoryFragment;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment2;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment3;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment4;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment5;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsCustomFragment6;
import com.zxxapp.mall.maintenance.ui.shopping.NewIndexFragment;
import com.zxxapp.mall.maintenance.view.MyFragmentPagerAdapter;

import java.util.ArrayList;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by jingbin on 16/11/21.
 * 展示干货的页面
 */
public class GankFragment extends BaseFragment<FragmentGankBinding> {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        bindingView.vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        bindingView.vpGank.setOffscreenPageLimit(3);
        myAdapter.notifyDataSetChanged();
        bindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
        bindingView.tabGank.setupWithViewPager(bindingView.vpGank);
        showContentView();
        // item点击跳转
        initRxBus();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_gank;
    }

    private void initFragmentList() {
        mTitleList.add("推荐");
        mTitleList.add("全部");
        mTitleList.add("卫浴");
        mTitleList.add("衣柜");
        mTitleList.add("管类");
        mTitleList.add("电类");
//        mTitleList.add("家电");
//        mTitleList.add("洁具");
//        mTitleList.add("福利");
//        mTitleList.add("干货订制");
//        mTitleList.add("大安卓");
        mFragments.add(new NewIndexFragment());
        mFragments.add(new AllCategoryFragment());
        mFragments.add(new GoodsCustomFragment());
        mFragments.add(new GoodsCustomFragment2());


        mFragments.add(new GoodsCustomFragment3());
        mFragments.add(new GoodsCustomFragment4());
//        mFragments.add(new GoodsCustomFragment5());
//        mFragments.add(new GoodsCustomFragment6());
//        mFragments.add(new WelfareFragment());
//        mFragments.add(new CustomFragment());
//        mFragments.add(AndroidFragment.newInstance("Android"));
    }

    /**
     * 每日推荐点击"更多"跳转
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == 0) {
                            bindingView.vpGank.setCurrentItem(3);
                        } else if (integer == 1) {
                            bindingView.vpGank.setCurrentItem(1);
                        } else if (integer == 2) {
                            bindingView.vpGank.setCurrentItem(2);
                        }
                    }
                });
        addSubscription(subscription);
    }
}
