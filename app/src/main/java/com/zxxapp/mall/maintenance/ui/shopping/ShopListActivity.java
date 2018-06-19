package com.zxxapp.mall.maintenance.ui.shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ShopListAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.account.UserAddressBean;
import com.zxxapp.mall.maintenance.bean.shopping.ShopListBean;
import com.zxxapp.mall.maintenance.databinding.ActivityShopListBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.ShopListModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.List;

import rx.Subscription;

/**
 * Created by Thinten on 2018-04-17
 * www.thinten.com
 * 9486@163.com.
 */
public class ShopListActivity extends BaseActivity<ActivityShopListBinding> {

    private ShopListModel mModel;
    private ShopListBean bean;
    private ShopListAdapter adapter;
    private List<ShopListBean.LsitBean> list;

    public String ServiceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        mModel = new ShopListModel();
        bean = new ShopListBean();
        adapter = new ShopListAdapter(ShopListActivity.this);
        initView();
        if (getIntent() != null) {
            ServiceId = (String) getIntent().getSerializableExtra("ServiceId");
            adapter.setServiceId(ServiceId);
        }
        LoadData(ServiceId);
        setTitle("服务商家");
        showMothed();
    }

    private void showMothed() {
        final NormalDialog dialog = new NormalDialog(ShopListActivity.this);
        dialog.content("是否发布您的需求?").style(NormalDialog.STYLE_TWO)
                .titleTextSize(23)
                .cornerRadius(10)
                .titleLineHeight(0)
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        BookingActivity.start(ShopListActivity.this,ServiceId);
                        dialog.dismiss();
                    }
                }
        );

    }

    private void initView() {
        bindingView.xrvList.setPullRefreshEnabled(true);
    }

    public void LoadData(String serviceId){
        mModel.setData(serviceId);
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                bean = (ShopListBean) object;
                if(bean.isSuccess() && !bean.getLsit().isEmpty()){
                    list = bean.getLsit();
                    adapter.clear();
                    adapter.addAll(list);
                    bindingView.xrvList.setLayoutManager( new LinearLayoutManager(ShopListActivity.this));
                    bindingView.xrvList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    showContentView();
                }
            }

            @Override
            public void loadFailed() {
                ToastUtil.showToast("获取数据失败");
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }
    public static void start(Context mContext,String serviceId) {
        Intent intent = new Intent(mContext, ShopListActivity.class);
        intent.putExtra("ServiceId", serviceId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }
}
