package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private FragmentOrderBinding binding;

    public OrderFragment() {
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_order, container, false);

        initView();

        return binding.getRoot();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void initView(){
        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(4);
        adapter.notifyDataSetChanged();
        binding.tab.setTabMode(TabLayout.MODE_FIXED);
        binding.tab.setupWithViewPager(binding.viewPager);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "全部";
            }else if(position==1){
                return "待定价";
            }else if(position==2){
                return "已完成";
            }else if(position==3) {
                return "退单";
            }

            return "其它";
        }

        @Override
        public Fragment getItem(int position) {
            OrderListFragment fragment = null;

            //0.提交订单未付款、2.确定金额、1.已付款，已完成、5.退单
            if(position==0){
                fragment = OrderListFragment.newInstance(-1);
            }else if(position==1){
                fragment = OrderListFragment.newInstance(0);
            }else if(position==2){
                fragment = OrderListFragment.newInstance(1);
            }else if(position==3){
                fragment = OrderListFragment.newInstance(5);
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
