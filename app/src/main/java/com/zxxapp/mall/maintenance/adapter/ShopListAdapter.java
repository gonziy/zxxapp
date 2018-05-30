package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.account.UserAddressBean;
import com.zxxapp.mall.maintenance.bean.shopping.ShopListBean;
import com.zxxapp.mall.maintenance.databinding.ItemShopBinding;
import com.zxxapp.mall.maintenance.ui.shopping.BookingActivity;
import com.zxxapp.mall.maintenance.ui.shopping.OrderActivity;


public class ShopListAdapter extends BaseRecyclerViewAdapter<ShopListBean.LsitBean> {

    private Activity activity;
    private String serviceId;
    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;

    public ShopListAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_shop);
    }
    public void setServiceId(String serviceId){
        this.serviceId = serviceId;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<ShopListBean.LsitBean, ItemShopBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final ShopListBean.LsitBean bean, int position) {
            binding.setResultsBean(bean);
            binding.executePendingBindings();
            int result = RESULT_SELECT;

            final int finalResult = result;
            binding.rlShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BookingActivity.start((Activity)v.getContext(),serviceId,String.valueOf(bean.getShopId()));
                    //OrderActivity.start(v.getContext());
//                    Intent intent = new Intent();
                    //intent = intent.setClass(activity, OrderActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("result", finalResult);
//                    bundle.putString("accept_name",bean.getAccept_name());
//                    bundle.putString("mobile",bean.getMobile());
//
//                    bundle.putString("province_name",bean.getArea().split(",")[0]);
//                    bundle.putString("city_name",bean.getArea().split(",")[1]);
//                    bundle.putString("area_name",bean.getArea().split(",")[2]);
//
//                    bundle.putString("province_id",bean.getArea_code().split(",")[0]);
//                    bundle.putString("city_id",bean.getArea_code().split(",")[1]);
//                    bundle.putString("area_id",bean.getArea_code().split(",")[2]);
//                    bundle.putString("address",bean.getAddress());

//                    intent.putExtras(bundle);
//                    activity.setResult(finalResult, intent);   //RESULT_OK是返回状态码
//                    activity.finish(); //会触发onDestroy();

                }
            });
        }
    }
}
