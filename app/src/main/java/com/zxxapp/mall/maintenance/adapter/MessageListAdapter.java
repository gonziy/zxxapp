package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.databinding.ItemMyOrderBinding;
import com.zxxapp.mall.maintenance.databinding.ItemMyOrderMessageBinding;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import io.rong.imkit.RongIM;


public class MessageListAdapter extends BaseRecyclerViewAdapter<OrderByAccountIdBean.DataRowsBean> {

    private Activity activity;
    private String token;
    private String pageIndex;
    private String pageCount;

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;

    public MessageListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_my_order_message);
    }
    public void setData(String token,String pageIndex,String pageCount){
        this.token = token;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<OrderByAccountIdBean.DataRowsBean,ItemMyOrderMessageBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final OrderByAccountIdBean.DataRowsBean bean, int position) {
            binding.setResultsBean(bean);
            binding.executePendingBindings();
            int result = RESULT_SELECT;
            final int finalResult = result;
            binding.reOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RongIM.getInstance().startPrivateChat(v.getContext(), String.valueOf(bean.getShopId()), "与商家对话");
                }
            });
        }
    }
}
