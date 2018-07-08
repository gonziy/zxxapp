package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.OrderByAccountIdAdapter;
import com.zxxapp.mall.maintenance.base.BaseActivity;
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
public class OrderByAccountIdActivity extends BaseActivity<ActivityOrderByAccountListBinding> {

    private OrderByAccountModel mModel;
    private OrderByAccountIdBean bean;
    private OrderByAccountIdAdapter adapter;
    private List<OrderByAccountIdBean.DataRowsBean> list;

    public String ServiceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_account_list);
        mModel = new OrderByAccountModel();
        bean = new OrderByAccountIdBean();
        adapter = new OrderByAccountIdAdapter(OrderByAccountIdActivity.this);
        initView();
        if(AccountHelper.isLogin())
        {
            User user = AccountHelper.getUser();

            LoadData(user.token,"1","20");
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
                    LoadData(user.token,"1","20");
                }
            }
        });
    }

    public void LoadData(String token,String pageIndex,String pageCount){
        mModel.setData(token,pageIndex,pageCount);
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                bean = (OrderByAccountIdBean) object;
                if(bean.isSuccess() && !bean.getDataRows().isEmpty()){
                    list = bean.getDataRows();
                    adapter.clear();
                    adapter.addAll(list);
                    bindingView.xrvList.setLayoutManager( new LinearLayoutManager(OrderByAccountIdActivity.this));
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
                bindingView.srlIndex.setRefreshing(false);
                ToastUtil.showToast("获取数据失败");
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

            LoadData(user.token,"1","20");
            showContentView();
        }
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, OrderByAccountIdActivity.class);
//        intent.putExtra("ServiceId", serviceId);
        mContext.startActivity(intent);
    }
}
