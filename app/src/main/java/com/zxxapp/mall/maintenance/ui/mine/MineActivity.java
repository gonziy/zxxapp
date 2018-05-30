package com.zxxapp.mall.maintenance.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.ActivityMineBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

public class MineActivity extends AppCompatActivity {

    private ActivityMineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mine);

        initView();

    }

    private void initView() {
        if(AccountHelper.isLogin()) {
            setUserInfo();
        }else
        {
            LoginActivity.start(this);
            MineActivity.this.finish();
        }
        binding.llOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_all_orders),"全部订单",true);
            }
        });
        binding.llInviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_inviter),"邀请",true);
            }
        });
        binding.llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_team),"我的团队",true);
            }
        });
        binding.llResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_result),"我的成绩",true);
            }
        });
        binding.llProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_property),"帐户资产",true);
            }
        });
        binding.llGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_all_orders),"帐户资产",true);
            }
        });
        binding.llWelfare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_all_orders),"帐户资产",true);
            }
        });
        binding.llMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_myinfo),"我的资料",true);
            }
        });
        binding.llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_all_orders),"帐户资产",true);
            }
        });

        binding.llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST + CommonUtils.getString(R.string.string_url_service),"帮助中心",true);
            }
        });




        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setPassword("");
                user.setUserName("");
                user.setNickName("");
                user.setGroupName("");
                user.setUserID(0);
                BaseApplication.getInstance().setUser(user);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(AccountHelper.isLogin())
                {
                    BaseApplication.getInstance().setUser(user);
                }
                LoginActivity.start(MineActivity.this);
                MineActivity.this.finish();
            }
        });
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, MineActivity.class);
        mContext.startActivity(intent);
    }

    public void setUserInfo(){
//        binding.ivMineFace.setImageResource(R.mipmap.user_face);
        User user = BaseApplication.getInstance().getUser();
        if(user.getUserID()>0) {
            binding.tvMineNickName.setText(user.getNickName());
            binding.tvMineUserGroup.setText(user.getGroupName());
            binding.tvMineAgentAddr.setVisibility(View.GONE);
        }else {
            MineActivity.this.finish();
            LoginActivity.start(this);
        }
    }
}
