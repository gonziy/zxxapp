package com.zxxapp.mall.maintenance.ui.shopping;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ShoppingCartAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.databinding.ActivityShoppingCartBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.http.cache.ACache;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class ShoppingCartFragment extends BaseFragment<ActivityShoppingCartBinding> implements ShoppingCartAdapter.ModifyCountInterface {

    private static final String TAG = "ShoppingCartActivity";
    private ShoppingCartModel mModel;
    private ShoppingCartAdapter adapter;
    private List<CartResult.DataBean> goodsList = new ArrayList<>();
    private ACache mACache;
    private CartResult cartResult;
    private boolean mSelect;
    public double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private MainActivity activity;
    private GridLayoutManager mLayoutManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading();
//        mACache = ACache.get(getContext());
        mModel = new ShoppingCartModel();
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        bindingView.xrvCart.setLayoutManager(mLayoutManager);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (!AccountHelper.isLogin())
        {
            bindingView.rlBottom.setVisibility(View.GONE);
            showContentView();
            showNeedLogin();

        }else {
            showLoading();

            initRecyclerView();
            loadCartListData();
            showContentView();
        }
    }

    private void initRecyclerView() {
        // 禁止下拉刷新
        bindingView.xrvCart.setPullRefreshEnabled(true);
        // 去掉刷新头
        //bindingView.xrvShop.clearHeader();
        adapter = new ShoppingCartAdapter();
        adapter.setModifyCountInterface(this);
        bindingView.xrvCart.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadCartListData();
                //statistics();
            }

            @Override
            public void onLoadMore() {

            }
        });
        bindingView.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalPrice>0) {
                    OrderActivity.start(v.getContext());
                }else {
                    ToastUtil.showToast("请您购物后再付款");
                }
            }
        });
    }

    /**
     * 设置adapter
     */
    private void setAdapter(CartResult cartResult) {
        adapter.clear();
        adapter.addAll(cartResult.getData());
        bindingView.xrvCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        bindingView.xrvCart.setAdapter(adapter);

        bindingView.xrvCart.refreshComplete();
        adapter.notifyDataSetChanged();

    }


    /**
     * 结算
     */
    public void statistics()
    {
        if (goodsList != null && goodsList.size() > 0) {
            totalPrice = 0.00;
            for (CartResult.DataBean cart:goodsList) {
                if(cart.getSelected()==1){
                    totalPrice += cart.getSell_price()* cart.getQuantity();
                }
            }
        }
        bindingView.tvTotalPrice.setText(StringUtils.doubleToString(totalPrice));
    }

    public void loadCartListData() {
//        User user = BaseApplication.getInstance().getUser();
//        if(user!=null&&user.getUserID()>0) {
//            mModel.setData(user.getUserName(), user.getPassword());
//            mModel.getGoodsData(new RequestImpl() {
//                @Override
//                public void loadSuccess(Object object) {
//
//                    cartResult = (CartResult) object;
//
//                    if (cartResult != null){
//                        if(cartResult.getData() != null && cartResult.getData().size() > 0) {
//                            goodsList = cartResult.getData();
//                            statistics();
//                            setAdapter(cartResult);
//                            bindingView.rlBottom.setVisibility(View.VISIBLE);
//                            showContentView();
//                        }else {
//                            goodsList = cartResult.getData();
//                            statistics();
//                            setAdapter(cartResult);
//                            showEmpty();
//                        }
//                    }
//                }
//
//
//                @Override
//                public void loadFailed() {
//                    bindingView.xrvCart.refreshComplete();
//                    if (adapter.getItemCount() == 0) {
//                        showEmpty();
//                    }
//                }
//
//                @Override
//                public void addSubscription(Subscription subscription) {
//                    ShoppingCartFragment.this.addSubscription(subscription);
//                }
//            });
//        }else {
//            LoginActivity.start(bindingView.xrvCart.getContext());
//        }
    }

    @Override
    public int setContent() {
        return R.layout.activity_shopping_cart;
    }


    @Override
    public void setQuantity(int position, final View showCountView, boolean isChecked, int article_id, int goods_id, final int quantity) {
        User user = BaseApplication.getInstance().getUser();
        if(user!=null&& user.getUserID()>0) {
            mModel.setQuantityData(user.getUserName(), user.getPassword(), article_id, goods_id, quantity);
            mModel.setQuantity(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    ResultBean resultBean = (ResultBean) object;
                    if (resultBean != null) {
                        if (resultBean.getError() == 0) {
                            if (quantity > 0) {
                                if (quantity + 1 <= 9999) {
                                    ((EditText) showCountView).setText(String.valueOf(quantity));
                                } else {
                                    ToastUtil.showToast("数量太多了");
                                }
                            } else {
                                if (quantity - 1 > 0) {
                                    ((EditText) showCountView).setText(String.valueOf(quantity));
                                } else {
                                    ToastUtil.showToast("数量太少了");
                                }
                            }
                        } else {
                            ToastUtil.showToast(resultBean.getMsg());
                        }
                        adapter.notifyDataSetChanged();
                        loadCartListData();
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
        }else {
            ToastUtil.showToast("请您登录后再试");
        }
    }

    @Override
    public void childDelete(final int position, int article_id, int goods_id) {
        User user = BaseApplication.getInstance().getUser();
        if(user!=null&& user.getUserID()>0) {
            mModel.setDeleteData(user.getUserName(), user.getPassword(), article_id, goods_id);
            mModel.setDelete(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    ResultBean resultBean = (ResultBean) object;
                    if (resultBean != null) {
                        if (resultBean.getError() == 0) {
                            goodsList.remove(position);
                            loadCartListData();
//                        adapter.notifyDataSetChanged();
//                        statistics();
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
        }else {
            ToastUtil.showToast("请您登录后再试");
        }
    }


    @Override
    public void doSelect(int position, final View showCheckedView, final boolean isChecked, int article_id, int goods_id, int selected) {
        User user = BaseApplication.getInstance().getUser();
        if(user!=null&& user.getUserID()>0) {
            mModel.setSelectedData(user.getUserName(), user.getPassword(), article_id, goods_id, selected);
            mModel.setSelected(new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    ResultBean resultBean = (ResultBean) object;
                    if (resultBean != null) {
                        if (resultBean.getError() == 0) {
                            ((CheckBox) showCheckedView).setChecked(isChecked);
                        } else {
                            ToastUtil.showToast(resultBean.getMsg());
                        }
                        adapter.notifyDataSetChanged();
                        loadCartListData();
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
        }else {
            ToastUtil.showToast("请您登录后再试");
        }
    }

}
