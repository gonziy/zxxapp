package com.zxxapp.mall.maintenance.ui.shopping;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseDialogFragment;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.GoodsDetailBean;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.DebugUtil;
import com.zxxapp.mall.maintenance.utils.DensityUtil;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.FlowLayout;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class GoodsSpecDialog extends BaseDialogFragment {

    private GoodsDetailBean goodsDetailBean;

    private ShoppingCartModel mModel;
    LinearLayout ll_all;
    LinearLayout ll_specs;
    Button btn_add_cart;
    TextView tv_seller_price;
    TextView tv_market_price;
    TextView tv_title;
    TextView tv_close;
    ImageView iv_thumb;
    private FlowLayout flowLayout;
    private String selectedGoodsId = "";

    public GoodsSpecDialog() {
    }

    @SuppressLint("ValidFragment")
    public GoodsSpecDialog(GoodsDetailBean goodsDetailBeans) {
        this.goodsDetailBean = goodsDetailBeans;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return LayoutInflater.from(getContext()).inflate(
                R.layout.dialog_choose_spec, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mModel = new ShoppingCartModel();
        initView();

    }
    private void loadGoodsDetail(String goodsId) {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getGoodsServer().getGoodsDetailData("goodsdetail",goodsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodsDetailBean>() {
                    @Override
                    public void onCompleted() {
                        DebugUtil.error("------onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        DebugUtil.error("------onError");
                    }

                    @Override
                    public void onNext(GoodsDetailBean goodsDetailBean) {
                        DebugUtil.error("------onNext");
                        tv_title.setText(goodsDetailBean.getTitle());
                        tv_market_price.setText(StringUtils.doubleToString(goodsDetailBean.getMarket_price()));
                        tv_seller_price.setText(StringUtils.doubleToString(goodsDetailBean.getSell_price()));
                        ImgLoadUtil.showImg(iv_thumb,HttpUtils.API_HOST.substring(0,HttpUtils.API_HOST.length()-1) + goodsDetailBean.getImg_url());
                        Integer quantity = goodsDetailBean.getStock_quantity();
                        if(quantity<=0){
                            if(goodsDetailBean.getIs_msg()==1){
                                btn_add_cart.setText("即将上市");
                            }else{
                                btn_add_cart.setText("待补货");
                            }
                            btn_add_cart.setBackgroundColor(Color.parseColor("#cccccc"));
                            btn_add_cart.setClickable(false);
                        }else {
                            btn_add_cart.setText("确定");
                            btn_add_cart.setBackgroundColor(Color.parseColor("#fffd625b"));
                            btn_add_cart.setClickable(true);

                        }
                    }
                });
    }


    private void initView() {
        if(!goodsDetailBean.getGoods_group().isEmpty()){
            flowLayout = createFlowLayout(goodsDetailBean);
            ll_specs.addView(flowLayout);
            String defaultGoodsId = goodsDetailBean.getGoods_group().split(";")[0].split(",")[0];
            loadGoodsDetail(defaultGoodsId);
            selectedGoodsId = defaultGoodsId;
        }
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!selectedGoodsId.isEmpty()){
                    User user = BaseApplication.getInstance().getUser();
                    if (user != null && user.getUserID() > 0) {
                        mModel.setIntoCartData(user.getUserName(), user.getPassword(), Integer.valueOf(selectedGoodsId), Integer.valueOf(0), 1);
                        mModel.setIntoCart(new RequestImpl() {
                            @Override
                            public void loadSuccess(Object object) {
                                ResultBean resultBean = (ResultBean) object;
                                if (resultBean != null) {
                                    if (resultBean.getError() == 0) {
                                        final NormalDialog dialog = new NormalDialog(v.getContext());
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
                                                        ((Activity)v.getContext()).finish();
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
                        LoginActivity.start(v.getContext());
                    }
                }else {
                    ToastUtil.showToast("请选择一个商品");
                }
            }
        });
    }

    private FlowLayout createFlowLayout(final GoodsDetailBean bean) {
        int dis = DensityUtil.dip2px(10);
        final FlowLayout flowLayout = new FlowLayout(getContext());
        for (int i = 0; i < bean.getGoods_group().split(";").length; i++) {

            final TextView textView = new TextView(getContext());
            textView.setTextColor(Color.parseColor("#888888"));
            textView.setWidth(BaseTools.getScreenWidth(getContext())/3 - DensityUtil.dip2px(20));
            textView.setGravity(Gravity.CENTER);
            textView.setHeight(DensityUtil.dip2px(40));
            textView.setPadding(DensityUtil.dip2px(10),DensityUtil.dip2px(10),DensityUtil.dip2px(10),DensityUtil.dip2px(10));
            textView.setBackgroundResource(R.drawable.selector_goods_spec);
            textView.setText(bean.getGoods_group().split(";")[i].split(",")[1]);
            textView.setTag(bean.getGoods_group().split(";")[i].split(",")[0]);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectGoodsId = bean.getGoods_group().split(";")[finalI].split(",")[0];
                    selectedGoodsId = selectGoodsId;
                    loadGoodsDetail(selectGoodsId);

                    for (int j = 0; j < flowLayout.getChildCount(); j++){
                        if (flowLayout.getChildAt(j).isSelected()) {
                            ((TextView)flowLayout.getChildAt(j)).setTextColor(Color.parseColor("#888888"));
                            flowLayout.getChildAt(j).setSelected(false);
                        }
                    }
                    if (!textView.isSelected()) {
                        textView.setSelected(true);
                        textView.setTextColor(Color.parseColor("#fd625b"));
                    }
                }
            });
            ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            marginLayoutParams.setMargins(0, dis, dis, dis);
            textView.setLayoutParams(marginLayoutParams);
            flowLayout.addView(textView);
            if(i==0){
                textView.setSelected(true);
                textView.setTextColor(Color.parseColor("#fd625b"));
            }
        }
        return flowLayout;
    }

    public void closeDialog() {
        View v = getView().findViewById(R.id.ll_all);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY",
                0, v.getHeight()).setDuration(150);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator arg0) {
            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                dismiss();
            }
        });
        animator.start();
    }
}
