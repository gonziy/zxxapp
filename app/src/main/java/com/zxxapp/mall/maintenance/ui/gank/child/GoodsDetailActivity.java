package com.zxxapp.mall.maintenance.ui.gank.child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;

import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.bean.shopping.CartCount;
import com.zxxapp.mall.maintenance.databinding.ActivityGoodsDetailBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AreaModel;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.shopping.GoodsSpecDialog;
import com.zxxapp.mall.maintenance.ui.shopping.OrderActivity;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoodsDetailActivity extends BaseActivity<ActivityGoodsDetailBinding> {

    private GoodsDetailBean mGoodsDetailBean;
    private String goodsId;
    private String mGoodsDetailUrl;
    private String mGoodsDetailName;
    private ShoppingCartModel mModel;
    private MainActivity activity;
    public final static String EXTRA_PARAM = "goodsId";
    private String groupStr = "";
    GoodsSpecDialog specDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        if (getIntent() != null) {
            goodsId = (String) getIntent().getSerializableExtra(EXTRA_PARAM);
        }
        mModel = new ShoppingCartModel();
        loadGoodsDetail(goodsId);
        initView(goodsId,"0",1);
        getGoodsCount();

    }

    private void initView(final String article_id, final String goods_id, final Integer quantity) {

        bindingView.tvGoodsMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        bindingView.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getMainActivity().toViewPager(2);
                GoodsDetailActivity.this.finish();
            }
        });


        bindingView.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupStr.isEmpty()) {
                    User user = BaseApplication.getInstance().getUser();
                    if (user != null && user.getUserID() > 0) {
                        mModel.setIntoCartData(user.getUserName(), user.getPassword(), Integer.valueOf(article_id), Integer.valueOf(goods_id), quantity);
                        mModel.setIntoCart(new RequestImpl() {
                            @Override
                            public void loadSuccess(Object object) {
                                ResultBean resultBean = (ResultBean) object;
                                if (resultBean != null) {
                                    if (resultBean.getError() == 0) {
                                        int cartCount = Integer.valueOf(String.valueOf(bindingView.tvCartNum.getText()));
                                        bindingView.tvCartNum.setText(String.valueOf(cartCount + 1));
                                        final NormalDialog dialog = new NormalDialog(GoodsDetailActivity.this);
                                        dialog.content("成功添加到购物车").style(NormalDialog.STYLE_TWO)
                                                .btnText("继续逛逛", "结算")
                                                .titleTextSize(23)
                                                .titleTextColor(Color.parseColor("#fffd625b"))
                                                .cornerRadius(10)
                                                .titleLineHeight(0)
                                                .show();
                                        dialog.setOnBtnClickL(
                                                new OnBtnClickL() {
                                                    @Override
                                                    public void onBtnClick() {
                                                        dialog.dismiss();
                                                    }
                                                },
                                                new OnBtnClickL() {
                                                    @Override
                                                    public void onBtnClick() {
                                                        MainActivity.getMainActivity().toViewPager(2);
                                                        dialog.dismiss();
                                                        GoodsDetailActivity.this.finish();

                                                    }
                                                }

                                        );

                                    } else {
                                        ToastUtil.showToast(resultBean.getMsg());
                                    }
                                }

                            }

                            @Override
                            public void loadFailed() {
                                //showError();
                            }

                            @Override
                            public void addSubscription(Subscription subscription) {

                            }
                        });
                    } else {
                        LoginActivity.start(GoodsDetailActivity.this);
                    }
                }else {
                    if(mGoodsDetailBean!=null) {
                        specDialog = new GoodsSpecDialog(mGoodsDetailBean);
                        specDialog.show(getSupportFragmentManager(),"");
                    }
                }
            }
        });
    }

    private void loadGoodsDetail(String goodsId) {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getGoodsServer().getGoodsDetailData("goodsdetail",goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsDetailBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                        DebugUtil.error("------onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.error("------onError");
                        showError();
                    }

                    @Override
                    public void onNext(GoodsDetailBean goodsDetailBean) {
                        DebugUtil.error("------onNext");
                        mGoodsDetailBean = goodsDetailBean;
                        setTitle(goodsDetailBean.getTitle());
                        mGoodsDetailUrl = HttpUtils.API_HOST + "goods/show-" + goodsDetailBean.getId() +".aspx";
                        mGoodsDetailName = goodsDetailBean.getTitle();
                        bindingView.setGoodsDetailBean(goodsDetailBean);
                        bindingView.executePendingBindings();
                        groupStr = goodsDetailBean.getGoods_group();

                        if(goodsDetailBean.getStock_quantity()==0){
                            if(groupStr.isEmpty()){
                                if(goodsDetailBean.getIs_msg()==1){
                                    bindingView.btnBuy.setText("即将上市");
                                }else {
                                    bindingView.btnBuy.setText("待补货");
                                }
                                bindingView.btnBuy.setBackgroundColor(Color.parseColor("#cccccc"));
                                bindingView.btnBuy.setClickable(false);
                            }else {
                                bindingView.btnBuy.setText("加入购物车");
                            }
                        }else {
                            bindingView.btnBuy.setText("加入购物车");
                            if(goodsDetailBean.getId().equals("2122")){
                                bindingView.btnBuy.setText("升级贵宾");
                            }
                        }
                        CookieSyncManager.createInstance(GoodsDetailActivity.this);
                        CookieManager cookieMgr = CookieManager.getInstance();
                        cookieMgr.setAcceptCookie(true);
                        cookieMgr.setCookie(HttpUtils.API_HOST, "dt_browser=android");
                        CookieSyncManager.getInstance().sync();
                        bindingView.webviewDetail.loadUrl(mGoodsDetailUrl);
                    }
                });
        addSubscription(get);
    }

    public void getGoodsCount() {
        User user = BaseApplication.getInstance().getUser();
        if (AccountHelper.isLogin()) {
            mModel.setData(user.getUserName(),user.getPassword());
            mModel.getGoodsCount(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    CartCount cartCount = (CartCount)object;
                    bindingView.tvCartNum.setText(String.valueOf(cartCount.getCount()));
                }

                @Override
                public void loadFailed() {
                    bindingView.tvCartNum.setText("0");
                }

                @Override
                public void addSubscription(Subscription subscription) {

                }
            });
        }else {
            bindingView.tvCartNum.setText("0");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        getGoodsCount();
    }

    @Override
    protected void onRefresh() {
        loadGoodsDetail(goodsId);
    }


    public static void start(Activity context, String id) {

        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(EXTRA_PARAM, id);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


}
