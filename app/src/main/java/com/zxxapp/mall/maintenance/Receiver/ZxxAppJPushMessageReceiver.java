package com.zxxapp.mall.maintenance.Receiver;

import android.content.Context;

import com.zxxapp.mall.maintenance.helper.jpush.TagAliasOperatorHelper;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class ZxxAppJPushMessageReceiver extends JPushMessageReceiver{
        @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
            ToastUtil.showToast("tag:"+jPushMessage.getAlias());
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
        ToastUtil.showToast("check:"+jPushMessage.getAlias());
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
        ToastUtil.showToast("alias:"+jPushMessage.getAlias());
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context,jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
        ToastUtil.showToast("mobile:"+jPushMessage.getAlias());
    }
}