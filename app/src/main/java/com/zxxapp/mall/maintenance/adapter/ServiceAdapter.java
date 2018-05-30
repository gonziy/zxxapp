package com.zxxapp.mall.maintenance.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.ShopDataBean;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.goods.ServiceListBean;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsOneBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTitleBinding;
import com.zxxapp.mall.maintenance.databinding.ItemGoodsTwoBinding;
import com.zxxapp.mall.maintenance.databinding.ItemServiceTwoBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.DensityUtil;
import com.zxxapp.mall.maintenance.utils.ImgHelper;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.SharedPreferencesHelper;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscription;

/**
 * Created by jingbin on 2016/12/15.
 */

public class ServiceAdapter extends BaseRecyclerViewAdapter<ServiceListBean.DataBean.ListBean> {

    private MainActivity context;

    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图

    int thisNum = 0;


    public ServiceAdapter(Context context) {
        this.context = (MainActivity) context;

    }

    @Override
    public int getItemViewType(int position) {
//        //如果是is_slide,则图片为标题广告
//       if(getData().get(position).getIs_slide().equals("1")){
//           return TYPE_TITLE;
//       }else
//       {
//           //is_top 大图 其余小图
//           if(getData().get(position).getIs_top().equals("1")){
//               return TYPE_ONE;
//           }else if(getData().get(position).getIs_top().equals("0")){
//               return TYPE_TWO;
//           }
//       }
//        return super.getItemViewType(position);
       return TYPE_TWO;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case TYPE_TITLE:
//                return new TitleHolder(parent, R.layout.item_goods_title);
//            case TYPE_ONE:
//                return new OneHolder(parent, R.layout.item_goods_one);
//            case TYPE_TWO:
//                return new TwoHolder(parent, R.layout.item_service_two);
//            default:
//                return new TwoHolder(parent, R.layout.item_goods_two);
//        }

        return new TwoHolder(parent, R.layout.item_service_two);
    }

    private class TwoHolder extends BaseRecyclerViewHolder<ServiceListBean.DataBean.ListBean, ItemServiceTwoBinding> {

        TwoHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final ServiceListBean.DataBean.ListBean goods, final int position) {
            ViewGroup.LayoutParams llAllPara =binding.llAll.getLayoutParams();
            int imgWidth = BaseTools.getScreenWidth(context) / 2;
            binding.setResultsBean(goods);
            binding.executePendingBindings();
            binding.llAll.setLayoutParams(llAllPara);
            int imgHeight = imgWidth * 3 / 4 ;
            if (goods.getPicture() != null
                    && !TextUtils.isEmpty(goods.getPicture())) {

                binding.ivServicePic.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams para;
                para = binding.ivServicePic.getLayoutParams();
                binding.ivServicePic.setLayoutParams(para);
                para.width = imgWidth;
                para.height = imgHeight;
                binding.ivServicePic.setBackgroundColor(Color.parseColor("#ffffff"));
                ImgLoadUtil.showImg( binding.ivServicePic,goods.getPicture());
            } else {
                binding.ivServicePic.setVisibility(View.GONE);
            }
            binding.ivServicePic.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    //BookDetailActivity.start(context,book,mBindBook.ivTopPhoto);
                    //GoodsDetailActivity.start((MainActivity)v.getContext(),goods.getId());
                }
            });
        }
    }

    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }

    public int getLoadStatus(){
        return this.status;
    }


}
