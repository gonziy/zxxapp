package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListPopupWindow;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.RongCloudResultBean;
import com.zxxapp.mall.maintenance.bean.RongCloudTokenBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.databinding.ActivityLoginBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper;
import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.sequence;

import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.RongCloudModel;
import com.zxxapp.mall.maintenance.ui.mine.MineActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ListPopupWindow listPopupWindow;
    String[] list;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        list = BaseApplication.getInstance().getUserHistory();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.start(v.getContext());
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etUsername.getText().length() == 0) {
                    ToastUtil.showToast("请填写手机号/账号");
                } else if (binding.etPassword.getText().length() == 0) {
                    ToastUtil.showToast("请填写密码");
                } else {
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
        if(list!=null && list.length>0) {
            listPopupWindow = new ListPopupWindow(LoginActivity.this);
            binding.etUsername.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    listPopupWindow.setAdapter(new ArrayAdapter<String>(LoginActivity.this,
                            android.R.layout.simple_list_item_1, list));
                    listPopupWindow.setAnchorView(binding.etUsername);
                    listPopupWindow.setModal(true);
                    listPopupWindow.show();
                    return false;
                }
            });
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectUsername = list[position];
                    if (!selectUsername.isEmpty()) {
                        binding.etUsername.setText(selectUsername);
                        if (listPopupWindow.isShowing()) {
                            listPopupWindow.dismiss();
                        }
                    }
                }
            });
        }
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
        Subscription get = HttpClient.Builder.getZhiXiuServer().getMobileLogin(username,password)
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
                        if(loginResult.getCode()==null){
                            String accessToken = loginResult.getData().getToken();

                            final User user = new User();
                            user.setPhone("");
                            user.setAvatarImg("");
                            user.setUserName(loginResult.getData().getUserName());
                            user.setNickName("");
                            user.setEmail("");
                            user.setGroupName("");
                            user.setInviter("");
                            user.setPassword("");
                            user.setUserID(loginResult.getData().getUserId());
                            user.setToken(accessToken);

                            BaseApplication.getInstance().setUser(user);
                            BaseApplication.getInstance().addUserHistory(loginResult.getData().getUserName());

                            sequence++;
                            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,user.getUserName());

                            connect(user.getUserID().toString(),user.getNickName());

                            LoginActivity.this.finish();
                        }else {
                            ToastUtil.showToast(loginResult.getData().getMsg());

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
    public void connect(String userId,String nickName) {
        RongCloudModel model = new RongCloudModel();
        model.setData(userId,nickName);
        model.get(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                RongCloudResultBean resultBean = (RongCloudResultBean)object;
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultBean.getData());
                String token = jsonObject.getString("token");

                if(!token.isEmpty()){
                    RongIM.connect(token, new RongIMClient.ConnectCallback() {
                        @Override
                        public void onTokenIncorrect() {

                        }

                        @Override
                        public void onSuccess(String s) {
                            Log.e("rongcloud", "连接通讯服务器成功—————>" + s);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.e("rongcloud", "连接通讯服务器失败—————>" + errorCode.getMessage());
                        }
                    });
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });



    }
    private void GetUserInfo(String accessToken){
        Subscription get = HttpClient.Builder.getZhiXiuServer().getMyMessageAPI(accessToken)
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件

    }


    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 1000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);
        }
    }
}
