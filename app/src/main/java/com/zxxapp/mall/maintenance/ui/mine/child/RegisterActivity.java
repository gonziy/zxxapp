package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.sequence;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private int passwordStrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        initView();
    }

    private void initView() {

        binding.agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(RegisterActivity.this,"http://zhixiuwang.com/zxxapp/account/adminAgreement","服务协议");
            }
        });
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
                                        binding.btnSend.setClickable(false);
                                        binding.btnSend.setText("已发送");
                                        binding.btnSend.setTextColor(Color.parseColor("#888888"));
                                    }else {
                                        ToastUtil.showToast("发送失败");

                                    }

                                }
                            });

                }
            }
        });
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = binding.etPassword.getText().toString().trim();
                int length = str.length();
                if(length<6){
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的纯数字为弱
                if (str.matches ("^[0-9]+$"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的纯小写字母为弱
                else if (str.matches ("^[a-z]+$"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的纯大写字母为弱
                else if (str.matches ("^[A-Z]+$"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的大写字母和数字，输入的字符小于7个密码为弱
                else if (str.matches ("^[A-Z0-9]{1,5}"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的大写字母和数字，输入的字符大于7个密码为中
                else if (str.matches ("^[A-Z0-9]{6,16}"))
                {
                    passwordStrong = 2;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#f7b23a"));
                }
                //输入的小写字母和数字，输入的字符小于7个密码为弱
                else if (str.matches ("^[a-z0-9]{1,5}"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的小写字母和数字，输入的字符大于7个密码为中
                else if (str.matches ("^[a-z0-9]{6,16}"))
                {
                    passwordStrong = 2;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#f7b23a"));
                }
                //输入的大写字母和小写字母，输入的字符小于7个密码为弱
                else if (str.matches ("^[A-Za-z]{1,5}"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的大写字母和小写字母，输入的字符大于7个密码为中
                else if (str.matches ("^[A-Za-z]{6,16}"))
                {
                    passwordStrong = 2;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#f7b23a"));
                }
                //输入的大写字母和小写字母和数字，输入的字符小于5个个密码为弱
                else if (str.matches ("^[A-Za-z0-9]{1,5}"))
                {
                    passwordStrong = 1;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#eb5858"));
                }
                //输入的大写字母和小写字母和数字，输入的字符大于6个个密码为中
                else if (str.matches ("^[A-Za-z0-9]{6,8}"))
                {
                    passwordStrong = 2;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#f7b23a"));
                }
                //输入的大写字母和小写字母和数字，输入的字符大于8个密码为强
                else if (str.matches ("^[A-Za-z0-9]{9,16}"))
                {
                    passwordStrong = 3;
                    binding.viewTip.setBackgroundColor(Color.parseColor("#00d198"));
                }

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                }else if(passwordStrong<2){
                    ToastUtil.showToast("密码不够安全，请使用大小写、数字组合");
                }else if(!binding.agreeCheck.isChecked()){
                    ToastUtil.showToast("您必须同意服务协议才可注册使用");
                }
                else {
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
                            ToastUtil.showToast("注册失败,"+registerBean.getMsg());

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
