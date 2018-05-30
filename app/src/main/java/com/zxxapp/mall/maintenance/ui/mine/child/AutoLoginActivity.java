package com.zxxapp.mall.maintenance.ui.mine.child;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.ui.mine.MineActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.TagAliasOperatorHelper;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.wxapi.WXEntryActivity;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AutoLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);

       String WXUnionID="";
        if (getIntent() != null) {
            WXUnionID = (String) getIntent().getSerializableExtra("WXUnionID");
        }
        if(!WXUnionID.isEmpty()){
            Subscription get = HttpClient.Builder.getZMServer().getUserData("unionidlogin",WXUnionID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginResult>() {
                        @Override
                        public void onCompleted() {
                            DebugUtil.error("------onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            DebugUtil.error("------onError");
                        }

                        @Override
                        public void onNext(LoginResult loginResult) {
                            DebugUtil.error("------onNext");
//                            if(loginResult.getMsg().equals("登录成功")){
//                                User user = new User();
//                                user.setNickName(loginResult.getData().getNick_name());
//                                user.setPassword(loginResult.getData().getPassword());
//                                user.setUserName(loginResult.getData().getUser_name());
//                                user.setGroupName(loginResult.getData().getGroup_name());
//                                user.setUserID(loginResult.getData().getId());
//                                BaseApplication.getInstance().setUser(user);
//
//
//
//                                AutoLoginActivity.this.finish();
//
//                            }else {
//                                ToastUtil.showToast(loginResult.getMsg());
//                                LoginActivity.start(AutoLoginActivity.this);
//                                AutoLoginActivity.this.finish();
//                            }
                        }
                    });
        }

    }
}
