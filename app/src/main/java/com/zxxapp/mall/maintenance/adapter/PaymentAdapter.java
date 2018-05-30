package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.moviechild.SubjectsBean;
import com.zxxapp.mall.maintenance.databinding.ItemOneBinding;
import com.zxxapp.mall.maintenance.databinding.ItemPaymentBinding;
import com.zxxapp.mall.maintenance.ui.one.OneMovieDetailActivity;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by jingbin on 2016/11/25.
 */

public class PaymentAdapter extends BaseRecyclerViewAdapter<PaymentBean> {

    private Activity activity;

    public PaymentAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_payment);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<PaymentBean, ItemPaymentBinding> {

        ViewHolder(ViewGroup context, int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void onBindViewHolder(final PaymentBean data, final int position) {
            if (data != null) {

                // 显示gif图片会很耗内存
                if (data.getIcon()>0) {
                    binding.payIcon.setVisibility(View.VISIBLE);
                    binding.payIcon.setImageResource(data.getIcon());
                } else {
                    binding.payIcon.setVisibility(View.GONE);
                }
                binding.payTitle.setText(data.getTitle());
                binding.payDesc.setText(data.getDesc());

                binding.rlAll.setOnClickListener(new PerfectClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        ToastUtil.showToast(data.getName());

                    }
                });
            }
        }
    }
}
