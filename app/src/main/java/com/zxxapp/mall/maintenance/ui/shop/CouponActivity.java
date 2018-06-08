package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.http.HttpUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivityCouponBinding;

public class CouponActivity extends AppCompatActivity {
    private ActivityCouponBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);

        Intent intent=getIntent();
        Integer shopId = intent.getIntExtra("shopId", -1);
        String url = HttpUtils.ZhiXiuWang_HOST+"order/createCoupon?shopId="+shopId;

        //生产二维码
        Bitmap qrcode = CodeUtils.createImage(url, 256, 256, null);
        binding.qrcodeImage.setImageBitmap(qrcode);
    }
}
