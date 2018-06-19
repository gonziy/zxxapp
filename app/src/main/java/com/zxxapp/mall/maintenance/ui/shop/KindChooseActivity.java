package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestDataArrayBean;
import com.zxxapp.mall.maintenance.bean.RequestDataListArrayBean;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.shop.CategoryBean;
import com.zxxapp.mall.maintenance.bean.shop.ServiceBean;
import com.zxxapp.mall.maintenance.databinding.ActivityKindChooseBinding;
import com.zxxapp.mall.maintenance.databinding.ItemCategoryBinding;
import com.zxxapp.mall.maintenance.databinding.ItemServicePictureBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KindChooseActivity extends AppCompatActivity {

    private ActivityKindChooseBinding binding;
    private List<CategoryBean> categoryList;
    private List<ServiceBean> serviceList;
    private String categoryType = "w";
    private Set<Integer> serviceIdSet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        categoryType = intent.getStringExtra("type");
        if("w".equalsIgnoreCase(categoryType)){
            setTitle("你会维修的项目");
        }else if("t".equalsIgnoreCase(categoryType)){
            setTitle("你会安装的项目");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_kind_choose);

        initView();
        initData();
    }

    private void initView(){
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.ic_recycle_view_default_decoration));

        categoryList = new LinkedList<>();
        binding.categoryList.setLayoutManager(new LinearLayoutManager(this));
        binding.categoryList.addItemDecoration(decoration);
        final CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = binding.categoryList.getChildAdapterPosition(view);
                for(CategoryBean categoryBean : categoryList){
                    categoryBean.setSelected(false);
                }
                categoryList.get(position).setSelected(true);

                categoryAdapter.notifyDataSetChanged();

                //加载Service的数据
                loadService(categoryList.get(position).getCategoryId(), categoryType);
            }
        });
        binding.categoryList.setAdapter(categoryAdapter);


        serviceList = new LinkedList<>();
        binding.serviceList.setLayoutManager(new GridLayoutManager(this, 4));
        final ServiceAdapter serviceAdapter = new ServiceAdapter();
        serviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = binding.serviceList.getChildAdapterPosition(view);
                final ServiceBean serviceBean = serviceList.get(position);
                serviceBean.setSelected(!serviceBean.isSelected());

                serviceAdapter.notifyDataSetChanged();

                if(serviceBean.isSelected()) {//增加服务
                    HttpClient.Builder.getZhiXiuServer().addService(AccountHelper.getShop().getShopId(), serviceBean.getServiceId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<RequestBaseBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(RequestBaseBean requestBaseBean) {
                                    if ("true".equalsIgnoreCase(requestBaseBean.getSuccess())) {
                                        serviceIdSet.add(serviceBean.getServiceId());

                                        Log.d("TEST", "成功添加商铺服务。");
                                    }else if("false".equalsIgnoreCase(requestBaseBean.getSuccess())){
                                        Log.d("TEST", "添加商铺服务失败。");
                                    }
                                }
                            });
                }else{//删除服务
                    HttpClient.Builder.getZhiXiuServer().delService(AccountHelper.getShop().getShopId(), serviceBean.getServiceId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<RequestBaseBean>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(RequestBaseBean requestBaseBean) {
                                    if ("true".equalsIgnoreCase(requestBaseBean.getSuccess())) {
                                        serviceIdSet.remove(serviceBean.getServiceId());

                                        Log.d("TEST", "成功删除商铺服务。");
                                    }else if("false".equalsIgnoreCase(requestBaseBean.getSuccess())){
                                        Log.d("TEST", "删除商铺服务失败。");
                                    }
                                }
                            });
                }
            }
        });
        binding.serviceList.setAdapter(serviceAdapter);
    }

    private void initData(){
        //获得商铺所有的服务
        HttpClient.Builder.getZhiXiuServer().getShopServiceList(AccountHelper.getShop().getShopId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestDataArrayBean<ServiceBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestDataArrayBean<ServiceBean> requestDataArrayBean) {
                        if("true".equalsIgnoreCase(requestDataArrayBean.getSuccess())){
                            for(ServiceBean bean : requestDataArrayBean.getData()){
                                serviceIdSet.add(bean.getServiceId());
                            }
                        }
                    }
                });

        HttpClient.Builder.getZhiXiuServer().getCategory(AccountHelper.getUser().token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestDataListArrayBean<CategoryBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestDataListArrayBean<CategoryBean> categoryBeanRequestDataListArrayBean) {
                        if("true".equalsIgnoreCase(categoryBeanRequestDataListArrayBean.getSuccess())){
                            for(CategoryBean categoryBean : categoryBeanRequestDataListArrayBean.getData().getList()){
                                categoryList.add(categoryBean);
                            }

                            if(categoryList.size()>0){
                                categoryList.get(0).setSelected(true);
                            }

                            binding.categoryList.getAdapter().notifyDataSetChanged();

                            loadService(categoryList.get(0).getCategoryId(), categoryType);
                        }
                    }
                });
    }

    private void loadService(int categoryId, String categoryType){
        HttpClient.Builder.getZhiXiuServer().getService(AccountHelper.getUser().token, categoryId, categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestDataListArrayBean<ServiceBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RequestDataListArrayBean<ServiceBean> serviceBeanRequestDataListArrayBean) {
                        if("true".equalsIgnoreCase(serviceBeanRequestDataListArrayBean.getSuccess())){
                            serviceList.clear();

                            for(ServiceBean serviceBean : serviceBeanRequestDataListArrayBean.getData().getList()){
                                serviceBean.setSelected(serviceIdSet.contains(serviceBean.getServiceId()));
                                serviceList.add(serviceBean);
                            }
                            binding.serviceList.getAdapter().notifyDataSetChanged();
                        }
                    }
                });
    }

    class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

        private OnItemClickListener onItemClickListener;
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final ItemCategoryBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_category, parent, false);
            binding.titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(binding.getRoot());
                    }
                }
            });
            return new CategoryViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            CategoryBean categoryBean = categoryList.get(position);
            holder.binding.titleText.setText(categoryBean.getCategoryName());
            holder.binding.titleText.setTag(categoryBean);
            if(categoryBean.isSelected()){
                holder.binding.titleText.setTextColor(Color.WHITE);
                holder.binding.titleText.setBackgroundColor(Color.rgb(0xff, 0x50, 0x00));
            }else{
                holder.binding.titleText.setTextColor(Color.rgb(0xff, 0x50, 0x00));
                holder.binding.titleText.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        public class CategoryViewHolder extends RecyclerView.ViewHolder {

            ItemCategoryBinding binding;

            public CategoryViewHolder(ItemCategoryBinding binding) {
                super(binding.getRoot());

                this.binding = binding;
            }
        }
    }

    class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

        private OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemServicePictureBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_service_picture, parent, false);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(v);
                    }
                }
            });
            return new ServiceViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
            ServiceBean serviceBean = serviceList.get(position);
            holder.binding.titleText.setText(serviceBean.getServiceName());
            Picasso.get().load(serviceBean.getPicture()).placeholder(R.drawable.ic_placeholder).into(holder.binding.iconImage);
            if(serviceBean.isSelected()){
                holder.binding.titleText.setTextColor(Color.rgb(0xff, 0x50, 0x00));
            }else{
                holder.binding.titleText.setTextColor(getResources().getColor(R.color.colorContent));
            }
        }

        @Override
        public int getItemCount() {
            return serviceList.size();
        }

        public class ServiceViewHolder extends RecyclerView.ViewHolder {

            ItemServicePictureBinding binding;

            public ServiceViewHolder(ItemServicePictureBinding binding) {
                super(binding.getRoot());

                this.binding = binding;
            }
        }
    }

    interface OnItemClickListener{
        void onItemClick(View view);
    }
}
