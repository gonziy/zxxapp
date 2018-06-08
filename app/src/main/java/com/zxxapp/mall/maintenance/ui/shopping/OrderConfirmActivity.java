package com.zxxapp.mall.maintenance.ui.shopping;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ShoppingOrderAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.payment.PayBean;
import com.zxxapp.mall.maintenance.bean.shopping.CouponListBean;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.databinding.ActivityConfirmOrderBinding;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderBinding;
import com.zxxapp.mall.maintenance.helper.TencentIM.StringUtil;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.alipay.AuthResult;
import com.zxxapp.mall.maintenance.helper.alipay.OrderInfoUtil2_0;
import com.zxxapp.mall.maintenance.helper.alipay.PayResult;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AreaModel;
import com.zxxapp.mall.maintenance.model.CouponModel;
import com.zxxapp.mall.maintenance.model.PaymentModel;
import com.zxxapp.mall.maintenance.model.ShopListModel;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.CouponListByAccountIdActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioGroup;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioPurified;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.Subscription;


public class OrderConfirmActivity extends BaseActivity<ActivityConfirmOrderBinding> {

    private PaymentModel mModel;

    public double totalPrice = 0.00;// 购买的商品总价
    private Integer bagCount= 0;
    private Integer zypGoods= 0;

    private IWXAPI iwxapi;
    private Double amount = 0.00;
    private Double orderAmount = 0.00;
    private String orderNo = "";
    private CouponListBean couponList;
    private List<CouponListBean.DataBean.ListBean> coupons;
    private ArrayAdapter couponAdapter;
    private String selectedCouponId = "";

    String selectedPayment = "";

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;


    private static final int ADDRESS_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        mModel = new PaymentModel();
        setTitle("付款");
        initView();
        if (getIntent() != null) {
            orderNo = (String) getIntent().getSerializableExtra("order_no");
            amount = (Double) getIntent().getSerializableExtra("amount");
            orderAmount = amount;
            bindingView.tvOrderAmount.setText("需支付：" + StringUtils.doubleToString(amount));
            bindingView.tvTotalPrice.setText(StringUtils.doubleToString(amount));
        }
    }


    private void initView() {
        bindingView.paymentGroup.setOnCheckedChangeListener(new PayRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(PayRadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                if(radioButtonId==bindingView.pAlipay.getId()){
                    selectedPayment = "alipay";
                }else if(radioButtonId == bindingView.pWeixin.getId()){
                    selectedPayment = "weixin";
                }else {
                    ToastUtil.showToast("您选择了不可用的支付方式");
                }
                for (int i =0;i<bindingView.paymentGroup.getChildCount();i++){
                    ((PayRadioPurified)bindingView.paymentGroup.getChildAt(i)).setChangeImg(checkedId);
                }
            }
        });
        bindingView.tvTotalPrice.setText("0.01");
        bindingView.tvSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });

        CouponModel couponModel = new CouponModel();
        couponModel.setData(BaseApplication.getInstance().getUser().getToken());
        couponModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                couponList = (CouponListBean) object;
                if(couponList.getSuccess().equals("true") && !couponList.getData().getList().isEmpty()){
                    coupons = couponList.getData().getList();
                    if(!coupons.isEmpty()){
                        final List<String> cs = new ArrayList<String>();
                        cs.add("===请选择优惠券===");
                        for (CouponListBean.DataBean.ListBean bean:couponList.getData().getList()
                                ) {
                            cs.add(bean.getShopName() +" 优惠"+   StringUtils.doubleToString(bean.getCouponAmount()));
                        }
                        couponAdapter = new ArrayAdapter(OrderConfirmActivity.this, R.layout.item_spinner_layout,cs);
                        bindingView.spCoupon.setAdapter(couponAdapter);
                        bindingView.spCoupon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position > 0) {
                                    selectedCouponId = String.valueOf(coupons.get(position - 1).getCouponId());
                                    amount = amount - coupons.get(position - 1).getCouponAmount();
                                    bindingView.tvOrderAmount.setText("需支付：" + StringUtils.doubleToString(amount));
                                    bindingView.tvTotalPrice.setText(StringUtils.doubleToString(amount));
                                }else {
                                    amount = orderAmount;
                                    bindingView.tvOrderAmount.setText("需支付：" + StringUtils.doubleToString(amount));
                                    bindingView.tvTotalPrice.setText(StringUtils.doubleToString(amount));
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }else {
                        final List<String> cs = new ArrayList<String>();
                        cs.add("===无优惠券===");
                        couponAdapter = new ArrayAdapter(OrderConfirmActivity.this, R.layout.item_spinner_layout,cs);
                        bindingView.spCoupon.setAdapter(couponAdapter);
                    }



                }else{

                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });


        showContentView();
    }

    public void submitOrder()
    {
        String couponId = "";

        if(selectedPayment.equals("alipay")) {
            mModel.setData(orderNo,"alipay",selectedCouponId);
            mModel.Payment(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    if(!object.equals(null)) {
                        PayBean payBean = (PayBean) object;
                        if(!payBean.equals(null)){
                            if(payBean.getSuccess().equals("true")){
                                alipayV2(StringUtils.doubleToString(amount), orderNo);
                                return;
                            }else {
                                ToastUtil.showToast(payBean.getMsg());
                                return;
                            }
                        }else {
                            ToastUtil.showToast("object无法转换为PayBean,返回的json数据有误");
                            return;
                        }
                    }else {
                        ToastUtil.showToast("object为null,返回的json数据有误");
                        return;
                    }
                }

                @Override
                public void loadFailed() {
                    ToastUtil.showToast("提交失败");
                    return;

                }

                @Override
                public void addSubscription(Subscription subscription) {

                }
            });

//            alipayV2(StringUtils.doubleToString(0.01,preOrderBean.getData().getId());
            //ToastUtil.showToast(preOrderBean.getMsg() + "|" + preOrderBean.getData().getId());
        }else if(selectedPayment.equals("weixin")){
            mModel.setData(orderNo,"weixin",selectedCouponId);
            mModel.Payment(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    if(!object.equals(null)) {
                        PayBean payBean = (PayBean) object;
                        if(!payBean.equals(null)){
                            if(payBean.getCode().equals("200")){
                                if(payBean.getData().getPrepayid().isEmpty()){
                                    ToastUtil.showToast("prepayid为空");
                                    return;
                                }
                                if(payBean.getData().getNoncestr().isEmpty()){
                                    ToastUtil.showToast("noncestr为空");
                                    return;
                                }
                                if(String.valueOf(payBean.getData().getTimestamp()).isEmpty()){
                                    ToastUtil.showToast("timestamp为空");
                                    return;
                                }
                                if(payBean.getData().getSign().isEmpty()){
                                    ToastUtil.showToast("sign为空");
                                    return;
                                }

                                wxpay(
                                        StringUtils.doubleToString(amount),
                                        orderNo,
                                        payBean.getData().getPrepayid(),
                                        payBean.getData().getNoncestr(),
                                        String.valueOf(payBean.getData().getTimestamp()),
                                        payBean.getData().getSign()
                                );

                                return;
                            }else {
                                ToastUtil.showToast(payBean.getMsg());
                                return;
                            }
                        }else {
                            ToastUtil.showToast("object无法转换为PayBean,返回的json数据有误");
                            return;
                        }
                    }else {
                        ToastUtil.showToast("object为null,返回的json数据有误");
                        return;
                    }
                }

                @Override
                public void loadFailed() {
                    ToastUtil.showToast("提交失败");
                    return;
                }

                @Override
                public void addSubscription(Subscription subscription) {

                }
            });



//            wxpay(StringUtils.doubleToString(preOrderBean.getData().getAmount()),
//                    preOrderBean.getData().getId(),
//                    preOrderBean.getData().getWx_prepay_id(),
//                    preOrderBean.getData().getWx_nonce_str(),
//                    preOrderBean.getData().getWx_timestamp(),
//                    preOrderBean.getData().getWx_sign()
//            );
            OrderConfirmActivity.this.finish();
        }else {
            showContentView();
            final NormalDialog dialog=new NormalDialog(OrderConfirmActivity.this);
            dialog.content("订单提交失败，支付方式不支持")
                    .btnNum(1)
                    .titleLineHeight(0)
                    .cornerRadius(10)
                    .titleTextColor(Color.parseColor("#fffd625b"))
                    .btnText("确认")
                    .show();
        }
    }


    public static void start(Context mContext,Double amount,String orderNo) {
        Intent intent = new Intent(mContext, OrderConfirmActivity.class);
        intent.putExtra("amount", amount);
        intent.putExtra("order_no", orderNo);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AppConfig.ALIPAY_SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String outOrderNo = "";
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(resultInfo);
                        if(jsonObject!=null){
                            JSONObject jsonResponse = jsonObject.getJSONObject("alipay_trade_app_pay_response");
                            outOrderNo = jsonResponse.getString("out_trade_no");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //WebViewActivity.loadUrl(OrderConfirmActivity.this, HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付成功");

                        final NormalDialog dialog=new NormalDialog(OrderConfirmActivity.this);
                        dialog.content("支付成功")
                                .btnNum(1)
                                .titleLineHeight(0)
                                .cornerRadius(10)
                                .titleTextColor(Color.parseColor("#fffd625b"))
                                .btnText("确认")
                                .show();
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                        OrderConfirmActivity.this.finish();
                                    }
                                }
                         );

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //WebViewActivity.loadUrl(OrderConfirmActivity.this,HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付失败");
                        final NormalDialog dialog=new NormalDialog(OrderConfirmActivity.this);
                        dialog.content("支付失败")
                                .btnNum(1)
                                .titleLineHeight(0)
                                .cornerRadius(10)
                                .titleTextColor(Color.parseColor("#fffd625b"))
                                .btnText("确认")
                                .show();
                        dialog.setOnBtnClickL(
                                new OnBtnClickL() {
                                    @Override
                                    public void onBtnClick() {
                                        dialog.dismiss();
                                        OrderConfirmActivity.this.finish();
                                    }
                                }
                        );
                    }
                    break;
                }
                case AppConfig.ALIPAY_SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.showToast("授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.showToast("授权失败" + String.format("authCode:%s", authResult.getAuthCode()));

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    /**
     *
     *
     * @param amount
     * @param order_no
     */
    public void alipayV2(String amount, String order_no) {
        if (TextUtils.isEmpty(AppConfig.ALIPAY_APPID) || (TextUtils.isEmpty(AppConfig.ALIPAY_RSA2_PRIVATE) && TextUtils.isEmpty(AppConfig.ALIPAY_RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA2_PRIVATE/RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (AppConfig.ALIPAY_RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AppConfig.ALIPAY_APPID, rsa2,amount,order_no);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? AppConfig.ALIPAY_RSA2_PRIVATE : AppConfig.ALIPAY_RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderConfirmActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = AppConfig.ALIPAY_SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(AppConfig.ALIPAY_PID) || TextUtils.isEmpty(AppConfig.ALIPAY_APPID)
                || (TextUtils.isEmpty(AppConfig.ALIPAY_RSA2_PRIVATE) && TextUtils.isEmpty(AppConfig.ALIPAY_RSA_PRIVATE))
                || TextUtils.isEmpty(AppConfig.ALIPAY_TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (AppConfig.ALIPAY_RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(AppConfig.ALIPAY_PID, AppConfig.ALIPAY_APPID, AppConfig.ALIPAY_TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? AppConfig.ALIPAY_RSA2_PRIVATE : AppConfig.ALIPAY_RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(OrderConfirmActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = AppConfig.ALIPAY_SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    public void wxpay(String amount, String order_no,String prepay_id,String noncestr,String timestamp,String sign) {
        iwxapi = WXAPIFactory.createWXAPI(this, AppConfig.WX_APPID);

        try {
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId = AppConfig.WX_APPID;
                req.partnerId = AppConfig.WX_PARTNERID;
                req.prepayId = prepay_id;
                req.nonceStr = noncestr;
                req.timeStamp = timestamp;
                req.packageValue = "Sign=WXPay";
                req.sign = sign;
                req.extData	= order_no; // optional
                //Toast.makeText(OrderActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                iwxapi.sendReq(req);


        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            Toast.makeText(OrderConfirmActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
