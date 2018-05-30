package com.zxxapp.mall.maintenance.ui.shopping;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
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
import com.zxxapp.mall.maintenance.bean.account.UserAddressBean;
import com.zxxapp.mall.maintenance.bean.shopping.PreOrderBean;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.alipay.AuthResult;
import com.zxxapp.mall.maintenance.helper.alipay.OrderInfoUtil2_0;
import com.zxxapp.mall.maintenance.helper.alipay.PayResult;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AreaModel;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.model.ShoppingModel;
import com.zxxapp.mall.maintenance.model.UserAddressModel;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rx.Subscription;


public class OrderActivity extends BaseActivity<ActivityOrderBinding> {

    private ShoppingOrderAdapter orderGoodsAdapter;
    private ShoppingCartModel mModel;
    private AreaModel areaModel;
    private CartResult cartResult;
    private List<CartResult.DataBean> goodsList = new ArrayList<>();
    private List<PaymentBean> paymentList = new ArrayList<>();
    private ArrayAdapter provinceAdapter;
    private ArrayAdapter cityAdapter;
    private ArrayAdapter areaAdapter;

    public double totalPrice = 0.00;// 购买的商品总价
    private Integer bagCount= 0;
    private Integer zypGoods= 0;

    private IWXAPI iwxapi;


    String selectedPayment = "";

    private static final int RESULT_SELECT = 1;
    private static final int RESULT_CUSTOM = 2;


    private static final int ADDRESS_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mModel = new ShoppingCartModel();
        setTitle("确认订单");
        initView();
        LoadDefaultAddressData();
        initProvince();
        initHintCityAndArea();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADDRESS_REQUEST_CODE) {
            switch (resultCode) { //根据状态码，处理返回结果
                case RESULT_SELECT:
                    Bundle bundle = data.getExtras();   //获取intent里面的bundle对象

                    String accept_name = bundle.getString("accept_name");
                    String mobile = bundle.getString("mobile");
                    String province_name = bundle.getString("province_name");
                    String city_name = bundle.getString("city_name");
                    String area_name = bundle.getString("area_name");
                    String province_id = bundle.getString("province_id");
                    String city_id = bundle.getString("city_id");
                    String area_id = bundle.getString("area_id");
                    String address = bundle.getString("address");

                    bindingView.tvAcceptName.setText(accept_name);
                    bindingView.tvMobile.setText(mobile);
                    bindingView.tvProvince.setText(province_name);
                    bindingView.tvCity.setText(city_name);
                    bindingView.tvArea.setText(area_name);
                    bindingView.tvAddress.setText(address);
                    bindingView.tvHidProvince.setText(province_id);
                    bindingView.tvHidCity.setText(city_id);
                    bindingView.tvHidArea.setText(area_id);
                    bindingView.etAddress.setText(address);
                    bindingView.etAcceptName.setText(accept_name);
                    bindingView.etAcceptMobile.setText(mobile);

                    break;
                case RESULT_CUSTOM:
                    bindingView.tvHidProvince.setText("");
                    bindingView.tvHidCity.setText("");
                    bindingView.tvHidArea.setText("");
                    bindingView.etAddress.setText("");
                    bindingView.etAcceptName.setText("");
                    bindingView.etAcceptMobile.setText("");
                    bindingView.llWriteAddr.setVisibility(View.VISIBLE);
                    bindingView.llEditAddr.setVisibility(View.GONE);
                    break;
                default:
                    bindingView.tvHidProvince.setText("");
                    bindingView.tvHidCity.setText("");
                    bindingView.tvHidArea.setText("");
                    bindingView.etAddress.setText("");
                    bindingView.etAcceptName.setText("");
                    bindingView.etAcceptMobile.setText("");
                    bindingView.llWriteAddr.setVisibility(View.VISIBLE);
                    bindingView.llEditAddr.setVisibility(View.GONE);
                    break;
            }
            statistics();
        }
    }

    public void LoadDefaultAddressData(){
//        User user = BaseApplication.getInstance().getUser();
//        if(user!=null&&user.getUserID()>0) {
//            UserAddressModel model = new UserAddressModel();
//            model.setData(user.getUserName(), user.getPassword());
//            model.getDefaultData(new RequestImpl() {
//                @Override
//                public void loadSuccess(Object object) {
//                    UserAddressBean addressBean = (UserAddressBean)object;
//                    if (addressBean != null && addressBean.getData() != null && addressBean.getData().size() > 0) {
//                        bindingView.llEditAddr.setVisibility(View.VISIBLE);
//                        bindingView.llWriteAddr.setVisibility(View.GONE);
//                        UserAddressBean.DataBean defaultAddrBean = addressBean.getData().get(0);
//                        bindingView.tvAcceptName.setText(defaultAddrBean.getAccept_name());
//                        bindingView.tvMobile.setText(defaultAddrBean.getMobile());
//                        bindingView.tvProvince.setText(defaultAddrBean.getArea().split(",")[0]);
//                        bindingView.tvCity.setText(defaultAddrBean.getArea().split(",")[1]);
//                        bindingView.tvArea.setText(defaultAddrBean.getArea().split(",")[2]);
//                        bindingView.tvAddress.setText(defaultAddrBean.getAddress());
//
//                        bindingView.tvHidProvince.setText(defaultAddrBean.getArea_code().split(",")[0]);
//                        bindingView.tvHidCity.setText(defaultAddrBean.getArea_code().split(",")[1]);
//                        bindingView.tvHidArea.setText(defaultAddrBean.getArea_code().split(",")[2]);
//
//                        bindingView.etAddress.setText(defaultAddrBean.getAddress());
//                        bindingView.etAcceptName.setText(defaultAddrBean.getAccept_name());
//                        bindingView.etAcceptMobile.setText(defaultAddrBean.getMobile());
//
//                    }else {
//                        bindingView.llEditAddr.setVisibility(View.GONE);
//                        bindingView.llWriteAddr.setVisibility(View.VISIBLE);
//                    }
//                    statistics();
//                }
//
//
//                @Override
//                public void loadFailed() {
//
//                }
//
//                @Override
//                public void addSubscription(Subscription subscription) {
//                }
//            });
//        }else {
//            LoginActivity.start(bindingView.xrvCart.getContext());
//        }
    }

    private void initHintCityAndArea() {
        final List<String> cities = new ArrayList<String>();
        cities.add("城市");
        cityAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout,cities);
        bindingView.spCity.setAdapter(cityAdapter);

        final List<String> areas = new ArrayList<String>();
        areas.add("区县");
        areaAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout,areas);
        bindingView.spArea.setAdapter(areaAdapter);
    }

    private void initProvince() {
        final List<String> provinces = new ArrayList<String>();
        final List<String> provinces_ids = new ArrayList<String>();
        bindingView.spProvince.setHeight(BaseTools.getScreenHeigh(OrderActivity.this)/2);
        areaModel = new AreaModel();
        areaModel.getProvinceData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                AreaBean areaBean = (AreaBean) object;

                provinceAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout,provinces);

                if (areaBean != null && areaBean.getData() != null && areaBean.getData().size() > 0) {
                    for (AreaBean.DataBean data: areaBean.getData()) {
                        provinces.add(data.getAreaName());
                        provinces_ids.add(data.getAreaId());
                    }
                    bindingView.spProvince.setAdapter(provinceAdapter);

                }
            }

            @Override
            public void loadFailed() {
            }

            @Override
            public void addSubscription(Subscription subscription) {
            }
        });
        bindingView.spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    bindingView.tvHidProvince.setText(provinces_ids.get(position));
                    final List<String> cities = new ArrayList<String>();
                    cities.add("城市");
                    cityAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout, cities);
                    bindingView.spCity.setAdapter(cityAdapter);

                    final List<String> areas = new ArrayList<String>();
                    areas.add("区县");
                    areaAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout, areas);
                    bindingView.spArea.setAdapter(areaAdapter);
                    initCity(provinces.get(position));



                    statistics();
                }else{
                    final List<String> cities = new ArrayList<String>();
                    cities.add("城市");
                    cityAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout, cities);
                    bindingView.spCity.setAdapter(cityAdapter);

                    final List<String> areas = new ArrayList<String>();
                    areas.add("区县");
                    areaAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout, areas);
                    bindingView.spArea.setAdapter(areaAdapter);
                }
                bindingView.tvHidCity.setText("");
                bindingView.tvHidArea.setText("");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initCity(String parentName) {
        final List<String> cities = new ArrayList<String>();
        final List<String> cities_ids = new ArrayList<String>();
        cityAdapter.clear();
        areaModel.setData(parentName);
        areaModel.getCityData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                AreaBean areaBean = (AreaBean) object;
                cityAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout,cities);
                if (areaBean != null && areaBean.getData() != null && areaBean.getData().size() > 0) {
                    for (AreaBean.DataBean data: areaBean.getData()) {
                        cities.add(data.getAreaName());
                        cities_ids.add(data.getAreaId());
                    }
                    bindingView.spCity.setAdapter(cityAdapter);
                    bindingView.tvHidArea.setText("");
                }
            }

            @Override
            public void loadFailed() {
            }

            @Override
            public void addSubscription(Subscription subscription) {
            }
        });
        bindingView.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    bindingView.tvHidCity.setText(cities_ids.get(position));
                    initArea(cities.get(position));
                    bindingView.tvHidArea.setText("");
                }else {
                    bindingView.tvHidArea.setText("");
                    final List<String> areas = new ArrayList<String>();
                    areas.add("区县");
                    areaAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout, areas);
                    bindingView.spArea.setAdapter(areaAdapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initArea(String parentName) {
        final List<String> areas = new ArrayList<String>();
        final List<String> areas_ids = new ArrayList<String>();
        areaAdapter.clear();
        areaModel.setData(parentName);
        areaModel.getAreaData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                AreaBean areaBean = (AreaBean) object;
                areaAdapter = new ArrayAdapter(OrderActivity.this, R.layout.item_spinner_layout,areas);
                if (areaBean != null && areaBean.getData() != null && areaBean.getData().size() > 0) {
                    for (AreaBean.DataBean data: areaBean.getData()) {
                        areas.add(data.getAreaName());
                        areas_ids.add(data.getAreaId());
                    }
                    bindingView.spArea.setAdapter(areaAdapter);
                }
            }

            @Override
            public void loadFailed() {
            }

            @Override
            public void addSubscription(Subscription subscription) {
            }
        });
        bindingView.spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    bindingView.tvHidArea.setText(areas_ids.get(position));
                }else {
                    bindingView.tvHidArea.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        bindingView.tvQuantityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bagCount = Integer.valueOf(bindingView.etQuantity.getText().toString());
                bagCount++;
                bindingView.etQuantity.setText(String.valueOf(bagCount));
                statistics();
            }
        });
        bindingView.tvQuantitySub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bagCount = Integer.valueOf(bindingView.etQuantity.getText().toString());
                if(Integer.valueOf(bindingView.etQuantity.getText().toString())>0) {
                    bagCount--;
                    bindingView.etQuantity.setText(String.valueOf(bagCount));
                    statistics();
                }
            }
        });
        bindingView.tvSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
        bindingView.llEditAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressListActivity.startForResult(v.getContext());
            }
        });


        bindingView.xrvCart.setPullRefreshEnabled(false);
        orderGoodsAdapter = new ShoppingOrderAdapter();
        if(AccountHelper.isLogin()) {
            loadCartListData();
            showContentView();
        }else
        {
            LoginActivity.start(this);
            OrderActivity.this.finish();
        }


    }

    public void submitOrder()
    {
//        Integer payment_id = 0;
//        if(selectedPayment.equals("weixin"))
//        {
//            payment_id=11;
//        }else if(selectedPayment.equals("alipay"))
//        {
//            payment_id=10;
//        }else {
//            payment_id = 0;
//        }
//
//
//        Integer express_id = 0;
//        if(bindingView.tvHidProvince.getText().toString().equals("150000")
//                ||bindingView.tvHidProvince.getText().toString().equals("540000")
//                ||bindingView.tvHidProvince.getText().toString().equals("650000")
//                ||bindingView.tvHidProvince.getText().toString().equals("640000")
//                ||bindingView.tvHidProvince.getText().toString().equals("620000")
//                ||bindingView.tvHidProvince.getText().toString().equals("450000")
//                ||bindingView.tvHidProvince.getText().toString().equals("460000")
//                ||bindingView.tvHidProvince.getText().toString().equals("530000")
//                ||bindingView.tvHidProvince.getText().toString().equals("630000")
//                ){
//            express_id=3;
//        }
//        else if(bindingView.tvHidProvince.getText().toString().equals("830000")
//                ||bindingView.tvHidProvince.getText().toString().equals("820000")
//                ||bindingView.tvHidProvince.getText().toString().equals("810000")
//                ){
//            express_id=2;
//        }else{
//            express_id=1;
//        }
//
//
//        User user = BaseApplication.getInstance().getUser();
//        if(user!=null&&user.getUserID()>0) {
//
//            if(payment_id<=0 || selectedPayment.isEmpty()){
//                ToastUtil.showToast("请选择支付方式");
//                return;
//            }
//            if(express_id<=0){
//                ToastUtil.showToast("请检查您的收货地址");
//                return;
//            }
//            if(bindingView.tvHidProvince.getText().toString().isEmpty()){
//                ToastUtil.showToast("请重新选择省份");
//                return;
//            }
//            if(bindingView.tvHidCity.getText().toString().isEmpty()){
//                ToastUtil.showToast("请重新选择城市");
//                return;
//            }
//            if(bindingView.tvHidArea.getText().toString().isEmpty()){
//                ToastUtil.showToast("请重新选择区县");
//                return;
//            }
//            if(bindingView.etAddress.getText().toString().isEmpty()){
//                ToastUtil.showToast("请检查您的收件地址");
//                return;
//            }
//            if(bindingView.etAddress.getText().toString().length()<=4){
//                ToastUtil.showToast("您的收件地址可能不准确");
//                return;
//            }
//            if(bindingView.etAcceptName.getText().toString().isEmpty()){
//                ToastUtil.showToast("请检查收件人姓名");
//                return;
//            }
//            if(bindingView.etAcceptMobile.getText().toString().isEmpty()){
//                ToastUtil.showToast("请检查您的收件联系电话");
//                return;
//            }
//            if(bindingView.etAcceptMobile.getText().toString().length()<7){
//                ToastUtil.showToast("您的收件联系电话可能不准确");
//                return;
//            }
//            //showLoading();
//
//            String tmpInviter = "";
//
//            if(!SharedPreferencesHelper.getInstance().getData("inviter_id","").toString().isEmpty()) {
//
//                Long inviteTime = (Long) SharedPreferencesHelper.getInstance().getData("inviter_time", 0L);
//                long interval = new Date().getTime() - inviteTime;
//                if (interval <= 7200000) {
//                    tmpInviter = SharedPreferencesHelper.getInstance().getData("inviter_id", "").toString();
//                    ToastUtil.showToast("此订单交易完成后，您的上级ID为:" + tmpInviter);
//                }
//            }
//
//
//            ShoppingModel shoppingModel = new ShoppingModel();
//            shoppingModel.setData(
//                    user.getUserName(),
//                    user.getPassword(),
//                    "0",
//                    Integer.parseInt(bindingView.etQuantity.getText().toString()),
//                    payment_id,
//                    express_id,
//                    bindingView.etAcceptName.getText().toString(),
//                    bindingView.tvHidProvince.getText().toString(),
//                    bindingView.tvHidCity.getText().toString(),
//                    bindingView.tvHidArea.getText().toString(),
//                    bindingView.etAddress.getText().toString(),
//                    bindingView.etAcceptMobile.getText().toString(),
//                    tmpInviter
//            );
//
//            showLoading();
//            shoppingModel.submitOrderData(new RequestImpl() {
//                @Override
//                public void loadSuccess(Object object) {
//                    PreOrderBean preOrderBean = (PreOrderBean) object;
//                    if (preOrderBean != null) {
//                        if (preOrderBean.getError() == 1) {
//                            if(selectedPayment.equals("alipay")) {
////                                alipayV2("0.01", preOrderBean.getData().getId());
//                                alipayV2(StringUtils.doubleToString(preOrderBean.getData().getAmount()),preOrderBean.getData().getId());
//                                //ToastUtil.showToast(preOrderBean.getMsg() + "|" + preOrderBean.getData().getId());
//                            }else if(selectedPayment.equals("weixin")){
//                                wxpay(StringUtils.doubleToString(preOrderBean.getData().getAmount()),
//                                        preOrderBean.getData().getId(),
//                                        preOrderBean.getData().getWx_prepay_id(),
//                                        preOrderBean.getData().getWx_nonce_str(),
//                                        preOrderBean.getData().getWx_timestamp(),
//                                        preOrderBean.getData().getWx_sign()
//                                        );
//                                OrderActivity.this.finish();
////                                wxpay("0.01",
////                                        preOrderBean.getData().getId(),
////                                        preOrderBean.getData().getWx_prepay_id(),
////                                        preOrderBean.getData().getWx_nonce_str(),
////                                        preOrderBean.getData().getWx_timestamp(),
////                                        preOrderBean.getData().getWx_sign()
////                                );
//
//                            }else {
//                                showContentView();
//                                final NormalDialog dialog=new NormalDialog(OrderActivity.this);
//                                dialog.content("订单提交失败，支付方式不支持")
//                                        .btnNum(1)
//                                        .titleLineHeight(0)
//                                        .cornerRadius(10)
//                                        .titleTextColor(Color.parseColor("#fffd625b"))
//                                        .btnText("确认")
//                                        .show();
//                            }
//                        }else {
//                            showContentView();
//                            final NormalDialog dialog=new NormalDialog(OrderActivity.this);
//                            dialog.content("订单提交失败，"+preOrderBean.getMsg())
//                                    .btnNum(1)
//                                    .titleLineHeight(0)
//                                    .cornerRadius(10)
//                                    .titleTextColor(Color.parseColor("#fffd625b"))
//                                    .btnText("确认")
//                                    .show();
//                        }
//                    }else {
//                        showContentView();
//                        ToastUtil.showToast(preOrderBean.getMsg());
//                    }
//                }
//
//                @Override
//                public void loadFailed() {
//
//                }
//
//                @Override
//                public void addSubscription(Subscription subscription) {
//
//                }
//            });
//        }else {
//            LoginActivity.start(bindingView.xrvCart.getContext());
//        }
    }

    public void statistics()
    {
        if(bindingView.tvHidProvince.getText().toString().equals("150000")
                ||bindingView.tvHidProvince.getText().toString().equals("540000")
                ||bindingView.tvHidProvince.getText().toString().equals("650000")
                ||bindingView.tvHidProvince.getText().toString().equals("640000")
                ||bindingView.tvHidProvince.getText().toString().equals("620000")
                ||bindingView.tvHidProvince.getText().toString().equals("450000")
                ||bindingView.tvHidProvince.getText().toString().equals("460000")
                ||bindingView.tvHidProvince.getText().toString().equals("530000")
                ||bindingView.tvHidProvince.getText().toString().equals("630000")
                ){
            bindingView.tvLogisticsPrice.setText("15.00");
        }
        else if(bindingView.tvHidProvince.getText().toString().equals("830000")
                ||bindingView.tvHidProvince.getText().toString().equals("820000")
                ||bindingView.tvHidProvince.getText().toString().equals("810000")
                ){
            bindingView.tvLogisticsPrice.setText("100.00");
        }else{
            bindingView.tvLogisticsPrice.setText("0.00");
        }
        if(zypGoods>0){
            if(Double.valueOf(bindingView.tvLogisticsPrice.getText().toString())==0){
                bindingView.tvZypPrice.setText("5.00");
            }else {
                bindingView.tvZypPrice.setText("0.00");
            }
        }

        if (goodsList != null && goodsList.size() > 0) {
            totalPrice = 0.00;
            for (CartResult.DataBean cart:goodsList) {
                if(cart.getSelected()==1){
                    totalPrice += cart.getSell_price()* cart.getQuantity();
                }
            }
        }
        Double bagFee = Double.valueOf(bindingView.etQuantity.getText().toString())*2;
        Double ExpressFee = Double.valueOf(bindingView.tvLogisticsPrice.getText().toString());
        Double zypFee = Double.valueOf(bindingView.tvZypPrice.getText().toString());
        bindingView.tvBagPrice.setText(StringUtils.doubleToString(bagFee));
        bindingView.tvAllPrice.setText(StringUtils.doubleToString(totalPrice + bagFee+ExpressFee+zypFee));
        bindingView.tvTotalPrice.setText(StringUtils.doubleToString(totalPrice + bagFee+ExpressFee+zypFee));
    }

    /**
     * 设置adapter
     */
    private void setOrderGoodsAdapter(CartResult cartResult) {
        orderGoodsAdapter.clear();
        orderGoodsAdapter.addAll(cartResult.getData());
        bindingView.xrvCart.setLayoutManager(new LinearLayoutManager(this));
        bindingView.xrvCart.setAdapter(orderGoodsAdapter);

        bindingView.xrvCart.refreshComplete();
        orderGoodsAdapter.notifyDataSetChanged();

    }



    public void loadCartListData() {

    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, OrderActivity.class);
        mContext.startActivity(intent);
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
                        WebViewActivity.loadUrl(OrderActivity.this, HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付成功");
                        OrderActivity.this.finish();
                        ToastUtil.showToast("支付成功");

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        WebViewActivity.loadUrl(OrderActivity.this,HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付失败");
                        OrderActivity.this.finish();
                        ToastUtil.showToast("支付失败");
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
                PayTask alipay = new PayTask(OrderActivity.this);
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
                AuthTask authTask = new AuthTask(OrderActivity.this);
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
            Toast.makeText(OrderActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
