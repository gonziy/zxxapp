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
import com.zxxapp.mall.maintenance.ui.mine.child.TempOrderByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.shop.ValidateActivity;
import com.zxxapp.mall.maintenance.ui.shopping.OrderConfirmActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

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
        bindingView.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setToken("");
                user.setUserName("");
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
                            String shopId = result.replace(HttpUtils.API_HOST + "zxxapp/order/getCoupon?shopId=","");
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

//        if (requestCode == SCAN_QR_REQUEST) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//
//                    if(!result.isEmpty()){
//                        //验证邀请人
//                        if(result.startsWith(HttpUtils.API_HOST + "inviter.aspx")){
//
//                            if(AccountHelper.isLogin())
//                            {
//                                final String inviter_id = result.replace(HttpUtils.API_HOST + "inviter.aspx?from=","");
//                                //ToastUtil.showToast("邀请人:" + inviter_id);
//
//                                User user = BaseApplication.getInstance().getUser();
//                                Subscription get = HttpClient.Builder.getUserServer().inviteUser("invite",user.getUserName(),user.getPassword(),inviter_id)
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe(new Observer<LoginResult>(){
//
//                                            @Override
//                                            public void onCompleted() {
//                                            }
//
//                                            @Override
//                                            public void onError(Throwable e) {
//                                                ToastUtil.showToast("系统无法读取您的个人信息,请您稍后再试");
//                                            }
//
//                                            @Override
//                                            public void onNext(LoginResult loginResult) {
//                                                if(loginResult.getMsg().equals("邀请成功")){
//                                                    if(loginResult.getData().getInviter()==0) {
//                                                        final NormalDialog dialog = new NormalDialog(MainActivity.this);
//                                                        dialog.content("您的邀请人ID为：" + inviter_id).style(NormalDialog.STYLE_ONE)//
//                                                                .titleTextSize(23)
//                                                                .titleTextColor(Color.parseColor("#fffd625b"))
//                                                                .cornerRadius(10)
//                                                                .titleLineHeight(0)
//                                                                .show();
//                                                        dialog.setOnBtnClickL(
//                                                                new OnBtnClickL() {
//                                                                    @Override
//                                                                    public void onBtnClick() {
//                                                                        SharedPreferencesHelper.getInstance().saveData("inviter_id", inviter_id);
//                                                                        SharedPreferencesHelper.getInstance().saveData("inviter_time", TimeUtil.getDateTime().getTime());
//                                                                        ToastUtil.showToast("您接受了"+SharedPreferencesHelper.getInstance().getData("inviter_id", "").toString()+"邀请,有效期1小时");
//                                                                        dialog.dismiss();
//                                                                    }
//                                                                }
//
//                                                        );
//                                                    }else {
//                                                        final NormalDialog dialog2 = new NormalDialog(MainActivity.this);
//                                                        dialog2.content("您已经被" + String.valueOf(loginResult.getData().getInviter()) +"邀请过了，无法再次被邀请")
//                                                                .btnNum(1)
//                                                                .btnText("我知道了")
//                                                                .titleLineHeight(0)
//                                                                .cornerRadius(10)
//                                                                .titleTextColor(Color.parseColor("#fffd625b"))
//                                                                .show();
//                                                        dialog2.setOnBtnClickL(
//                                                                new OnBtnClickL() {
//                                                                    @Override
//                                                                    public void onBtnClick() {
//                                                                        dialog2.dismiss();
//                                                                    }
//                                                                });
//                                                    }
//
//                                                }else {
//                                                    ToastUtil.showToast("登录信息错误,请您重新登录");
//                                                }
//
//                                            }
//                                        });
//
//
//                            }else {
//                                LoginActivity.start(MainActivity.this);
//                            }
//                        }
//                        else if(result.startsWith(HttpUtils.API_HOST + "goods/show-")){
//                            final String goods_id = result.replace(HttpUtils.API_HOST + "goods/show-","").replace(".aspx","");
//                            if(!goods_id.isEmpty()) {
//                                try {
//                                    int id = Integer.valueOf(goods_id);
//                                    if (id > 0) {
//                                        GoodsDetailActivity.start(MainActivity.this, String.valueOf(id));
//                                    }
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//                        }
//                        else if(result.equals("孙冰")||result.equals("Gonziy")){
//                            WebViewActivity.loadUrl(MainActivity.this,"http://shop.zhenmeizhixiu.com/gonziy.aspx","");
//
//                        }
//                        else{
//                            final NormalDialog dialog=new NormalDialog(this);
//                            dialog.content("解析结果:" + result)//
//                                    .btnNum(1)
//                                    .titleLineHeight(0)
//                                    .cornerRadius(10)
//                                    .titleTextColor(Color.parseColor("#fffd625b"))
//                                    .btnText("确认")//
//                                    .show();
//                        }
//                    }
//
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    ToastUtil.showToast("解析二维码失败");
//                }
//            }
//        }
    }

}
