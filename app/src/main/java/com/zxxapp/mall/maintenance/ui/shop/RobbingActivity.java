package com.zxxapp.mall.maintenance.ui.shop;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestDataBean;
import com.zxxapp.mall.maintenance.bean.shop.OrderBean;
import com.zxxapp.mall.maintenance.bean.shop.WalletBean;
import com.zxxapp.mall.maintenance.databinding.ActivityRobbingBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RobbingActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityRobbingBinding binding;
    private String orderNo;
    private int notificationId;
    private OrderBean orderBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_robbing);

        Intent intent = getIntent();
        orderNo = intent.getStringExtra("orderNo");
        notificationId = intent.getIntExtra("notificationId", 0);

        binding.giveupButton.setOnClickListener(this);
        binding.okButton.setOnClickListener(this);

        initData();
    }

    private void initData() {
        HttpClient.Builder.getZhiXiuServer().getOrder(orderNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestDataBean<OrderBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestDataBean<OrderBean> orderBeanRequestDataBean) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

                        if ("true".equalsIgnoreCase(orderBeanRequestDataBean.getSuccess())) {
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.cancel(notificationId);

                            orderBean = orderBeanRequestDataBean.getData();

                            if (orderBean.getOrderDate() != null) {
                                binding.timeText.setText(sdf.format(new Date(Long.parseLong(orderBean.getOrderDate()))));
                            }
                            binding.customText.setText((orderBean.getName() == null ? "未提供" : orderBean.getName()) + ", " + (orderBean.getPhone() == null ? "未提供" : orderBean.getPhone()));
                            binding.addressText.setText(orderBean.getAddress() == null ? "未提供" : orderBean.getAddress());
                            binding.serviceText.setText(orderBean.getContent());
                        } else {
                            ToastUtil.showToast("获取订单数据失败，请稍后重试。");
                        }
                    }
                });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.okButton) {
            HttpClient.Builder.getZhiXiuServer().grabOrder(orderNo,orderBean.getShopId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RequestBaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RequestBaseBean requestBaseBean) {
                            if ("true".equalsIgnoreCase(requestBaseBean.getSuccess())) {
                                new AlertDialog.Builder(RobbingActivity.this)
                                        .setTitle("提示").setMessage("抢单成功。")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //RobbingActivity.this.finish();
                                                //跳转订单详情界面


                                                Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
                                                intent.putExtra("orderId", orderBean.getOrderId());
                                                intent.putExtra("orderNo", orderBean.getOrderNo());
                                                startActivity(intent);
                                            }
                                        }).show();
                            } else {
                                new AlertDialog.Builder(RobbingActivity.this)
                                        .setTitle("提示").setMessage("抢单失败。")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                RobbingActivity.this.finish();
                                            }
                                        }).show();
                            }
                        }
                    });
        } else if (v.getId() == R.id.giveupButton) {
            finish();
        }
    }
}
