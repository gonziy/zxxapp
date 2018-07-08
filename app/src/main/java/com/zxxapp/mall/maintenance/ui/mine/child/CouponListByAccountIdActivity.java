package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.CouponListByAccountIdAdapter;
import com.zxxapp.mall.maintenance.adapter.OrderByAccountIdAdapter;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.shopping.CouponListBean;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderByAccountListBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.CouponModel;
import com.zxxapp.mall.maintenance.model.OrderByAccountModel;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.List;

import rx.Subscription;

/**
 * Created by Thinten on 2018-04-17
 * www.thinten.com
 * 9486@163.com.
 */
public class CouponListByAccountIdActivity extends BaseActivity<ActivityOrderByAccountListBinding> {

    private CouponModel mModel;
    private CouponListBean bean;
    private CouponListByAccountIdAdapter adapter;
    private List<CouponListBean.DataBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_account_list);
        mModel = new CouponModel();
        bean = new CouponListBean();
        adapter = new CouponListByAccountIdAdapter(CouponListByAccountIdActivity.this);
        initView();
        if(AccountHelper.isLogin())
        {
            User user = AccountHelper.getUser();

            LoadData(user.token);
        }
        setTitle("我的优惠券");
    }


    private void initView() {
        bindingView.srlIndex.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(AccountHelper.isLogin())
                {
                    User user = AccountHelper.getUser();
                    LoadData(user.token);
                }
            }
        });
        //bindingView.xrvList.setPullRefreshEnabled(true);
    }

    public void LoadData(String token){
        mModel.setData(token);
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                bean = (CouponListBean) object;
                if(bean.getSuccess().equals("true") && !bean.getData().getList().isEmpty()){
                    list = bean.getData().getList();
                    adapter.clear();
                    adapter.addAll(list);
                    bindingView.xrvList.setLayoutManager( new LinearLayoutManager(CouponListByAccountIdActivity.this));
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
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, CouponListByAccountIdActivity.class);
//        intent.putExtra("ServiceId", serviceId);
        mContext.startActivity(intent);
    }
}
