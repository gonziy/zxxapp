package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.shop.WalletBean;
import com.zxxapp.mall.maintenance.databinding.ActivityCapitalBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.DecimalFormat;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CapitalActivity extends AppCompatActivity {

    private ActivityCapitalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capital);
        binding.llDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DepositActivity.class);
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData() {
        HttpClient.Builder.getZhiXiuServer().getTotalAmountByShop(AccountHelper.getUser().token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WalletBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WalletBean walletBean) {
                        DecimalFormat df = new DecimalFormat(",###,##0.00");

                        if("true".equalsIgnoreCase(walletBean.getSuccess())){
                            if(walletBean.getTotalAmount()!=null){
                                binding.totalText.setText(df.format(walletBean.getTotalAmount()));
                            }

                            if(walletBean.getDayAmount()!=null){
                                binding.dayText.setText("+"+df.format(walletBean.getDayAmount()));
                            }

                            if(walletBean.getWallet()!=null){
                                binding.walletText.setText(df.format(walletBean.getWallet()));
                            }

                            if(walletBean.getDeposit()!=null){
                                binding.depositText.setText(df.format(walletBean.getDeposit()));
                            }
                        }else {
                            binding.totalText.setText(df.format(0));
                            binding.dayText.setText(df.format(0));
                            binding.walletText.setText(df.format(0));
                            binding.depositText.setText(df.format(0));

                            ToastUtil.showToast("获取数据发生错误，请稍后重试。");
                        }
                    }
                });
    }
}
