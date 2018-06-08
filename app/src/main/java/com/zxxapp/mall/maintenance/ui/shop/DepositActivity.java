package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.shop.WalletBean;
import com.zxxapp.mall.maintenance.databinding.ActivityDepositBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.text.DecimalFormat;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DepositActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityDepositBinding binding;

    private String bankCode;
    private Float total;
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_deposit);

        bankCode = "ICBC_DEBIT";
        total = 0f;

        decimalFormat = new DecimalFormat("0.00");
        binding.bankText.setOnClickListener(this);
        binding.totalText.setOnClickListener(this);
        binding.moneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                if("".equalsIgnoreCase(str)){
                    str = "0";
                }

                float money = Float.parseFloat(str);
                if(money>total){
                    binding.beyondText.setVisibility(View.VISIBLE);
                    binding.balanceText.setVisibility(View.INVISIBLE);
                }else{
                    binding.beyondText.setVisibility(View.INVISIBLE);
                    binding.balanceText.setVisibility(View.VISIBLE);
                }

                if(money>=0 && money<=total){
                    binding.okButton.setEnabled(true);
                }else{
                    binding.okButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.okButton.setOnClickListener(this);

        initData();
    }

    private void initData() {
        HttpClient.Builder.getZhiXiuServer().getDeposit(AccountHelper.getUser().token)
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
                        if ("true".equalsIgnoreCase(walletBean.getSuccess())) {
                            total = walletBean.getTotalDeposit();
                            if(total==null){
                                total = 0f;
                            }

                            binding.balanceText.setText("可用余额 " + decimalFormat.format(total) + " 元");
                        }else{
                            ToastUtil.showToast("获取数据失败，请稍后重试。");

                            total = 0f;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bankText) {
            Intent intent = new Intent(v.getContext(), BankActivity.class);
            startActivityForResult(intent, 0x01);
        }else if(v.getId()==R.id.totalText){
            binding.moneyEdit.setText(decimalFormat.format(total));
        }else if(v.getId()==R.id.okButton){
            save();
        }
    }

    private void save() {
        String name = binding.nameEdit.getText().toString().trim();
        String card = binding.cardEdit.getText().toString().trim();
        String str = binding.moneyEdit.getText().toString().trim();

        if("".equalsIgnoreCase(name)){
            ToastUtil.showToast("请输入收款姓名。");
            return ;
        }

        if("".equalsIgnoreCase(card)){
            ToastUtil.showToast("请输入收款卡号。");
            return ;
        }

        if("".equalsIgnoreCase(str)){
            ToastUtil.showToast("请输入提现金额。");
            return ;
        }

        float money= Float.parseFloat(str);

        HttpClient.Builder.getZhiXiuServer().deposit(AccountHelper.getUser().token, money, name, card, bankCode, 2)
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
                        if ("true".equalsIgnoreCase(walletBean.getSuccess())) {
                            ToastUtil.showToast("成功提交提现申请。");
                        }else{
                            ToastUtil.showToast("申请提现失败，请稍后重试。");
                        }

                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0x01) {//选择银行信息
            bankCode = data.getStringExtra("code");
            binding.bankText.setText(data.getStringExtra("title"));
        }
    }
}
