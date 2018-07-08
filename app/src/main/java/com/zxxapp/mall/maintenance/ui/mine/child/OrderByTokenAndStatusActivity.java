package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.OrderByAccountIdAdapter;
import com.zxxapp.mall.maintenance.adapter.OrderByStatusAdapter;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.OrderByStatusBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderByAccountListBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.OrderByAccountModel;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.List;

import rx.Subscription;

/**
 * Created by Thinten on 2018-04-17
 * www.thinten.com
 * 9486@163.com.
 */
public class OrderByTokenAndStatusActivity extends BaseActivity<ActivityOrderByAccountListBinding> {

    private OrderByAccountModel mModel;
    private OrderByStatusBean bean;
    private OrderByStatusAdapter adapter;
    private List<OrderByStatusBean.DataBean> list;

    public String ServiceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_account_list);
        mModel = new OrderByAccountModel();
        bean = new OrderByStatusBean();
        adapter = new OrderByStatusAdapter(OrderByTokenAndStatusActivity.this);
        initView();
        if(AccountHelper.isLogin())
        {
            User user = AccountHelper.getUser();
            LoadData(user.token,"0");
        }
        setTitle("我的订单");
    }


    private void initView() {
        //bindingView.xrvList.setPullRefreshEnabled(true);
        bindingView.srlIndex.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AccountHelper.isLogin())
                {
                    User user = AccountHelper.getUser();
                    LoadData(user.token,"0");
                }
            }
        });
    }

    public void LoadData(String token,String status){
        mModel.setData(token,status);

        mModel.getOrderByTokenAndStatus(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                bean = (OrderByStatusBean) object;
                if(bean.getSuccess().equals("true") && !bean.getData().isEmpty()){
                    list = bean.getData();
                    adapter.clear();
                    adapter.addAll(list);
                    bindingView.xrvList.setLayoutManager( new LinearLayoutManager(OrderByTokenAndStatusActivity.this));
                    bindingView.xrvList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.showToast(bean.getMsg());
                }
                bindingView.srlIndex.setRefreshing(false);
                showContentView();
            }

            @Override
            public void loadFailed() {
                ToastUtil.showToast("获取数据失败");
                bindingView.srlIndex.setRefreshing(false);
                showError();
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        if(AccountHelper.isLogin())
        {
            User user = AccountHelper.getUser();

            LoadData(user.token,"0");
            showContentView();
        }
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, OrderByTokenAndStatusActivity.class);
//        intent.putExtra("ServiceId", serviceId);
        mContext.startActivity(intent);
    }
}