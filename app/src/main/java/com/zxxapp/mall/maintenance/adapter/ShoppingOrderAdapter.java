package com.zxxapp.mall.maintenance.adapter;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.databinding.ItemCartBinding;
import com.zxxapp.mall.maintenance.databinding.ItemOrderGoodsBinding;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;

/**
 * Created by jingbin on 2016/12/2.
 */

public class ShoppingOrderAdapter extends BaseRecyclerViewAdapter<CartResult.DataBean> {

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_order_goods);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<CartResult.DataBean, ItemOrderGoodsBinding> {

        ViewHolder(ViewGroup parent, int item_goods) {
            super(parent, item_goods);
        }

        @Override
        public void onBindViewHolder(final CartResult.DataBean dataBean, final int position) {
            binding.setResultsBean(dataBean);
            binding.executePendingBindings();


            binding.etQuantity.setFocusable(false);


            // 显示gif图片会很耗内存
            if (dataBean.getImg_url() != null
                    && !TextUtils.isEmpty(dataBean.getImg_url())) {
                binding.ivThumb.setVisibility(View.VISIBLE);
                binding.ivThumb.setImageURI(Uri.parse(dataBean.getImg_url()));
                ImgLoadUtil.displayGif(dataBean.getImg_url(), binding.ivThumb);
            } else {
                binding.ivThumb.setVisibility(View.GONE);
            }
        }

    }
}
