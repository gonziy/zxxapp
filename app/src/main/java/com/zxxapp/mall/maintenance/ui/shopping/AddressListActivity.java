package com.zxxapp.mall.maintenance.ui.shopping;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.UserAddressAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.account.UserAddressBean;
import com.zxxapp.mall.maintenance.databinding.ActivityAddresslistBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.UserAddressModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class AddressListActivity extends BaseActivity<ActivityAddresslistBinding> {

    private UserAddressModel mModel;
    private UserAddressBean addressBean;
    private UserAddressAdapter adapter;
    private List<UserAddressBean.DataBean> addressList;
    private static final int ADDRESS_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        mModel = new UserAddressModel();
        addressBean = new UserAddressBean();
        adapter = new UserAddressAdapter(AddressListActivity.this);
        initView();
        LoadData();
    }

    private void initView() {
        setTitle("选择地址");
        bindingView.xrvAddr.setPullRefreshEnabled(false);

    }

    public void LoadData(){
        User user = BaseApplication.getInstance().getUser();
        if(user!=null&&user.getUserID()>0) {
            mModel.setData(user.getUserName(), user.getPassword());
            mModel.getData(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    addressBean = (UserAddressBean)object;
                    if (addressBean != null && addressBean.getData() != null && addressBean.getData().size() > 0) {
                        addressList = addressBean.getData();
                        adapter.clear();

                        UserAddressBean.DataBean customAddr = new UserAddressBean.DataBean();
                        customAddr.setAccept_name("自定义");
                        customAddr.setAddress("");
                        customAddr.setArea(" , , ");
                        customAddr.setArea_code(" , , ");
                        customAddr.setMobile("创建新的地址");
                        adapter.add(customAddr);
                        adapter.addAll(addressList);
                        bindingView.xrvAddr.setLayoutManager(new LinearLayoutManager(AddressListActivity.this));
                        bindingView.xrvAddr.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                        showContentView();
                    }
                }


                @Override
                public void loadFailed() {

                }

                @Override
                public void addSubscription(Subscription subscription) {
                }
            });
        }else {
            LoginActivity.start(bindingView.xrvAddr.getContext());
        }
    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, AddressListActivity.class);
        mContext.startActivity(intent);
    }
    public static void startForResult(Context mContext) {
        Intent intent = new Intent(mContext, AddressListActivity.class);
        ((Activity)mContext).startActivityForResult(intent,ADDRESS_REQUEST_CODE);
    }
}
