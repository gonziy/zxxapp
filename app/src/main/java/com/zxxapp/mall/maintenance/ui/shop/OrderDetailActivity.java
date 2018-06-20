package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestDataBean;
import com.zxxapp.mall.maintenance.bean.shop.OrderBean;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderDetailBinding;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.rong.imkit.RongIM;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityOrderDetailBinding binding;
    private String orderNo;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        Intent intent = getIntent();
        int orderId = intent.getIntExtra("orderId", -1);
        orderNo = intent.getStringExtra("orderNo");

        binding.backButton.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);
        binding.offerButton.setOnClickListener(this);

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
                            final OrderBean orderBean = orderBeanRequestDataBean.getData();

                            binding.orderNoText.setText(orderBean.getOrderNo());
                            binding.customName.setText(orderBean.getName());
                            binding.customPhone.setText(orderBean.getPhone());
                            binding.customAddressText.setText(orderBean.getAddress());
                            binding.servceText.setText(orderBean.getContent());
                            binding.imageView9.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    RongIM.getInstance().startPrivateChat(v.getContext(), String.valueOf(orderBean.getAccountId()), "与客户沟通、");
                                }
                            });

                            //0.提交订单未付款、2.确定金额、1.已付款，已完成、5.退单
                            if("0".equalsIgnoreCase(orderBean.getStatus())){
                                binding.quoteLayout.setVisibility(View.VISIBLE);
                                binding.paymentLayout.setVisibility(View.GONE);
                                binding.serviceLayout.setVisibility(View.GONE);

                                binding.orderStatusText.setText("客户提交订单");
                            }else if("2".equalsIgnoreCase(orderBean.getStatus())){
                                binding.quoteLayout.setVisibility(View.GONE);
                                binding.paymentLayout.setVisibility(View.VISIBLE);
                                binding.serviceLayout.setVisibility(View.GONE);

                                binding.orderStatusText.setText("商家完成报价");
                            }else if("1".equalsIgnoreCase(orderBean.getStatus())){
                                binding.quoteLayout.setVisibility(View.GONE);
                                binding.paymentLayout.setVisibility(View.VISIBLE);
                                binding.serviceLayout.setVisibility(View.VISIBLE);

                                binding.orderStatusText.setText("客户完成支付");
                            }else if("5".equalsIgnoreCase(orderBean.getStatus())){
                                binding.quoteLayout.setVisibility(View.GONE);
                                binding.paymentLayout.setVisibility(View.GONE);
                                binding.serviceLayout.setVisibility(View.GONE);

                                binding.orderStatusText.setText("商家退单");
                            }

                            DecimalFormat df = new DecimalFormat(",###,##0.00");

                            binding.offerEdit.setText(df.format(orderBean.getUnitPrice()));
                            binding.priceText.setText(df.format(orderBean.getUnitPrice()));

                            if (orderBean.getPayment()==null){
                                binding.paymentText.setText("未支付");
                                binding.paymentTimeText.setText("");
                                binding.paymentTypeText.setText("");
                            }else{
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
        } else if(v.getId()== R.id.offerButton){
            updateOrderPrice();
        }
    }

    private void updateOrderPrice() {
        String memo = binding.memoEdit.getText().toString();
        float price = Float.parseFloat(binding.offerEdit.getText().toString());

        HttpClient.Builder.getZhiXiuServer().updateOrderPrice(orderNo, price)
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
                        if("true".equalsIgnoreCase(requestBaseBean.getSuccess())){
                            ToastUtil.showToast("已更新报价。");

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, Toast.LENGTH_LONG);
                        }else {
                            ToastUtil.showToast("更新报价失败，请重新尝试。");
                        }
                    }
                });
    }
}
