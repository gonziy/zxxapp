package com.zxxapp.mall.maintenance.view.webview.config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.shopping.BookingActivity;
import com.zxxapp.mall.maintenance.ui.shopping.ShopListActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

/**
 * Created by jingbin on 2016/11/17.
 * js通信接口
 */
public class JavascriptBridgeInterface {
    private Context context;

    public JavascriptBridgeInterface(Context context) {
        this.context = context;
    }


    @JavascriptInterface
    public void ToastShow(String text) {
        if (!TextUtils.isEmpty(text)) {
            ToastUtil.showToast(text);
        }
    }
    @JavascriptInterface
    public void BookingShop(String serviceId,String shopId) {
        BookingActivity.start((Activity)context,serviceId,shopId);
    }

    @JavascriptInterface
    public void FinishActivity() {
        ((Activity)context).finish();
    }

    @JavascriptInterface
    public void GoToUserCenter() {
        ((Activity)context).finish();
        MainActivity.getMainActivity().toViewPager(3);
    }

    @JavascriptInterface
    public void GoToCart() {
        ((Activity)context).finish();
        MainActivity.getMainActivity().toViewPager(2);
    }

    @JavascriptInterface
    public void GoToViewPager(String id) {
        if (!TextUtils.isEmpty(id)) {
            ((Activity) context).finish();
            MainActivity.getMainActivity().toViewPager(Integer.valueOf(id));
        }
    }

    @JavascriptInterface
    public void GoodsActivity(String id) {
        if (!TextUtils.isEmpty(id)) {
            GoodsDetailActivity.start((Activity)context, id);
        }
    }

    @JavascriptInterface
    public void ShopList(String category_id) {
        if (!TextUtils.isEmpty(category_id)) {
            ShopListActivity.start((Activity)context,category_id);
        }
    }

    @JavascriptInterface
    public void Login() {
        LoginActivity.start(context);
    }

    @JavascriptInterface
    public void Logout() {
        User user = new User();
        user.setPassword("");
        user.setUserName("");
        user.setNickName("");
        user.setGroupName("");
        user.setUserID(0);
        BaseApplication.getInstance().setUser(user);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(AccountHelper.isLogin())
        {
            BaseApplication.getInstance().setUser(user);
        }
        LoginActivity.start(context);
    }

    @JavascriptInterface
    public void CallPhone(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    @JavascriptInterface
    public void SendSMS(String phone,String text) {
        SmsManager smsManager = SmsManager.getDefault();
        String content = text;
        smsManager.divideMessage(content);
        smsManager.sendTextMessage(phone, null, content, null, null);
    }
}
