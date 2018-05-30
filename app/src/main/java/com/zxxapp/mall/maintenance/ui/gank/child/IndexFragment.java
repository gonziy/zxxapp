package com.zxxapp.mall.maintenance.ui.gank.child;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.IndexAdapter;
import com.zxxapp.mall.maintenance.adapter.ServiceAdapter;
import com.zxxapp.mall.maintenance.app.Constants;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.databinding.FragmentIndexBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.http.cache.ACache;
import com.zxxapp.mall.maintenance.model.ArticleModel;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.utils.DebugUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

public class IndexFragment extends BaseFragment<FragmentIndexBinding>  {

    private static final String TYPE = "param1";
    private boolean mIsPrepared;
    private boolean mIsFirst = true;
    // 开始请求的角标
    private int mPage = 1;
    // 一次请求的数量
    private int mCount = 16;
    private int totalCount = 0;
    private MainActivity activity;
    private IndexAdapter indexAdapter;
    private GridLayoutManager mLayoutManager;
    private List<ArticleBean.ResultsBean> list;
    private ACache maCache;
    private ArrayList<String> mBannerImages;


    @Override
    public int setContent() {
        return R.layout.fragment_index;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        maCache = ACache.get(getContext());
        list= new ArrayList<ArticleBean.ResultsBean>();
        showContentView();
        initRecyclerView();
        if(maCache.getAsObject(Constants.BANNER_PIC)!=null) {
            mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);
        }


        bindingView.srlIndex.setColorSchemeColors(CommonUtils.getColor(R.color.colorTheme));
        bindingView.srlIndex.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DebugUtil.error("-----onRefresh");
                bindingView.srlIndex.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mPage = 1;
                        loadCustomData();
                    }
                }, 1000);

            }
        });


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

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getActivity(), 12);
        bindingView.xrvIndex.setLayoutManager(mLayoutManager);



        // 需加，不然滑动不流畅
        bindingView.xrvIndex.setNestedScrollingEnabled(false);
        bindingView.xrvIndex.setHasFixedSize(false);
        bindingView.xrvIndex.setItemAnimator(new DefaultItemAnimator());
    }
    /**
     * 取缓存
     */
    private void getACacheData() {
        if (!mIsFirst) {
            return;
        }
        if (mBannerImages != null && mBannerImages.size() > 0) {
//            bindingView.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
        } else {
            loadBannerPicture();
        }
    }

    private void loadBannerPicture() {
        ArticleModel model = new ArticleModel();
        model.getBannerData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                if (mBannerImages == null) {
                    mBannerImages = new ArrayList<String>();
                } else {
                    mBannerImages.clear();
                }
                final ArticleBean bean = (ArticleBean) object;
                if (bean != null && bean.getResults() != null && bean.getResults().size()>0) {

                    for (ArticleBean.ResultsBean article:bean.getResults()) {
                        mBannerImages.add(article.getImg_url());
                    }

//                    bindingView.banner.setImages(mBannerImages).setImageLoader(new GlideImageLoader()).start();
//                    bindingView.banner.setOnBannerClickListener(new OnBannerClickListener() {
//                        @Override
//                        public void OnBannerClick(int position) {
//                            position = position - 1;
//                            // 链接没有做缓存，如果轮播图使用的缓存则点击图片无效
//                            if (bean.getResults().get(position) != null && bean.getResults().get(position).getImg_url() != null){
//                                if(bean.getResults().get(position).getUrl().startsWith("http://") || bean.getResults().get(position).getUrl().startsWith("https://")){
//                                    WebViewActivity.loadUrl(getContext(), bean.getResults().get(position).getUrl(), bean.getResults().get(position).getTitle());
//                                }else {
//                                    GoodsDetailActivity.start(activity,bean.getResults().get(position).getUrl());
//                                }
//                            }
//                        }
//                    });
                    maCache.remove(Constants.BANNER_PIC);
                    maCache.put(Constants.BANNER_PIC, mBannerImages, 30000);

                }
            }

            @Override
            public void loadFailed() {

            }

            @Override
            public void addSubscription(Subscription subscription) {
                IndexFragment.this.addSubscription(subscription);
            }
        });
    }

    private void resizeLayout(){
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (list.get(position).getApp_view().equals("one_img")
                        || list.get(position).getApp_view().equals("one_goods")
                        || list.get(position).getApp_view().equals("one_article")
                        ){
                    return 12;
                }
                else if (list.get(position).getApp_view().equals("two_img")
                        || list.get(position).getApp_view().equals("two_goods")
                        || list.get(position).getApp_view().equals("two_article")
                        ) {
                    return 6;
                }
                else if (list.get(position).getApp_view().equals("three_img")
                        || list.get(position).getApp_view().equals("three_goods")
                        || list.get(position).getApp_view().equals("three_article")
                        ) {
                    return 4;
                }
                else if (list.get(position).getApp_view().equals("four_img")
                        || list.get(position).getApp_view().equals("four_goods")
                        || list.get(position).getApp_view().equals("four_article")
                        ) {
                    return 3;
                }
                else
                    return 12;
            }

        });
        bindingView.xrvIndex.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void loadData() {
        DebugUtil.error("-----loadData");
//        // 显示时轮播图滚动
//        if (bindingView != null && bindingView.banner != null) {
//            bindingView.banner.startAutoPlay();
//            bindingView.banner.setDelayTime(4000);
//        }

        if (!mIsPrepared || !mIsVisible || !mIsFirst) {
            return;
        }

        bindingView.srlIndex.setRefreshing(true);
        bindingView.srlIndex.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCustomData();
            }
        }, 500);
        getACacheData();
        DebugUtil.error("-----setRefreshing");
    }

    private void loadCustomData() {
        ArticleModel model = new ArticleModel();
        model.setData(mCount,mPage);
        model.getIndexData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                final ArticleBean articleBean = (ArticleBean)object;
                showContentView();
                if (mPage == 1) {

                    if (articleBean != null && articleBean.getResults() != null && articleBean.getResults().size() > 0) {
                        totalCount = articleBean.getCount();
                        if (indexAdapter == null) {
                            indexAdapter = new IndexAdapter(activity);
                        }else {
                            indexAdapter.clear();}
                        if(list == null){
                            list = new ArrayList<ArticleBean.ResultsBean>();
                        }else {
                            list.clear();
                        }
                        list.addAll(articleBean.getResults());
                        resizeLayout();
                        indexAdapter.addAll(articleBean.getResults());
                        bindingView.xrvIndex.setAdapter(indexAdapter);
                    }
                    mIsFirst = false;
                } else {
                    list.addAll(articleBean.getResults());
                    indexAdapter.addAll(articleBean.getResults());
                }
                if(mPage * mCount < articleBean.getCount())
                    indexAdapter.updateLoadStatus(IndexAdapter.LOAD_MORE);
                else
                    indexAdapter.updateLoadStatus(IndexAdapter.LOAD_NONE);
            }

            @Override
            public void loadFailed() {
                showContentView();
                totalCount = 0;
                if (bindingView.srlIndex.isRefreshing()) {
                    bindingView.srlIndex.setRefreshing(false);
                }
                if (mPage == 1) {
                    showError();
                }
            }

            @Override
            public void addSubscription(Subscription subscription) {
                showContentView();
                if (bindingView.srlIndex.isRefreshing()) {
                    bindingView.srlIndex.setRefreshing(false);
                }
            }
        });
    }
    public void scrollRecycleView() {
        bindingView.xrvIndex.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

                    if (indexAdapter == null) {
                        return;
                    }
                    if (mLayoutManager.getItemCount() == 0) {
                        indexAdapter.updateLoadStatus(IndexAdapter.LOAD_NONE);
                        return;

                    }
                    if (mPage * mCount == mLayoutManager.getItemCount()
                            && indexAdapter.getLoadStatus() == ServiceAdapter.LOAD_MORE) {

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

    @Override
    protected void onRefresh() {
        bindingView.srlIndex.setRefreshing(true);
        loadCustomData();
    }

}
