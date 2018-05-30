package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.http.HttpUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.databinding.ActivityLoginBinding;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.ui.mine.MineActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etUsername.getText().length()==0){
                    ToastUtil.showToast("请填写手机号/账号");
                }
                else if(binding.etPassword.getText().length()==0){
                    ToastUtil.showToast("请填写密码");
                }else {
                    String username = binding.etUsername.getText().toString();
                    String password = binding.etPassword.getText().toString();
                    Login(username, password);

                    binding.etUsername.setFocusable(false);
                    binding.etPassword.setFocusable(false);
                    binding.btnLogin.setText("登录中...");
                    binding.btnLogin.setClickable(false);
                }
            }
        });
        binding.ivWxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wxLogin();
            }
        });
    }

    public void wxLogin() {
        if (!BaseApplication.getInstance().WXApi.isWXAppInstalled()) {
            ToastUtil.showToast("系统找不到您手机中的微信，是不是没有安装?");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        IWXAPI iwxapi = BaseApplication.getInstance().WXApi;
//        CloudReaderApplication.getInstance().WXApi.sendReq(req);
        iwxapi.sendReq(req);
        LoginActivity.this.finish();
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    private void Login(final String username, final String password) {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getZXServer().getMobileLogin(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>(){

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
                        binding.btnLogin.setClickable(true);
                        binding.btnLogin.setText("登录");


                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        if(loginResult.getCode().equals("100")){
                            String accessToken = loginResult.getData().getToken();

                            User user = new User();
                            user.setPhone("");
                            user.setAvatarImg("");
                            user.setUserName("");
                            user.setNickName("");
                            user.setEmail("");
                            user.setGroupName("");
                            user.setInviter("");
                            user.setPassword("");
                            user.setUserID(0);
                            user.setToken(accessToken);

                            BaseApplication.getInstance().setUser(user);
                            LoginActivity.this.finish();
                        }else {
                            ToastUtil.showToast("账号或密码错误");

                            binding.etUsername.setFocusable(true);
                            binding.etUsername.setFocusableInTouchMode(true);
                            binding.etPassword.setFocusable(true);
                            binding.etPassword.setFocusableInTouchMode(true);
                            binding.btnLogin.setClickable(true);
                            binding.btnLogin.setText("登录");
                        }

                    }
                });
    }
    private void GetUserInfo(String accessToken){


        Subscription get = HttpClient.Builder.getZXServer().getMyMessageAPI(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserLoginBean>(){

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
                        binding.btnLogin.setClickable(true);
                        binding.btnLogin.setText("登录");


                    }

                    @Override
                    public void onNext(UserLoginBean userLoginBean) {
                        if(userLoginBean.getSuccess().equals("true")){

                            User user = new User();
//                            user.setAvatarImg(userLoginBean.getData().getAvatarImg().equals(null)?"":userLoginBean.getData().getAvatarImg().toString());
//                            user.setPhone(userLoginBean.getData().getPhone().equals(null)?"":userLoginBean.getData().getPhone().toString());
                            user.setToken(userLoginBean.getData().getToken());
//                            user.setNickName(userLoginBean.getData().getNickName().equals(null)?"":userLoginBean.getData().getNickName().toString());
                            user.setUserName(userLoginBean.getData().getUsername());
                            BaseApplication.getInstance().setUser(user);
                            LoginActivity.this.finish();

                        }else {
                            ToastUtil.showToast("获取帐户信息失败");

                            binding.etUsername.setFocusable(true);
                            binding.etUsername.setFocusableInTouchMode(true);
                            binding.etPassword.setFocusable(true);
                            binding.etPassword.setFocusableInTouchMode(true);
                            binding.btnLogin.setClickable(true);
                            binding.btnLogin.setText("登录");
                        }

                    }
                });

    }

}
