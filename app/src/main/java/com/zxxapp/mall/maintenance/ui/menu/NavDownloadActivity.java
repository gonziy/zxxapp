package com.zxxapp.mall.maintenance.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.databinding.ActivityNavDownloadBinding;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.QRCodeUtil;
import com.zxxapp.mall.maintenance.utils.ShareUtils;

public class NavDownloadActivity extends BaseActivity<ActivityNavDownloadBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_download);
        showContentView();

        setTitle("扫码下载");
        String url = "http://shop.zhenmeizhixiu.com/goods.aspx";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.mipmap.ic_launcher);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, NavDownloadActivity.class);
        mContext.startActivity(intent);
    }
}
