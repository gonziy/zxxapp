package com.zxxapp.mall.maintenance.ui.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.MessageListAdapter;
import com.zxxapp.mall.maintenance.adapter.OrderByAccountIdAdapter;
import com.zxxapp.mall.maintenance.adapter.ShoppingOrderAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseFragment;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.bean.shopping.OrderByAccountIdBean;
import com.zxxapp.mall.maintenance.databinding.FragmentMessageBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.OrderByAccountModel;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.OrderByAccountIdActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import rx.Subscription;

/**
 * Created by Thinten on 2018-06-02
 * www.thinten.com
 * 9486@163.com.
 */
public class FragmentMessage extends BaseFragment<FragmentMessageBinding>  {



    private MainActivity activity;
    private OrderByAccountModel mModel;
    private OrderByAccountIdBean bean;
    private MessageListAdapter adapter;
    private List<OrderByAccountIdBean.DataRowsBean> list;

    public String ServiceId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();

        mModel = new OrderByAccountModel();
        bean = new OrderByAccountIdBean();
        adapter = new MessageListAdapter(activity);
        initView();
        if(AccountHelper.isLogin())
        {
            User user = AccountHelper.getUser();
            LoadData(user.token,"1","20");
        }


    }
    private void initView() {
        bindingView.xrvList.setPullRefreshEnabled(true);
    }
    public void LoadData(String token,String pageIndex,String pageCount){
        mModel.setData(token,pageIndex,pageCount);
        mModel.getData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                bean = (OrderByAccountIdBean) object;
                if(bean.isSuccess() && !bean.getDataRows().isEmpty()){
                    list = bean.getDataRows();
                    adapter.clear();
                    adapter.addAll(list);
                    bindingView.xrvList.setLayoutManager( new LinearLayoutManager((Context) activity));
                    bindingView.xrvList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.showToast(bean.getMsg());
                }
                showContentView();
            }

            @Override
            public void loadFailed() {
                ToastUtil.showToast("获取数据失败");
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }


    @Override
    public int setContent() {
        return R.layout.fragment_message;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
