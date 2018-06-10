package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.databinding.ActivityEditUserInfoBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditUserInfoActivity extends BaseActivity<ActivityEditUserInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        setTitle("修改信息");
        initView();
    }

    private void initView() {
        if(AccountHelper.isLogin())
        {
            Subscription get = HttpClient.Builder.getZhiXiuServer().getMyMessageAPI(AccountHelper.getUser().token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserLoginBean>(){

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                            ToastUtil.showToast("系统错误，请您稍后再试");
                        }

                        @Override
                        public void onNext(UserLoginBean userLoginBean) {
                            if (userLoginBean.getSuccess().equals("true")) {
                                bindingView.etNickname.setText(userLoginBean.getData().getNickName());
                                bindingView.etPhone.setText(userLoginBean.getData().getPhone());
                                showContentView();
                            } else {
                                ToastUtil.showToast("获取信息失败");

                            }
                        }
                    });

        }else {
            ToastUtil.showToast("请登录");
            EditUserInfoActivity.this.finish();
        }
        bindingView.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String nickname = bindingView.etNickname.getText().toString();
                 String phone = bindingView.etPhone.getText().toString();

                 if(nickname.isEmpty()){
                     ToastUtil.showToast("昵称不可为空");
                     return;
                 }
                if(phone.isEmpty()){
                    ToastUtil.showToast("手机不可为空");
                    return;
                }

                Subscription get = HttpClient.Builder.getZhiXiuServer().editAccountAPI(AccountHelper.getUser().token,nickname,phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>(){

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {

                                ToastUtil.showToast("系统错误，请您稍后再试");
                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                if (resultBean.getSuccess().equals("true")) {
                                    ToastUtil.showToast("修改成功");
                                    EditUserInfoActivity.this.finish();

                                } else {
                                    ToastUtil.showToast("获取信息失败");

                                }
                            }
                        });
            }
        });

    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, EditUserInfoActivity.class);
        mContext.startActivity(intent);
    }

}
