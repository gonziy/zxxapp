package com.zxxapp.mall.maintenance.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.squareup.picasso.Picasso;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.databinding.FragmentMineBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.CouponModel;
import com.zxxapp.mall.maintenance.model.UserModel;
import com.zxxapp.mall.maintenance.ui.mine.child.CouponListByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.EditUserInfoActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.Feedback;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.OrderByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.OrderByTokenAndStatusActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.TempOrderByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.shop.ValidateActivity;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MineFragment extends BaseFragment<FragmentMineBinding> {
    private MainActivity activity;
    private UserModel model;
    private String alias = null;
    private WebView webView;
    private WebChromeClient mWebChromeClient;

    private String mUrl = "";
    //检测是否登录
    private String mCheckLogin;

    private static final int SCAN_QR_REQUEST = 103;
    @Override
    public int setContent() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initWebView();
        checkLogin();
        showContentView();
        DebugUtil.error("---OneFragment   --onActivityCreated");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void checkLogin(){
//        binding.ivMineFace.setImageResource(R.mipmap.user_face);
//        if(AccountHelper.isLogin()) {
//            loadUrl(mUrl,true);
//        }else {
//            showNeedLogin();
//        }
        if(AccountHelper.isLogin()) {
            getUserInfo();
        }else
        {
            LoginActivity.start(activity);
        }
        bindingView.ivMineFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserInfoActivity.start(v.getContext());
            }
        });
        bindingView.llUnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderByTokenAndStatusActivity.start(v.getContext());
            }
        });

        bindingView.llOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderByAccountIdActivity.start(v.getContext());
            }
        });
        bindingView.llTempOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempOrderByAccountIdActivity.start(v.getContext());
            }
        });
        bindingView.llMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            EditUserInfoActivity.start(v.getContext());
            }
        });
        bindingView.llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateActivity.start(v.getContext());
            }
        });

        bindingView.llCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineFragment.this.getActivity(), CaptureActivity.class);
                startActivityForResult(intent, SCAN_QR_REQUEST);
            }
        });
        bindingView.llResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponListByAccountIdActivity.start(v.getContext());
            }
        });
        bindingView.llService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback.start(v.getContext());
            }
        });
        bindingView.llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.loadUrl(v.getContext(),"http://zhixiuwang.com/zxxapp/account/help","帮助");
            }
        });
        bindingView.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setToken("");
                user.setUserName("");
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
                LoginActivity.start(activity);
            }
        });
    }
    public void getUserInfo(){
        getUserData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                UserLoginBean bean = (UserLoginBean)object;
                bindingView.tvMineNickName.setText(bean.getData().getNickName());
                bindingView.tvMineAgentAddr.setText(bean.getData().getPhone());
                if(bean.getData().getAvatarImg()==null){
                    Picasso.get().load(R.mipmap.user_face).into(bindingView.ivMineFace);
                }else {
                    Picasso.get().load(bean.getData().getAvatarImg().toString()).into(bindingView.ivMineFace);
                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }

    public static void getUserData(final RequestImpl listener) {

        // 添加新的参数
        Subscription subscription = HttpClient.Builder.getZhiXiuServer().getMyMessageAPI(BaseApplication.getInstance().getUser().getToken())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserLoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();

                    }

                    @Override
                    public void onNext(UserLoginBean bean) {
                        listener.loadSuccess(bean);


                    }
                });
        listener.addSubscription(subscription);
    }

    private void initView() {
        if (!AccountHelper.isLogin()) {
            LoginActivity.start(activity);
        }
    }

    @Override
    protected void onRefresh() {
        //checkLogin();
        if(!AccountHelper.isLogin()) {
            showNeedLogin();
        }else {
            getUserInfo();
            showContentView();
        }
        DebugUtil.error("--OneFragment   ----onRefresh");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DebugUtil.error("--OneFragment   ----onDestroy");
    }

    /**
     * 从此页面新开activity界面返回此页面 走这里
     */
    @Override
    public void onResume() {
        super.onResume();
        //checkLogin();
        if(!AccountHelper.isLogin()) {
            showNeedLogin();
        }else {
            if(mNeedLogin.getVisibility() != View.GONE){
                //loadUrl(mUrl,true);
            }
            getUserInfo();
            showContentView();
        }
        DebugUtil.error("--OneFragment   ----onResume");
    }


    private void loadUrl(String url,boolean checkLogin) {

        if (checkLogin) {
            if (AccountHelper.isLogin()) {
                User user = BaseApplication.getInstance().getUser();
                CookieSyncManager.createInstance(MineFragment.this.getActivity());
                CookieManager cookieMgr = CookieManager.getInstance();
                cookieMgr.setAcceptCookie(true);
                //第一参数是：该网页的主机地址+端口号，例如：http://192.168.1.1:8080
                //第二参数是：Cookie的键值对
                cookieMgr.setCookie(HttpUtils.API_HOST, "dt_cookie_user_name_remember=DTcms=" + user.getUserName());
                cookieMgr.setCookie(HttpUtils.API_HOST, "dt_cookie_user_pwd_remember=DTcms=" + user.getPassword());
                cookieMgr.setCookie(HttpUtils.API_HOST, "dt_browser=android");
                CookieSyncManager.getInstance().sync();
                webView.loadUrl(url);

            } else {
                LoginActivity.start(MainActivity.getMainActivity());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_QR_REQUEST){
                        //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);

                    if(!result.isEmpty()){

                        if(result.startsWith(HttpUtils.API_HOST)){
                            String shopId = result.replace(HttpUtils.API_HOST + "zxxapp/order/createCoupon?shopId=","");
                            CouponModel model = new CouponModel();
                            model.setCouponData(BaseApplication.getInstance().getUser().getToken(),shopId);
                            model.getCoupon(new RequestImpl() {
                                @Override
                                public void loadSuccess(Object object) {

                                    final NormalDialog dialog=new NormalDialog((Context)activity);
                                    dialog.content("领取优惠券成功")//
                                            .btnNum(1)
                                            .titleLineHeight(0)
                                            .cornerRadius(10)
                                            .btnText("确认")//
                                            .show();
                                }

                                @Override
                                public void loadFailed() {
                                    final NormalDialog dialog=new NormalDialog((Context)activity);
                                    dialog.content("领取优惠券失败")//
                                            .btnNum(1)
                                            .titleLineHeight(0)
                                            .cornerRadius(10)
                                            .btnText("确认")//
                                            .show();
                                }

                                @Override
                                public void addSubscription(Subscription subscription) {

                                }
                            });
                        }
                    }
                }
            }
        }
    }

}
