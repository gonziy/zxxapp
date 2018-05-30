package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestDataBean;
import com.zxxapp.mall.maintenance.bean.RequestPaginationBean;
import com.zxxapp.mall.maintenance.bean.shop.OrderBean;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderDetailBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOrderDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        String orderNo = intent.getStringExtra("orderNo");

        binding.backButton.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);

        initData(orderNo);
    }

    private void initData(final String orderNo) {
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
                        if("true".equalsIgnoreCase(orderBeanRequestDataBean.getSuccess())){
                            OrderBean orderBean = orderBeanRequestDataBean.getData();

                            binding.customName.setText(orderBean.getName());
                            binding.customPhone.setText(orderBean.getPhone());
                            binding.customAddressText.setText(orderBean.getAddress());

                            if (orderBean.getPayment()==null){
                                binding.paymentText.setText("未支付");
                                binding.paymentTimeText.setText("");
                                binding.paymentTypeText.setText("");
                            }else{
                                DecimalFormat df = new DecimalFormat("0.00");
                                binding.paymentText.setText(df.format(orderBean.getPayment()));

                                if(orderBean.getPaymentTime()!=null) {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd月 HH:mm:ss");
                                    binding.paymentTimeText.setText(sdf.format(new Date(Long.parseLong(orderBean.getPaymentTime()))));
                                }else{
                                    binding.paymentTimeText.setText("");
                                }

                                if(orderBean.getPaymentType()==1){
                                    binding.paymentTypeText.setText("在线支付");
                                }else{
                                    binding.paymentTypeText.setText("线下支付");
                                }
                            }
                        }else {
                            ToastUtil.showToast("获取数据失败，请稍后重试。");
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        } else if (v.getId() == R.id.finishButton) {
            finish();
        }
    }
}
