package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.OrderByStatusBean;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.databinding.ItemMyOrderBinding;
import com.zxxapp.mall.maintenance.databinding.ItemMyOrderByStatusBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.CancelOrderModel;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.ui.shopping.OrderEvaluationActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import io.rong.imkit.RongIM;
import rx.Subscription;


public class OrderByStatusAdapter extends BaseRecyclerViewAdapter<OrderByStatusBean.DataBean> {

    private Activity activity;
    private String token;
    private String pageIndex;
    private String pageCount;

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;

    public OrderByStatusAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_my_order_by_status);
    }
    public void setData(String token,String pageIndex,String pageCount){
        this.token = token;
        this.pageIndex = pageIndex;
        this.pageCount = pageCount;
    }

    private class ViewHolder extends BaseRecyclerViewHolder<OrderByStatusBean.DataBean,ItemMyOrderByStatusBinding> {

        public ViewHolder(ViewGroup viewGroup, int layoutId) {
            super(viewGroup, layoutId);
        }

        @Override
        public void onBindViewHolder(final OrderByStatusBean.DataBean bean, int position) {
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
                        //OrderEvaluationActivity.start(v.getContext(),bean.getOrderNo(),String.valueOf(bean.getShopId()));
                        //
                         RongIM.getInstance().startPrivateChat(v.getContext(), String.valueOf(bean.getShopId()), "与商家对话");
                        //ToastUtil.showToast("商家还未确认订单");
                    }else if(bean.getStatus().equals("1")){
                        OrderEvaluationActivity.start(v.getContext(),bean.getOrderNo(),String.valueOf(bean.getShopId()));
                    }else if(bean.getStatus().equals("5")){
                        ToastUtil.showToast("已退单，订单无效");
                    }else {
                        ToastUtil.showToast("系统未知");
                    }
                }
            });
            binding.tvRetreat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    CancelOrderModel model = new CancelOrderModel();
                    model.setData(BaseApplication.getInstance().getUser().token,bean.getOrderNo(),"1","");
                    model.getData(new RequestImpl() {
                        @Override
                        public void loadSuccess(Object object) {
                            ResultBean bean = (ResultBean)object;
                            Toast.makeText(v.getContext(),bean.getMsg(),Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void loadFailed() {

                            Toast.makeText(v.getContext(),"取消订单失败",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void addSubscription(Subscription subscription) {

                        }
                    });
                }
            });
        }
    }
}
