package com.zxxapp.mall.maintenance.adapter;

import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.databinding.ItemListBinding;

/**
 * Created by jingbin on 2016/11/30.
 */

public class Adapter extends BaseRecyclerViewAdapter<String> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_list);
    }

    class ViewHolder extends BaseRecyclerViewHolder<String, ItemListBinding> {

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
        }

        @Override
        public void onBindViewHolder(String object, int position) {
            binding.tvText.setText("测试:  " + object);
        }
    }
}