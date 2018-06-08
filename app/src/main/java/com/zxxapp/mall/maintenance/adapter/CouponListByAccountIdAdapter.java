package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.shopping.CouponListBean;
import com.zxxapp.mall.maintenance.databinding.ItemMyCouponBinding;


public class CouponListByAccountIdAdapter extends BaseRecyclerViewAdapter<CouponListBean.DataBean.ListBean> {

    private Activity activity;
    private String token;
    private String pageIndex;
    private String pageCount;

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;

    public CouponListByAccountIdAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_my_coupon);
    }
    public void setData(String token,String pageIndex,String pageCount){
        this.token = token;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<CouponListBean.DataBean.ListBean,ItemMyCouponBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final CouponListBean.DataBean.ListBean bean, int position) {
            binding.setResultsBean(bean);
            binding.executePendingBindings();
            int result = RESULT_SELECT;
            final int finalResult = result;
//            binding.reOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(bean.getStatus().equals("2")) {
//                        OrderConfirmActivity.start(v.getContext(), bean.getUnitPrice() == null ? 0 : Double.valueOf(bean.getUnitPrice().toString()), bean.getOrderNo());
//                    }else if(bean.getStatus().equals("0")){
//                        RongIM.getInstance().startPrivateChat(v.getContext(), String.valueOf(bean.getShopId()), "与商家对话");
//                        //ToastUtil.showToast("商家还未确认订单");
//                    }else if(bean.getStatus().equals("1")){
//                        ToastUtil.showToast("订单已完成,无需重复付款");
//                    }else if(bean.getStatus().equals("5")){
//                        ToastUtil.showToast("已退单，订单无效");
//                    }else {
//                        ToastUtil.showToast("系统未知");
//                    }
//                }
//            });
        }
    }
}
