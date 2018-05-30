package com.zxxapp.mall.maintenance.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.databinding.ItemArticleFourBinding;
import com.zxxapp.mall.maintenance.databinding.ItemArticleOneBinding;
import com.zxxapp.mall.maintenance.databinding.ItemArticleThreeBinding;
import com.zxxapp.mall.maintenance.databinding.ItemArticleTwoBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsOneBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTitleBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTwoBinding;
import com.zxxapp.mall.maintenance.databinding.ItemImgOneBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import java.util.Date;

import rx.Subscription;

/**
 * Created by jingbin on 2016/12/15.
 */

public class IndexAdapter extends BaseRecyclerViewAdapter<ArticleBean.ResultsBean> {

    private MainActivity context;

    private static final int TYPE_IMG_ONE = 1; // 图1
    private static final int TYPE_IMG_TWO = 2;// 图2
    private static final int TYPE_IMG_THREE = 3;// 图3
    private static final int TYPE_IMG_FOUR = 4;// 图4

    private static final int TYPE_GOODS_ONE = 5; // 商品1
    private static final int TYPE_GOODS_TWO = 6;// 商品2
    private static final int TYPE_GOODS_THREE = 7;// 商品3
    private static final int TYPE_GOODS_FOUR = 8;// 商品4

    private static final int TYPE_ARTICLE_ONE = 9; // 文章1
    private static final int TYPE_ARTICLE_TWO = 10;// 文章2
    private static final int TYPE_ARTICLE_THREE = 11;// 文章3
    private static final int TYPE_ARTICLE_FOUR = 12;// 文章4

    int thisNum = 0;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;

    public IndexAdapter(Context context) {
        this.context = (MainActivity) context;

    }
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    public int getLoadStatus(){
        return this.status;
    }

    @Override
    public int getItemViewType(int position) {
        //如果是is_slide,则图片为标题广告
        if (getData().get(position).getApp_view().equals("one_img")) {
            return TYPE_IMG_ONE;
        } else if (getData().get(position).getApp_view().equals("two_img")) {
            return TYPE_IMG_TWO;
        } else if (getData().get(position).getApp_view().equals("three_img")) {
            return TYPE_IMG_THREE;
        } else if (getData().get(position).getApp_view().equals("four_img")) {
            return TYPE_IMG_FOUR;
        } else if (getData().get(position).getApp_view().equals("one_goods")) {
            return TYPE_GOODS_ONE;
        } else if (getData().get(position).getApp_view().equals("two_goods")) {
            return TYPE_GOODS_TWO;
        } else if (getData().get(position).getApp_view().equals("three_goods")) {
            return TYPE_GOODS_THREE;
        } else if (getData().get(position).getApp_view().equals("four_goods")) {
            return TYPE_GOODS_FOUR;
        } else if (getData().get(position).getApp_view().equals("one_article")) {
            return TYPE_ARTICLE_ONE;
        } else if (getData().get(position).getApp_view().equals("two_article")) {
            return TYPE_ARTICLE_TWO;
        } else if (getData().get(position).getApp_view().equals("three_article")) {
            return TYPE_ARTICLE_THREE;
        } else if (getData().get(position).getApp_view().equals("four_article")) {
            return TYPE_ARTICLE_FOUR;
        }


        return super.getItemViewType(position);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_IMG_ONE:
                return new OneImgHolder(parent, R.layout.item_img_one);
            case TYPE_IMG_TWO:
                return new TwoImgHolder(parent, R.layout.item_img_one);
            case TYPE_IMG_THREE:
                return new ThreeImgHolder(parent, R.layout.item_img_one);
            case TYPE_IMG_FOUR:
                return new FourImgHolder(parent, R.layout.item_img_one);

//            case TYPE_GOODS_ONE:
//                return new OneGoodsHolder(parent, R.layout.item_goods_list_one);
//            case TYPE_GOODS_TWO:
//                return new TwoGoodsHolder(parent, R.layout.item_goods_list_one);
//            case TYPE_GOODS_THREE:
//                return new ThreeGoodsHolder(parent, R.layout.item_goods_list_one);
//            case TYPE_GOODS_FOUR:
//                return new FourGoodsHolder(parent, R.layout.item_goods_list_one);

            case TYPE_ARTICLE_ONE:
                return new OneArticleHolder(parent, R.layout.item_article_one);
            case TYPE_ARTICLE_TWO:
                return new TwoArticleHolder(parent, R.layout.item_article_two);
            case TYPE_ARTICLE_THREE:
                return new ThreeArticleHolder(parent, R.layout.item_article_three);
            case TYPE_ARTICLE_FOUR:
                return new FourArticleHolder(parent, R.layout.item_article_four);
            default:
                return new OneImgHolder(parent, R.layout.item_img_one);
        }
    }

    private ImageView.ScaleType toScaleType(String scaletype){
        if(scaletype.equals("CENTER")){
            return ImageView.ScaleType.CENTER;
        }else if(scaletype.equals("CENTER_CROP")) {
            return ImageView.ScaleType.CENTER_CROP;
        }else if(scaletype.equals("CENTER_INSIDE")) {
            return ImageView.ScaleType.CENTER_INSIDE;
        }else if(scaletype.equals("FIT_CENTER")) {
            return ImageView.ScaleType.FIT_CENTER;
        }else if(scaletype.equals("FIT_END")) {
            return ImageView.ScaleType.FIT_END;
        }else if(scaletype.equals("FIT_START")) {
            return ImageView.ScaleType.FIT_START;
        }else if(scaletype.equals("FIT_XY")) {
            return ImageView.ScaleType.FIT_XY;
        }else {
            return ImageView.ScaleType.FIT_XY;
        }
    }

    private class OneImgHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemImgOneBinding> {

        OneImgHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();
            if(position==0 && object.getShow_view().equals("inviter")){

                if(!object.getText_color().isEmpty()) {
                    if (!SharedPreferencesHelper.getInstance().getData("inviter_id", "").toString().isEmpty()) {
                        Long inviteTime = (Long) SharedPreferencesHelper.getInstance().getData("inviter_time", 0L);
                        long interval = new Date().getTime() - inviteTime;
                        if (interval <= 7200000) {
                            String tmpInviter = SharedPreferencesHelper.getInstance().getData("inviter_id", "").toString();
                            binding.tvText.setText("邀请人ID:" + tmpInviter);
                            binding.tvText.setTextColor(Color.parseColor("#" + object.getText_color()));
                        }
                    }
                }
            }else {
                binding.tvText.setText("");
            }
            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context);
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }

                if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                    binding.llAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                                WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                            }else {
                                if(object.getUrl().equals("article")){
                                    WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                                }else {
                                    GoodsDetailActivity.start(context, object.getUrl());
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private class TwoImgHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemImgOneBinding> {

        TwoImgHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();

            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 2;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }

                if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                    binding.llAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                                WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                            }else {
                                if(object.getUrl().equals("article")){
                                    WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                                }else {
                                    GoodsDetailActivity.start(context, object.getUrl());
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private class ThreeImgHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemImgOneBinding> {

        ThreeImgHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();

            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 3;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }

                if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                    binding.llAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                                WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                            }else {
                                if(object.getUrl().equals("article")){
                                    WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                                }else {
                                    GoodsDetailActivity.start(context, object.getUrl());
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private class FourImgHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemImgOneBinding> {

        FourImgHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();

            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 4;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }

                if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                    binding.llAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                                WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                            }else {
                                if(object.getUrl().equals("article")){
                                    WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                                }else {
                                    GoodsDetailActivity.start(context, object.getUrl());
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private class OneArticleHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemArticleOneBinding> {

        OneArticleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();
            if(object.getZhaiyao().isEmpty()){
                binding.tvIntro.setVisibility(View.GONE);
            }else {
                binding.tvIntro.setVisibility(View.VISIBLE);
            }
            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context);
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }
            }
            if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                binding.llAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                            WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                        }else {
                            if(object.getUrl().equals("article")){
                                WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                            }else {
                                GoodsDetailActivity.start(context, object.getUrl());
                            }
                        }
                    }
                });
            }
        }
    }

    private class TwoArticleHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemArticleTwoBinding> {

        TwoArticleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();
            if(object.getZhaiyao().isEmpty()){
                binding.tvIntro.setVisibility(View.GONE);
            }else {
                binding.tvIntro.setVisibility(View.VISIBLE);
            }
            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 2;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }
            }
            if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                binding.llAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                            WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                        }else {
                            if(object.getUrl().equals("article")){
                                WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                            }else {
                                GoodsDetailActivity.start(context, object.getUrl());
                            }
                        }
                    }
                });
            }
        }
    }

    private class ThreeArticleHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemArticleThreeBinding> {

        ThreeArticleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();

            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 3;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }
            }
            if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                binding.llAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                            WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                        }else {
                            if(object.getUrl().equals("article")){
                                WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                            }else {
                                GoodsDetailActivity.start(context, object.getUrl());
                            }
                        }
                    }
                });
            }
        }
    }

    private class FourArticleHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemArticleFourBinding> {

        FourArticleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean object, final int position) {
            binding.setResultsBean(object);
            binding.executePendingBindings();

            if (object.getFront_img() != null
                    && !TextUtils.isEmpty(object.getFront_img())) {
                int imgWidth = BaseTools.getScreenWidth(context) / 4;
                binding.ivImg.setBackgroundColor(Color.parseColor("#ffffff"));
                ViewGroup.LayoutParams para;
                para = binding.ivImg.getLayoutParams();
                para.width = imgWidth;
                para.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                binding.ivImg.setLayoutParams(para);
                binding.ivImg.setScaleType(toScaleType(object.getFront_img_scaletype()));
                ImgLoadUtil.showImg(binding.ivImg, object.getFront_img());
                if (object.getBg_img() != null
                        && !TextUtils.isEmpty(object.getBg_img())){
                    ImgLoadUtil.showBg(binding.llAll,object.getBg_img());
                }
            }
            if(object.getUrl()!=null && !TextUtils.isEmpty(object.getUrl())){
                binding.llAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(object.getUrl().startsWith("http://") ||object.getUrl().startsWith("https://")){
                            WebViewActivity.loadUrl(context,object.getUrl(),object.getTitle());
                        }else {
                            if(object.getUrl().equals("article")){
                                WebViewActivity.loadUrl(context, HttpUtils.API_HOST + "news/show-"+object.getId()+".aspx",object.getTitle());
                            }else {
                                GoodsDetailActivity.start(context, object.getUrl());
                            }
                        }
                    }
                });
            }
        }
    }


}
