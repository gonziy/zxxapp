package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.bean.shopping.TempOrderListBean;
import com.zxxapp.mall.maintenance.databinding.ItemMyOrderBinding;
import com.zxxapp.mall.maintenance.databinding.ItemMyTempOrderBinding;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import io.rong.imkit.RongIM;


public class TempOrderByAccountIdAdapter extends BaseRecyclerViewAdapter<TempOrderListBean.ListBean> {

    private Activity activity;
    private String token;
    private String pageIndex;
    private String pageCount;

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;

    public TempOrderByAccountIdAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_my_temp_order);
    }
    public void setData(String token,String pageIndex,String pageCount){
        this.token = token;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<TempOrderListBean.ListBean,ItemMyTempOrderBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final TempOrderListBean.ListBean bean, int position) {
            binding.setResultsBean(bean);
            binding.executePendingBindings();
            int result = RESULT_SELECT;
            final int finalResult = result;
            binding.reOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bean.getStatus().equals("2")) {
                        OrderConfirmActivity.start(v.getContext(), bean.getUnitPrice() == null ? 0 : Double.valueOf(bean.getUnitPrice().toString()), bean.getOrderNo(),String.valueOf(bean.getShopId()));
                    }else if(bean.getStatus().equals("0")){
                        RongIM.getInstance().startPrivateChat(v.getContext(), String.valueOf(bean.getShopId()), "与商家对话");
                        //ToastUtil.showToast("商家还未确认订单");
                    }else if(bean.getStatus().equals("1")){
                        ToastUtil.showToast("订单已完成,无需重复付款");
                    }else if(bean.getStatus().equals("5")){
                        ToastUtil.showToast("已退单，订单无效");
                    }else {
                        ToastUtil.showToast("系统未知");
                    }
                }
            });
        }
    }
}
