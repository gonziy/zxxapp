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
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.databinding.ActivityFeedbackBinding;
import com.zxxapp.mall.maintenance.ui.shop.ValidateActivity;

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
                final NormalDialog dialog=new NormalDialog(Feedback.this);
                dialog.content("已提交反馈，我们将尽快处理您的意见")//
                        .btnNum(1)
                        .titleLineHeight(0)
                        .cornerRadius(10)
                        .btnText("确认")//
                        .show();
            }
        });
    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, Feedback.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }
}
