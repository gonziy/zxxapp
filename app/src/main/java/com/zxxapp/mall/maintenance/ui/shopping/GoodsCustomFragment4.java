package com.zxxapp.mall.maintenance.ui.shopping;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ServiceAdapter;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.goods.ServiceListBean;
import com.zxxapp.mall.maintenance.databinding.FragmentGoodsCustomBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.ServiceModel;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class GoodsCustomFragment4 extends BaseFragment<FragmentGoodsCustomBinding>{

    private static final String TYPE = "param1";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    // 开始请求的角标
    private int mPage = 1;
    // 一次请求的数量
    private int mCount = 8;
    public int category_id = 4;
    private MainActivity activity;
    private ServiceAdapter mGoodsAdapter;
    private GridLayoutManager mLayoutManager;
    private List<ServiceListBean.DataBean.ListBean> list;

    @Override
    public int setContent() {
        return R.layout.fragment_goods_custom;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    public static GoodsCustomFragment4 newInstance() {
        GoodsCustomFragment4 fragment = new GoodsCustomFragment4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list= new ArrayList<ServiceListBean.DataBean.ListBean>();
        showContentView();
        bindingView.srlGoods.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlGoods.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DebugUtil.error("-----onRefresh");
                bindingView.srlGoods.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mPage = 1;
                        loadCustomData();
                    }
                }, 1000);

            }
        });

        mLayoutManager = new GridLayoutManager(getActivity(), 3);


        bindingView.xrvGoods.setLayoutManager(mLayoutManager);

//        bindingView.xrvGoods.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
//        bindingView.xrvGoods.setNestedScrollingEnabled(false);
//        bindingView.xrvGoods.setHasFixedSize(false);
//        bindingView.xrvGoods.setItemAnimator(new DefaultItemAnimator());

        scrollRecycleView();

        // 准备就绪
        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }
    private void resizeLayout(){
//        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (list.get(position).getIs_slide().equals("1") || list.get(position).getIs_top().equals("1"))
//                    return 2;
//                else
//                    return 1;
//            }
//
//        });
        bindingView.xrvGoods.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlGoods.setRefreshing(true);
        bindingView.srlGoods.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCustomData();
            }
        }, 500);
        DebugUtil.error("-----setRefreshing");
    }


    private void loadGoodsData(){
    }


    private void loadCustomData() {
        ServiceModel model = new ServiceModel();
        model.setData(String.valueOf(category_id),"w");
        model.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                final ServiceListBean serviceListBean = (ServiceListBean)object;
                showContentView();
                if (mPage == 1) {

                    if (serviceListBean != null && serviceListBean.getData() != null && serviceListBean.getData().getList().size() > 0) {

                        if (mGoodsAdapter == null) {
                            mGoodsAdapter = new ServiceAdapter(activity);
                        }else {
                            mGoodsAdapter.clear();}
                        if(list == null){
                            list = new ArrayList<ServiceListBean.DataBean.ListBean>();
                        }else {
                            list.clear();
                        }
                        list.addAll(serviceListBean.getData().getList());
                        resizeLayout();
                        mGoodsAdapter.addAll(serviceListBean.getData().getList());
                        bindingView.xrvGoods.setAdapter(mGoodsAdapter);
                    }
                    mIsFirst = false;
                } else {
                    list.addAll(serviceListBean.getData().getList());
                    mGoodsAdapter.addAll(serviceListBean.getData().getList());
                }
            }

            @Override
            public void loadFailed() {
                showContentView();
                if (bindingView.srlGoods.isRefreshing()) {
                    bindingView.srlGoods.setRefreshing(false);
                }
                if (mPage == 1) {
                    showError();
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                showContentView();
                if (bindingView.srlGoods.isRefreshing()) {
                    bindingView.srlGoods.setRefreshing(false);
                }
            }
        });
    }


    public void scrollRecycleView() {
        bindingView.xrvGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                    if (mGoodsAdapter == null) {
                        return;
                    }
                    if (mLayoutManager.getItemCount() == 0) {
                        mGoodsAdapter.updateLoadStatus(ServiceAdapter.LOAD_NONE);
                        return;

                    }
                    if (mPage * mCount == mLayoutManager.getItemCount()
                            && mGoodsAdapter.getLoadStatus() == ServiceAdapter.LOAD_MORE) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPage++;
                                loadCustomData();
                            }
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    @Override
    protected void onRefresh() {
        bindingView.srlGoods.setRefreshing(true);
        loadCustomData();
    }
}
