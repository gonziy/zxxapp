package com.zxxapp.mall.maintenance.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewAdapter;
import com.zxxapp.mall.maintenance.base.baseadapter.BaseRecyclerViewHolder;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.databinding.ItemCartBinding;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.utils.ImgLoadUtil;

/**
 * Created by jingbin on 2016/12/2.
 */

public class ShoppingCartAdapter extends BaseRecyclerViewAdapter<CartResult.DataBean> {

    private MainActivity context;
    private ShoppingCartModel mModel = new ShoppingCartModel();
    private ModifyCountInterface modifyCountInterface;

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.item_cart);
    }

    private class ViewHolder extends BaseRecyclerViewHolder<CartResult.DataBean, ItemCartBinding> {

        ViewHolder(ViewGroup parent, int item_goods) {
            super(parent, item_goods);
        }

        @Override
        public void onBindViewHolder(final CartResult.DataBean dataBean, final int position) {
            binding.setResultsBean(dataBean);
            binding.executePendingBindings();

            binding.etQuantity.setFocusable(false);


            // 显示gif图片会很耗内存
            if (dataBean.getImg_url() != null
                    && !TextUtils.isEmpty(dataBean.getImg_url())) {
                binding.ivThumb.setVisibility(View.VISIBLE);
                binding.ivThumb.setImageURI(Uri.parse(dataBean.getImg_url()));
                ImgLoadUtil.displayGif(dataBean.getImg_url(), binding.ivThumb);
            } else {
                binding.ivThumb.setVisibility(View.GONE);
            }

            // 点击缩略图 进入详情页
            binding.ivThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    WebViewActivity.loadUrl(v.getContext(), object.getUrl(), "加载中...");
                    GoodsDetailActivity.start((MainActivity)v.getContext(),String.valueOf(dataBean.getArticle_id()));
                }
            });
            //增加数量操作
            binding.tvQuantityAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer quantity = Integer.valueOf(binding.etQuantity.getText().toString());
                    if(quantity+1<=9999) {
                        //SetQuantity(dataBean.getArticle_id(),dataBean.getGoods_id(),quantity +1);
                        modifyCountInterface.setQuantity(position,binding.etQuantity,false,dataBean.getArticle_id(),dataBean.getGoods_id(),quantity +1);
                    }
                }
            });
            //减少数量操作
            binding.tvQuantitySub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer quantity = Integer.valueOf(binding.etQuantity.getText().toString());
                    if(quantity-1>0) {
//                        SetQuantity(dataBean.getArticle_id(),dataBean.getGoods_id(),quantity-1);
                        modifyCountInterface.setQuantity(position,binding.etQuantity,false,dataBean.getArticle_id(),dataBean.getGoods_id(),quantity - 1);
                    }
                }
            });
            //选择操作
            binding.ckChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer selected = binding.ckChoose.isChecked()?1:0;
//                    setSelected(dataBean.getArticle_id(),dataBean.getGoods_id(),selected);
                    modifyCountInterface.doSelect(position,binding.ckChoose,binding.ckChoose.isChecked(),dataBean.getArticle_id(),dataBean.getGoods_id(),selected);
                }
            });
            //删除操作
            binding.tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final NormalDialog dialog = new NormalDialog(v.getContext());
                    dialog.content("您真的要狠心删除吗?").style(NormalDialog.STYLE_TWO)//
                            .titleTextSize(23)
                            .btnText("狠心删除", "留下吧")
                            .titleTextColor(Color.parseColor("#fffd625b"))
                            .cornerRadius(10)
                            .titleLineHeight(0)
                            .show();
                    dialog.setOnBtnClickL(
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    modifyCountInterface.childDelete(position,dataBean.getArticle_id(),dataBean.getGoods_id());
                                    dialog.dismiss();
                                }
                            },
                            new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    dialog.dismiss();
                                }
                            }

                    );

                    //setDelete(dataBean.getArticle_id(),dataBean.getGoods_id());
                }
            });

        }

    }
    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void setQuantity(int position, View showCountView, boolean isChecked, int article_id, int goods_id,int quantity);
        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position, int article_id, int goods_id);

        /**
         * 选择操作
         * @param position
         * @param showCheckedView
         * @param isChecked
         */
        void doSelect(int position, View showCheckedView, boolean isChecked, int article_id, int goods_id,int selected);
    }

}
