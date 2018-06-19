package com.zxxapp.mall.maintenance.ui.mine.child;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.Result2Bean;
import com.zxxapp.mall.maintenance.databinding.ActivityFeedbackBinding;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.ui.shop.ValidateActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Feedback extends BaseActivity<ActivityFeedbackBinding> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setTitle("意见反馈");
        initView();
        showContentView();
    }

    private void initView() {
        bindingView.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bindingView.etFeedback.getText().toString().isEmpty()){
                    Subscription get = HttpClient.Builder.getZhiXiuServer().addFeedback(BaseApplication.getInstance().getUser().token,bindingView.etFeedback.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Result2Bean>(){

                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {

                                    ToastUtil.showToast("系统错误，请您稍后再试");
                                }

                                @Override
                                public void onNext(Result2Bean resultBean) {
                                    if(resultBean.isSccuss()){
                                        final NormalDialog dialog=new NormalDialog(Feedback.this);
                                        dialog.content("提交成功，等待平台处理")//
                                                .btnNum(1)
                                                .titleLineHeight(0)
                                                .cornerRadius(10)
                                                .btnText("确认")//
                                                .show();
                                    }else {
                                        final NormalDialog dialog=new NormalDialog(Feedback.this);
                                        dialog.content(resultBean.getMsg())//
                                                .btnNum(1)
                                                .titleLineHeight(0)
                                                .cornerRadius(10)
                                                .btnText("确认")//
                                                .show();
                                    }

                                }
                            });

                }else{
                    final NormalDialog dialog=new NormalDialog(Feedback.this);
                    dialog.content("请填写反馈意见后再提交")//
                            .btnNum(1)
                            .titleLineHeight(0)
                            .cornerRadius(10)
                            .btnText("确认")//
                            .show();
                }





            }
        });
    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, Feedback.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }
}
