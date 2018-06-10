package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.http.HttpUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.SendCodeBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.RegisterBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.ActivityLoginBinding;
import com.zxxapp.mall.maintenance.databinding.ActivityRegisterBinding;
import com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.sequence;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initView();
    }

    private void initView() {

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etUsername.getText().length()==0){
                    ToastUtil.showToast("请填写手机号");
                }else {
                    Subscription get = HttpClient.Builder.getZhiXiuServer().sendSms(binding.etUsername.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<SendCodeBean>(){

                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast("系统错误，请您稍后再试");
                                }

                                @Override
                                public void onNext(SendCodeBean sendCodeBean) {
                                    if(sendCodeBean.getCode().equals("100")){

                                        ToastUtil.showToast("发送成功");
                                    }else {
                                        ToastUtil.showToast("注发失败");

                                    }

                                }
                            });

                }
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etUsername.getText().length()==0){
                    ToastUtil.showToast("请填写手机号");
                }
                else if(binding.etPassword.getText().length()==0){
                    ToastUtil.showToast("请填写密码");
                }else if(binding.etValCode.getText().length()==0){
                    ToastUtil.showToast("请填写验证码");
                }else {
                    register(binding.etUsername.getText().toString(),binding.etPassword.getText().toString(),binding.etValCode.getText().toString());
                }

            }
        });
    }

//    public void wxLogin() {
//        if (!BaseApplication.getInstance().WXApi.isWXAppInstalled()) {
//            ToastUtil.showToast("系统找不到您手机中的微信，是不是没有安装?");
//            return;
//        }
//        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "wechat_sdk_demo_test";
//        IWXAPI iwxapi = BaseApplication.getInstance().WXApi;
////        CloudReaderApplication.getInstance().WXApi.sendReq(req);
//        iwxapi.sendReq(req);
//        RegisterActivity.this.finish();
//    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        mContext.startActivity(intent);
    }

    private void register(final String username, final String password, String valCode) {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getZhiXiuServer().registByTelphone(username,password,valCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>(){

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        ToastUtil.showToast("系统错误，请您稍后再试");

                        binding.etUsername.setFocusable(true);
                        binding.etUsername.setFocusableInTouchMode(true);
                        binding.etPassword.setFocusable(true);
                        binding.etPassword.setFocusableInTouchMode(true);
                        binding.btnRegister.setClickable(true);


                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        if(registerBean.getCode().equals("100")){

                            ToastUtil.showToast("注册成功，去登录吧");
                            RegisterActivity.this.finish();
                            LoginActivity.start(RegisterActivity.this);
                        }else {
                            ToastUtil.showToast("注册失败");

                            binding.etUsername.setFocusable(true);
                            binding.etUsername.setFocusableInTouchMode(true);
                            binding.etPassword.setFocusable(true);
                            binding.etPassword.setFocusableInTouchMode(true);
                            binding.btnRegister.setClickable(true);
                        }

                    }
                });
    }
}
