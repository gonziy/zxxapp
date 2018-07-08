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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.hedgehog.ratingbar.RatingBar;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.payment.PayBean;
import com.zxxapp.mall.maintenance.bean.shopping.CouponListBean;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.databinding.ActivityConfirmOrderBinding;
import com.zxxapp.mall.maintenance.databinding.ActivityOrderEvaluationBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.alipay.AuthResult;
import com.zxxapp.mall.maintenance.helper.alipay.OrderInfoUtil2_0;
import com.zxxapp.mall.maintenance.helper.alipay.PayResult;
import com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.CouponModel;
import com.zxxapp.mall.maintenance.model.PaymentModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioGroup;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioPurified;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper.sequence;


public class OrderEvaluationActivity extends BaseActivity<ActivityOrderEvaluationBinding> {


    private String orderNo = "";
    private String shopId = "";
    private float score = 5;
    private float score2 = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_evaluation);
        setTitle("服务评价");
        initView();
        if (getIntent() != null) {
            orderNo = (String) getIntent().getSerializableExtra("order_no");
            shopId = (String) getIntent().getSerializableExtra("shop_id");
        }
    }


    private void initView() {


        //设置是否可点击，在需要评分的地方要设置为可点击
        bindingView.ratTest.setmClickable(true);
        bindingView.ratTest.halfStar(false);
        //设置星星总数
        bindingView.ratTest.setStarCount(5);
        //设置星星的宽度
        bindingView.ratTest.setStarImageWidth(40f);
        //设置星星的高度
        bindingView.ratTest.setStarImageHeight(40f);
        //设置星星之间的距离
        bindingView.ratTest.setImagePadding(5f);
        //设置空星星
        bindingView.ratTest.setStarEmptyDrawable(getResources()
                .getDrawable(R.mipmap.ratingbar_star_empty));
        //设置填充的星星
        bindingView.ratTest.setStarFillDrawable(getResources()
                .getDrawable(R.mipmap.ratingbar_star_fill));
        //设置显示的星星个数
        bindingView.ratTest.setStar(5f);
        //设置评分的监听
        bindingView.ratTest.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        ToastUtil.showToast(String.valueOf(RatingCount));
                        score = RatingCount;
                    }
                });



        //设置是否可点击，在需要评分的地方要设置为可点击
        bindingView.ratTest2.setmClickable(true);
        bindingView.ratTest2.halfStar(false);
        //设置星星总数
        bindingView.ratTest2.setStarCount(5);
        //设置星星的宽度
        bindingView.ratTest2.setStarImageWidth(40f);
        //设置星星的高度
        bindingView.ratTest2.setStarImageHeight(40f);
        //设置星星之间的距离
        bindingView.ratTest2.setImagePadding(5f);
        //设置空星星
        bindingView.ratTest2.setStarEmptyDrawable(getResources()
                .getDrawable(R.mipmap.ratingbar_star_empty));
        //设置填充的星星
        bindingView.ratTest2.setStarFillDrawable(getResources()
                .getDrawable(R.mipmap.ratingbar_star_fill));
        //设置显示的星星个数
        bindingView.ratTest2.setStar(5f);
        //设置评分的监听
        bindingView.ratTest2.setOnRatingChangeListener(
                new RatingBar.OnRatingChangeListener() {
                    @Override
                    public void onRatingChange(float RatingCount) {
                        ToastUtil.showToast(String.valueOf(RatingCount));
                        score2 = RatingCount;
                    }
                });

        bindingView.tvSettlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AccountHelper.isLogin()){
                    LoginActivity.start(v.getContext());
                }
                if(score<=0){
                    ToastUtil.showToast("必须选择一个分数");
                    return;
                }
                if(score2<=0){
                    ToastUtil.showToast("必须选择一个分数");
                    return;
                }
                User user = BaseApplication.getInstance().getUser();

                Subscription get = HttpClient.Builder.getZhiXiuServer().orderComment(user.token,orderNo,String.valueOf(score),String.valueOf(score2),bindingView.etSide.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>(){

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {

                                ToastUtil.showToast("系统错误，请您稍后再试");
                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                ToastUtil.showToast("评价成功");
                                OrderEvaluationActivity.this.finish();

                            }
                        });
            }
        });
        showContentView();
    }




    public static void start(Context mContext,String orderNo,String shopId) {
        Intent intent = new Intent(mContext, OrderEvaluationActivity.class);
        intent.putExtra("order_no", orderNo);
        intent.putExtra("shop_id", shopId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(mContext, intent, options.toBundle());
    }

}
