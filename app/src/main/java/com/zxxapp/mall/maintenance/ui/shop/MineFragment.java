package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.FragmentMine2Binding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;

public class MineFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;
    private FragmentMine2Binding binding;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_mine2, container, false);

        binding.llCoupon.setOnClickListener(this);
        binding.llCapital.setOnClickListener(this);
        binding.llRobbing.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.ll_coupon){
            //生成二维码
            createCoupon();
        }else if(v.getId()== R.id.ll_capital){
            Intent intent = new Intent(getContext(), CapitalActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.ll_robbing){
            Intent intent = new Intent(getContext(), RobbingActivity.class);
            intent.putExtra("notificationId", 10);
            intent.putExtra("orderNo", "123345569");
            startActivity(intent);
        }
    }

    private void createCoupon() {
        Intent intent = new Intent(getContext(), CouponActivity.class);
        intent.putExtra("shopId", AccountHelper.getShop().getShopId());
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
