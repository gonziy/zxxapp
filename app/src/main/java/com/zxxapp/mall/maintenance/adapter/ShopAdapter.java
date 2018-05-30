package com.zxxapp.mall.maintenance.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTitleBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTwoBinding;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingbin on 2016/12/2.
 */

public class ShopAdapter extends BaseRecyclerViewAdapter<ShopDataBean.ResultBean> {

    private int status = 1;
    private MainActivity context;
    private List<ShopDataBean.ResultBean> list;

    public ShopAdapter(Context context)
    {
        this.context = (MainActivity) context;
        list = new ArrayList<>();
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_goods_two);
    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }
    public List<ShopDataBean.ResultBean> getList() {
        return list;
    }

    public void setList(List<ShopDataBean.ResultBean> list) {
        this.list.clear();
        this.list = list;
    }
    public void addAll(List<ShopDataBean.ResultBean> list) {
        this.list.addAll(list);
    }


    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    public int getLoadStatus(){
        return this.status;
    }


    private class ViewHolder extends BaseRecyclerViewHolder<ShopDataBean.ResultBean, ItemGoodsTwoBinding> {

        ViewHolder(ViewGroup parent, int item_goods) {
            super(parent, item_goods);
        }

        @Override
        public void onBindViewHolder(final ShopDataBean.ResultBean resultsBean, final int position) {



            /**
             * 注意：DensityUtil.setViewMargin(itemView,true,5,3,5,0);
             * 如果这样使用，则每个item的左右边距是不一样的，
             * 这样item不能复用，所以下拉刷新成功后显示会闪一下
             * 换成每个item设置上下左右边距是一样的话，系统就会复用，就消除了图片不能复用 闪跳的情况
             */
//            if (position % 2 == 0) {
//                DensityUtil.setViewMargin(itemView, false, 6, 12, 2, 2);
//            } else {
//                DensityUtil.setViewMargin(itemView, false, 12, 6, 2, 2);
//            }

            binding.setResultsBean(resultsBean);
            binding.executePendingBindings();

            binding.llWelfareOther.setVisibility(View.VISIBLE);

            // 显示gif图片会很耗内存
            if (resultsBean.getImgUrl() != null
                    && !TextUtils.isEmpty(resultsBean.getImgUrl())) {
                binding.ivGoodsPic.setVisibility(View.VISIBLE);
                ImgLoadUtil.showImg( binding.ivGoodsPic,resultsBean.getImgUrl());
            } else {
                binding.ivGoodsPic.setVisibility(View.GONE);
            }
            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    WebViewActivity.loadUrl(v.getContext(), object.getUrl(), "加载中...");
                    GoodsDetailActivity.start((MainActivity)v.getContext(),resultsBean.getId());
                }
            });
        }


    }
}
