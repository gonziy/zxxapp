package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestListArrayBean;
import com.zxxapp.mall.maintenance.bean.shop.ShopBean;
import com.zxxapp.mall.maintenance.databinding.ActivityShopValidateBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ValidateActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityShopValidateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_validate);
        binding.createTenantButton.setOnClickListener(this);
        binding.validateTenantButton.setOnClickListener(this);
        binding.agreeCheck.setOnClickListener(this);
        binding.agreement.setOnClickListener(this);
    }

    /**
     * 验证商户是否存在
     */
    private void validateExist() {
        System.out.println(AccountHelper.getUser().token);
        DebugUtil.error("shop------http2-----" + AccountHelper.getUser().token);
        HttpClient.Builder.getZhiXiuServer().getShopByToken(AccountHelper.getUser().token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestListArrayBean<ShopBean>>(){

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("系统错误，请您稍后再试");
                    }

                    @Override
                    public void onNext(RequestListArrayBean<ShopBean> shopBean) {
                        if("true".equalsIgnoreCase(shopBean.getSuccess())){
                            //TODO:binding.createTenantButton.setEnabled(false);
                            binding.validateTenantButton.setEnabled(true);

                            AccountHelper.setShop(shopBean.getList()[0]);

                            checkAuth();
                        }else{
                            ToastUtil.showToast("无用户信息，来创建商铺吧！");
                        }
                    }
                });
    }

    private void checkAuth() {
//        //直接进入
//        Intent intent = new Intent(this, ShopMainActivity.class);
//        startActivity(intent);
//        finish();

        binding.validateState.setText("获取认证状态...");
        HttpClient.Builder.getZhiXiuServer().checkAuth(AccountHelper.getUser().token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestBaseBean>(){

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("系统错误，请您稍后再试");
                    }

                    @Override
                    public void onNext(RequestBaseBean requestBaseBean) {
                        if("100".equalsIgnoreCase(requestBaseBean.getCode())){
                            binding.validateState.setText("通过认证");
                            binding.validateTenantButton.setEnabled(false);
                            ToastUtil.showToast("进入商家的首页");

                            Intent intent = new Intent(ValidateActivity.this, ShopMainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if("101".equalsIgnoreCase(requestBaseBean.getCode())){
                            binding.validateState.setText("未通过认证");
                            binding.validateTenantButton.setEnabled(true);
                        }else if("102".equalsIgnoreCase(requestBaseBean.getCode())){
                            binding.validateTenantButton.setEnabled(false);
                            binding.validateState.setText("正在等待平台认证...");
                            binding.validateState.setTextColor(Color.RED);
                        }else if("103".equalsIgnoreCase(requestBaseBean.getCode())){
                            binding.validateState.setText("未提交认证资料");
                            binding.validateTenantButton.setEnabled(true);
                        }else{
                            ToastUtil.showToast("未知状态。");
                        }
                    }
                });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, ValidateActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }

    @Override
    public void onClick(View v) {
        if(v==binding.createTenantButton) {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivityForResult(intent, 0x02);
        }else if(v==binding.validateTenantButton){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivityForResult(intent, 0x01);
        }else if(v==binding.agreement){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(HttpUtils.ZhiXiuWang_HOST+"account/shopAgreement");
            intent.setData(content_url);
            startActivity(intent);
        }else if(v==binding.agreeCheck){
            if(binding.agreeCheck.isChecked()) {
                binding.agreeCheck.setEnabled(false);

                validateExist();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0x01 || requestCode==0x02){
            //查看当前的验证状态
            validateExist();
        }
    }
}
