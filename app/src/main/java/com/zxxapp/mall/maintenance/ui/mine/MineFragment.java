package com.zxxapp.mall.maintenance.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.FragmentMineBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.model.UserModel;
import com.zxxapp.mall.maintenance.ui.mine.child.EditUserInfoActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.OrderByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.shop.ValidateActivity;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;


public class MineFragment extends BaseFragment<FragmentMineBinding> {
    private MainActivity activity;
    private UserModel model;
    private String alias = null;
    private WebView webView;
    private WebChromeClient mWebChromeClient;

    private String mUrl = "";
    //检测是否登录
    private String mCheckLogin;

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
            setUserInfo();
        }else
        {
            LoginActivity.start(activity);
        }

        bindingView.llOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderByAccountIdActivity.start(v.getContext());
            }
        });
        bindingView.llMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            EditUserInfoActivity.start(v.getContext());
            EditUserInfoActivity.start(v.getContext());
            }
        });
        bindingView.llTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateActivity.start(v.getContext());
            }
        });
        bindingView.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setToken("");
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
    public void setUserInfo(){
     ToastUtil.showToast("获取用户数据接口");
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
            showContentView();
        }
        DebugUtil.error("--OneFragment   ----onResume");
    }

//    private void initWebView() {
//        webView = bindingView.webview;
//        WebSettings ws = webView.getSettings();
//        // 网页内容的宽度是否可大于WebView控件的宽度
//        ws.setLoadWithOverviewMode(false);
//        // 保存表单数据
//        ws.setSaveFormData(true);
//        // 是否应该支持使用其屏幕缩放控件和手势缩放
//        ws.setSupportZoom(true);
//        ws.setBuiltInZoomControls(true);
//        ws.setDisplayZoomControls(false);
//        // 启动应用缓存
//        ws.setAppCacheEnabled(true);
//        // 设置缓存模式
//        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
//        // setDefaultZoom  api19被弃用
//        // 设置此属性，可任意比例缩放。
//        ws.setUseWideViewPort(true);
//        // 缩放比例 1
//        webView.setInitialScale(1);
//        // 告诉WebView启用JavaScript执行。默认的是false。
//        ws.setJavaScriptEnabled(true);
//        //  页面加载好以后，再放开图片
//        ws.setBlockNetworkImage(false);
//        // 使用localStorage则必须打开
//        ws.setDomStorageEnabled(true);
//        // 排版适应屏幕
//        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        // WebView是否支持多个窗口。
//        ws.setSupportMultipleWindows(true);
//
//        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
//        ws.setTextZoom(100);
//
//        // 与js交互
//        webView.addJavascriptInterface(new JavascriptBridgeInterface(MineFragment.this.getActivity()), "JavascriptBridge");
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                showContentView();
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                WebViewActivity.loadUrl(getContext(), request.getUrl().toString(),"");
//                return true;
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                WebViewActivity.loadUrl(getContext(), url,"");
//                return true;
//            }
//        });
//
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                final NormalDialog dialog = new NormalDialog(view.getContext());
//                dialog.content(message).style(NormalDialog.STYLE_TWO)//
//                        .titleTextSize(23)
//                        .titleTextColor(Color.parseColor("#fffd625b"))
//                        .cornerRadius(10)
//                        .titleLineHeight(0)
//                        .btnNum(1)
//                        .btnText("确定")
//                        .show();
//                dialog.setOnBtnClickL(new OnBtnClickL() {
//                    @Override
//                    public void onBtnClick() {
//                        result.cancel();
//                        dialog.dismiss();
//                    }
//                });
//                return true;
//            }
//
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//                final NormalDialog dialog = new NormalDialog(view.getContext());
//                dialog.content(message).style(NormalDialog.STYLE_TWO)//
//                        .titleTextSize(23)
//                        .titleTextColor(Color.parseColor("#fffd625b"))
//                        .cornerRadius(10)
//                        .titleLineHeight(0)
//                        .show();
//                dialog.setOnBtnClickL(
//                        new OnBtnClickL() {
//                            @Override
//                            public void onBtnClick() {
//                                result.cancel();
//                                dialog.dismiss();
//                            }
//                        },
//                        new OnBtnClickL() {
//                            @Override
//                            public void onBtnClick() {
//                                result.confirm();
//                                dialog.dismiss();
//                            }
//                        });
//                return true;
//            }
//
//        });
//    }

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

}
