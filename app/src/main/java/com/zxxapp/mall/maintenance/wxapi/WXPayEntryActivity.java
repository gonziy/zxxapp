package com.zxxapp.mall.maintenance.wxapi;

import com.alibaba.fastjson.JSON;
import com.example.http.HttpUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.ui.shopping.OrderActivity;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, AppConfig.WX_APPID);
        api.handleIntent(getIntent(), this);

    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d("wxpay", "onPayFinish, errCode = " + resp.errCode);


		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			PayResp payResp = (PayResp) resp;
			int code = resp.errCode;
			String outOrderNo = payResp.extData;
			switch (code) {
				case 0:
					WebViewActivity.loadUrl(WXPayEntryActivity.this, HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付成功");
					ToastUtil.showToast("支付成功");
					WXPayEntryActivity.this.finish();
					break;
				case -1:
					WebViewActivity.loadUrl(WXPayEntryActivity.this, HttpUtils.API_HOST + "payment.aspx?action=succeed&order_no="+outOrderNo,"支付失败");
					ToastUtil.showToast("支付失败");
					WXPayEntryActivity.this.finish();
					break;
				case -2:
					ToastUtil.showToast("支付取消");
					WXPayEntryActivity.this.finish();
					break;
				default:
					ToastUtil.showToast("支付失败");
					setResult(RESULT_OK);
					WXPayEntryActivity.this.finish();
					break;
			}
		}
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}
}