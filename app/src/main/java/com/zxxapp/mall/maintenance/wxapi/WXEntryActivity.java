package com.zxxapp.mall.maintenance.wxapi;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.http.HttpUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.bean.account.AccessTokenBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.MineActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.AutoLoginActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private String WXUnionID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

        BaseApplication.getInstance().WXApi.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == baseResp.getType()) {
                    ToastUtil.showToast("分享失败");
                }
                else {
                    ToastUtil.showToast("登录失败");
                }
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) baseResp).code;
//                        ToastUtil.showToast("code = " + code);
                        if(!code.isEmpty())
                        {
                            OkGo.<AccessTokenBean>get("https://api.weixin.qq.com/sns/oauth2/access_token")
                                    .tag(this)
                                    .cacheMode(CacheMode.NO_CACHE)
                                    .params("appid", AppConfig.WX_APPID)
                                    .params("secret", AppConfig.WX_APPSECRET)
                                    .params("code", code)
                                    .params("grant_type", "authorization_code")

                                    .execute(new Callback<AccessTokenBean>() {
                                        @Override
                                        public void onStart(Request<AccessTokenBean, ? extends Request> request) {

                                        }

                                        @Override
                                        public void onSuccess(Response<AccessTokenBean> response) {
                                            okhttp3.Response resp = response.getRawResponse();
                                            try {
                                                String body = resp.body().string();
                                                if(!body.isEmpty())
                                                {
                                                    JSONObject jsonObject = new JSONObject(body);
                                                    if(jsonObject!=null)
                                                    {
                                                        WXUnionID = jsonObject.getString("unionid");
                                                        Intent intent = new Intent(WXEntryActivity.this, AutoLoginActivity.class);
                                                        intent.putExtra("WXUnionID", WXUnionID);
                                                        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
                                                        ActivityCompat.startActivity(WXEntryActivity.this, intent, options.toBundle());
                                                        WXEntryActivity.this.finish();

                                                    }
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onCacheSuccess(Response<AccessTokenBean> response) {

                                        }

                                        @Override
                                        public void onError(Response<AccessTokenBean> response) {

                                        }

                                        @Override
                                        public void onFinish() {



                                        }

                                        @Override
                                        public void uploadProgress(Progress progress) {

                                        }

                                        @Override
                                        public void downloadProgress(Progress progress) {

                                        }

                                        @Override
                                        public AccessTokenBean convertResponse(okhttp3.Response response) throws Throwable {
                                            return null;
                                        }
                                    });




                        }



                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtil.showToast("微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

}
