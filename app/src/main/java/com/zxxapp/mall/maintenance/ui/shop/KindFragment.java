package com.zxxapp.mall.maintenance.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestDataArrayBean;
import com.zxxapp.mall.maintenance.bean.shop.ServiceBean;
import com.zxxapp.mall.maintenance.databinding.FragmentKindBinding;
import com.zxxapp.mall.maintenance.databinding.ItemKindPictureBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class KindFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private FragmentKindBinding binding;
    private List<ServiceBean> repaireList = new LinkedList<>();
    private List<ServiceBean> installList = new LinkedList<>();
    private PictureAdapter repaireAdapter;
    private PictureAdapter installAdapter;

    public KindFragment() {
    }

    public static KindFragment newInstance() {
        KindFragment fragment = new KindFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kind, container, false);

        initView();
        initData();

        return binding.getRoot();
    }

    private void initView(){
        repaireAdapter = new PictureAdapter(0x01, repaireList);
        binding.repaireList.setLayoutManager(new GridLayoutManager(getContext(), 5));
        binding.repaireList.setAdapter(repaireAdapter);

        installAdapter = new PictureAdapter(0x02, installList);
        binding.installList.setLayoutManager(new GridLayoutManager(getContext(), 5));
        binding.installList.setAdapter(installAdapter);
    }

    private void initData(){
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
                            repaireList.clear();
                            installList.clear();

                            ServiceBean[] serviceBeans = requestDataArrayBean.getData();
                            for(ServiceBean bean : serviceBeans){
                                if("w".equalsIgnoreCase(bean.getCategoryType())){
                                    repaireList.add(bean);
                                }else if("t".equalsIgnoreCase(bean.getCategoryType())){
                                    installList.add(bean);
                                }
                            }

                            repaireAdapter.notifyDataSetChanged();
                            installAdapter.notifyDataSetChanged();
                        }else{
                            ToastUtil.showToast("加载数据时发生错误，请稍候重试。");
                        }
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder>{
        private List<ServiceBean> datas;
        private int type;

        public PictureAdapter(int type, List<ServiceBean> datas) {
            this.type = type;
            this.datas = datas;
        }

        @NonNull
        @Override
        public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemKindPictureBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.item_kind_picture, parent, false);
            PictureViewHolder holder = new PictureViewHolder(binding);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
            if(position<datas.size()){
                holder.binding.editImage.setVisibility(View.GONE);
                holder.binding.iconImage.setVisibility(View.VISIBLE);
                holder.binding.titleText.setVisibility(View.VISIBLE);

                ServiceBean model = datas.get(position);
                Picasso.get().load(model.getPicture()).placeholder(R.drawable.ic_placeholder).into(holder.binding.iconImage);
                //Picasso.get().load(R.drawable.ic_file_type_audio).into(holder.binding.iconImage);
                holder.binding.titleText.setText(model.getServiceName());
            }else{
                holder.binding.editImage.setVisibility(View.VISIBLE);
                holder.binding.iconImage.setVisibility(View.GONE);
                holder.binding.titleText.setVisibility(View.GONE);

                holder.binding.editImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), KindChooseActivity.class);
                        if(type==0x01){
                            intent.putExtra("type", "w");
                        }else if(type==0x02){
                            intent.putExtra("type", "t");
                        }
                        startActivityForResult(intent, type);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return datas.size()+1;
        }

        class PictureViewHolder extends RecyclerView.ViewHolder {
            ItemKindPictureBinding binding;

            public PictureViewHolder(ItemKindPictureBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        initData();
    }
}
