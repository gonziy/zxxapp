package com.zxxapp.mall.maintenance.ui.shopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.example.http.HttpUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ShoppingOrderAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.databinding.ActivityBookingBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.alipay.AuthResult;
import com.zxxapp.mall.maintenance.helper.alipay.OrderInfoUtil2_0;
import com.zxxapp.mall.maintenance.helper.alipay.PayResult;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AreaModel;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioGroup;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioPurified;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BookingActivity extends BaseActivity<ActivityBookingBinding> {


    private String serviceId;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        initView();
        if (getIntent() != null) {
            this.serviceId = (String) getIntent().getSerializableExtra("serviceId");
            this.shopId = (String) getIntent().getSerializableExtra("shopId");
        }
        if(this.shopId==null|| this.shopId.equals("")){
            setTitle("抢单模式");
        }else {
            setTitle("预约商家");
        }
        showContentView();
    }



    private void initView() {
        bindingView.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

    }

    public void submitOrder()
    {
        bindingView.btnSubmit.setClickable(false);
        String name = bindingView.etAcceptName.getText().toString();
        String phone = bindingView.etAcceptMobile.getText().toString();
        String address = bindingView.etAddress.getText().toString();
        String remark = bindingView.etRemark.getText().toString();
        String uploadImg="";
        String lat = "0";
        String lng = "0";
        String token = AccountHelper.getUser().token;
        if(name.isEmpty()){
            ToastUtil.showToast("姓名不可为空");
            return;
        }
        if(phone.isEmpty()){
            ToastUtil.showToast("电话不可为空");
            return;
        }
        if(address.isEmpty()){
            ToastUtil.showToast("地址不可为空");
            return;
        }
        if(token.isEmpty()){
            LoginActivity.start(BookingActivity.this);
            return;
        }
        Subscription get = HttpClient.Builder.getZhiXiuServer().addOrder(uploadImg,phone,this.serviceId,this.shopId
        ,address,lng,lat,remark,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>(){

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("系统错误，请您稍后再试");

                        bindingView.btnSubmit.setClickable(true);
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if(resultBean.getSuccess().equals("true")){
                            ToastUtil.showToast("发布成功");
                            BookingActivity.this.finish();
                        }else {
                            ToastUtil.showToast(String.valueOf(resultBean.getMsg()));
                            bindingView.btnSubmit.setClickable(true);
                        }

                    }
                });
    }

    public static void start(Activity context, String serviceId) {

        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra("serviceId", serviceId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
    public static void start(Activity context, String serviceId, String shopId) {

        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra("serviceId", serviceId);
        intent.putExtra("shopId", shopId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

}
