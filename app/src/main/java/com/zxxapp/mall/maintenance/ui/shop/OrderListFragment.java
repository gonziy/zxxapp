package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestPaginationBean;
import com.zxxapp.mall.maintenance.bean.shop.OrderBean;
import com.zxxapp.mall.maintenance.databinding.FragmentOrderListBinding;
import com.zxxapp.mall.maintenance.databinding.ItemListFooterBinding;
import com.zxxapp.mall.maintenance.databinding.ItemOrderBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//https://github.com/zyao89/ZLoading

public class OrderListFragment extends Fragment {
    private int orderStatus;
    private FragmentOrderListBinding binding;
    private List<OrderBean> orderBeanList;
    private BeanAdapter adapter;
    private int currentPage = 1;
    private int orderCount = 0;

    public OrderListFragment() {

    }

    public static OrderListFragment newInstance(int orderStatus) {
        OrderListFragment fragment = new OrderListFragment();
        fragment.orderStatus = orderStatus;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_order_list, container, false);
        binding.orderList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        orderBeanList = new LinkedList<>();

        adapter = new BeanAdapter();
        binding.orderList.setAdapter(adapter);
        binding.orderList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.orderList.getLayoutManager();
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                    if (orderCount>orderBeanList.size()
                            && visibleItemCount + pastVisibleItem >= totalItemCount) {
                        binding.swipeRefreshLayout.setRefreshing(true);

                        currentPage++;
                        loadData();
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(View view) {
                OrderBean orderBean = (OrderBean) view.getTag();

                Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
                intent.putExtra("orderId", orderBean.getOrderId());
                intent.putExtra("orderNo", orderBean.getOrderNo());
                startActivity(intent);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderBeanList.clear();
                currentPage = 1;

                loadData();
            }
        });

        loadData();

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadData() {
        Observable<RequestPaginationBean<OrderBean>> observable;
        if (orderStatus == -1) {
            observable = HttpClient.Builder.getZhiXiuServer().getOrderList(AccountHelper.getShop().getShopId(), currentPage, 10);
        } else {
            observable = HttpClient.Builder.getZhiXiuServer().getOrderList(AccountHelper.getShop().getShopId(), orderStatus, currentPage, 10);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestPaginationBean<OrderBean>>() {
                    @Override
                    public void onCompleted() {
                        //关闭正在加载
                        if (binding.swipeRefreshLayout.isRefreshing()) {
                            binding.swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(RequestPaginationBean<OrderBean> orderBeanArrayResultBean) {
                        if ("true".equalsIgnoreCase(orderBeanArrayResultBean.getSuccess())) {
                            orderBeanArrayResultBean.getTotal();
                            for (OrderBean bean : orderBeanArrayResultBean.getDataRows()) {
                                orderBeanList.add(bean);
                            }

                            adapter.notifyDataSetChanged();
                        } else if ("false".equalsIgnoreCase(orderBeanArrayResultBean.getSuccess())) {
                            orderCount = 0;
                            adapter.notifyDataSetChanged();

                            //ToastUtil.showToast("未查询到相关数据。");
                        }
                    }
                });
    }

    class BeanAdapter extends RecyclerView.Adapter<BeanAdapter.BeanViewHolder> {
        private static final int ITEM_VIEW_TYPE_HEADER = 0x01;
        private static final int ITEM_VIEW_TYPE_ITEM = 0x02;
        private static final int ITEM_VIEW_TYPE_FOOTER = 0x03;

        private OnItemClickListener onItemClickListener;
        public void setOnItemClickListener(OnItemClickListener onItemClickListener){
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == orderBeanList.size()) {
                return ITEM_VIEW_TYPE_FOOTER;
            }

            return ITEM_VIEW_TYPE_ITEM;
        }

        @NonNull
        @Override
        public BeanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BeanViewHolder holder = null;

            if (viewType == ITEM_VIEW_TYPE_FOOTER) {
                ItemListFooterBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_list_footer, parent, false);
                holder = new BeanViewHolder(binding);
            } else if (viewType == ITEM_VIEW_TYPE_ITEM) {
                ItemOrderBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_order, parent, false);
                holder = new BeanViewHolder(binding);

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener!=null){
                            onItemClickListener.onItemClick(v);
                        }
                    }
                });
            }

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BeanViewHolder holder, int position) {
            if (position >= getItemCount()) {
                return;
            }

            if (getItemViewType(position) == ITEM_VIEW_TYPE_FOOTER) {
                ItemListFooterBinding binding = (ItemListFooterBinding) holder.binding;

                if (orderBeanList.size() >= orderCount) {
                    binding.noMoreText.setVisibility(View.VISIBLE);
                    binding.loadingText.setVisibility(View.GONE);
                } else {
                    binding.noMoreText.setVisibility(View.GONE);
                    binding.loadingText.setVisibility(View.VISIBLE);
                }
            } else if (getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
                ItemOrderBinding binding = (ItemOrderBinding) holder.binding;
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

                OrderBean bean = orderBeanList.get(position);
                binding.getRoot().setTag(bean);

                binding.orderNoText.setText(bean.getOrderNo());
                binding.customNameText.setText(bean.getName());
                binding.phoneText.setText(bean.getPhone());
                binding.addressText.setText(bean.getLocation());
//            binding.contentText.setText(bean.getContent());

                if (bean.getOrderDate() != null) {
                    binding.orderTimeText.setText(sdf1.format(new Date(Long.parseLong(bean.getOrderDate()))));
                }

                Picasso.get().load(bean.getServicePicture()).placeholder(R.drawable.ic_placeholder).into(binding.servicePictureImage);
                binding.serviceNameText.setText(bean.getServiceName());

                if ("5".equalsIgnoreCase(bean.getStatus())) {
                    Picasso.get().load(R.drawable.ic_order_back).placeholder(R.drawable.ic_placeholder).into(binding.statusImage);
                } else if ("4".equalsIgnoreCase(bean.getStatus())) {
                    Picasso.get().load(R.drawable.ic_order_finished).placeholder(R.drawable.ic_placeholder).into(binding.statusImage);
                } else if ("1".equalsIgnoreCase(bean.getStatus())) {
                    Picasso.get().load(R.drawable.ic_order_paied).placeholder(R.drawable.ic_placeholder).into(binding.statusImage);
                }
            }
        }

        @Override
        public int getItemCount() {
            return orderBeanList.size() + 1;
        }

        class BeanViewHolder extends RecyclerView.ViewHolder {
            private ViewDataBinding binding;

            public BeanViewHolder(ViewDataBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    interface OnItemClickListener{
        void onItemClick(View view);
    }
}
