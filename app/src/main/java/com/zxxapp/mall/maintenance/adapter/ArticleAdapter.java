package com.zxxapp.mall.maintenance.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.example.http.HttpUtils;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.databinding.ItemArticleBinding;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

/**
 * Created by jingbin on 2016/12/2.
 */

public class ArticleAdapter extends BaseRecyclerViewAdapter<ArticleBean.ResultsBean> {

    private Activity activity;

    public ArticleAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_article);
    }


    private class ViewHolder extends BaseRecyclerViewHolder<ArticleBean.ResultsBean, ItemArticleBinding> {

        ViewHolder(ViewGroup parent, int item_article) {
            super(parent, item_article);
        }

        @Override
        public void onBindViewHolder(final ArticleBean.ResultsBean resultsBean, final int position) {


            binding.setResultsBean(resultsBean);
            binding.executePendingBindings();



            binding.ivImg.setVisibility(View.VISIBLE);

            // 显示gif图片会很耗内存
            if (resultsBean.getImg_url() != null
                    && !TextUtils.isEmpty(resultsBean.getImg_url())) {
                binding.ivImg.setVisibility(View.VISIBLE);
                ImgLoadUtil.displayGif(resultsBean.getImg_url(), binding.ivImg);
            } else {
                binding.ivImg.setVisibility(View.GONE);
            }

            binding.llAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.loadUrl(v.getContext(), HttpUtils.API_HOST +"news/show-"+ String.valueOf(resultsBean.getId())+".aspx", resultsBean.getTitle());
//                    GoodsDetailActivity.start((MainActivity)v.getContext(), String.valueOf(resultsBean.getId()));
                }
            });
        }


    }
}
