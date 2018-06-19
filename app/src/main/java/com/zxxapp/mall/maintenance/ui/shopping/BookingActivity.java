package com.zxxapp.mall.maintenance.ui.shopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.example.http.HttpUtils;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.adapter.ShoppingOrderAdapter;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.PaymentBean;
import com.zxxapp.mall.maintenance.bean.RequestUploadBean;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.AreaBean;
import com.zxxapp.mall.maintenance.bean.account.CartResult;
import com.zxxapp.mall.maintenance.bean.account.User;
import com.zxxapp.mall.maintenance.config.AppConfig;
import com.zxxapp.mall.maintenance.databinding.ActivityBookingBinding;
import com.zxxapp.mall.maintenance.databinding.ItemShopPictureListBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.helper.alipay.AuthResult;
import com.zxxapp.mall.maintenance.helper.alipay.OrderInfoUtil2_0;
import com.zxxapp.mall.maintenance.helper.alipay.PayResult;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AreaModel;
import com.zxxapp.mall.maintenance.model.PictureDataModel;
import com.zxxapp.mall.maintenance.model.ShoppingCartModel;
import com.zxxapp.mall.maintenance.ui.gank.child.GoodsDetailActivity;
import com.zxxapp.mall.maintenance.ui.mine.child.LoginActivity;
import com.zxxapp.mall.maintenance.ui.shop.AuthActivity;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.CameraUtil;
import com.zxxapp.mall.maintenance.utils.FileUtil;
import com.zxxapp.mall.maintenance.utils.StringUtils;
import com.zxxapp.mall.maintenance.utils.ToastUtil;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioGroup;
import com.zxxapp.mall.maintenance.view.paymentradio.PayRadioPurified;
import com.zxxapp.mall.maintenance.view.webview.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BookingActivity extends BaseActivity<ActivityBookingBinding> {


    private String serviceId = "";
    private String shopId = "";

    private List<PictureDataModel> pictureDataList = new LinkedList<>();
    private String remotePictureUrls = "";

    private Double lat = 0D;
    private Double lng = 0D;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        initView();
        if (getIntent() != null) {
            this.serviceId = (String) getIntent().getSerializableExtra("serviceId");
            this.shopId = (String) getIntent().getSerializableExtra("shopId");
        }
        if(this.shopId==null|| this.shopId.equals("")){
            setTitle("发布需求");
        }else {
            setTitle("预约商家");
        }
        showContentView();
    }



    private void initView() {
        bindingView.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
        bindingView.llLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LocationActivity.start(v.getContext());
                // 创建需要对应于目标Activity的Intent
                Intent intent = new Intent(BookingActivity.this,
                        LocationActivity.class);
                // 启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
                startActivityForResult(intent, 0);
            }
        });

        bindingView.recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        bindingView.recyclerView.setAdapter(new BookingActivity.PictureAdapter(this, pictureDataList));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 0) {
            // 取出Intent里的数据
            lat = data.getDoubleExtra("lat",0);
            lng = data.getDoubleExtra("lng",0);
            String addr = data.getStringExtra("addr");
            bindingView.tvLocation.setText("已定位");
            bindingView.etAddress.setText(addr);
        }
        else if (resultCode == RESULT_OK) {
            File bitmapFile = null;

            if ((requestCode & 0x0f) == 0x01) {
                Bundle extras = data.getExtras();
                Bitmap bm = (Bitmap) extras.get("data");
                bitmapFile = CameraUtil.saveBitmap(this, bm);
            } else if ((requestCode & 0x0f) == 0x04) {
                Uri uri = data.getData();
                bitmapFile = FileUtil.getFile(this, uri);
            }

            if (bitmapFile != null && bitmapFile.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapFile));

                    if ((requestCode & 0xf0) == 0x40) {
                        PictureAdapter adapter = (PictureAdapter) bindingView.recyclerView.getAdapter();
                        PictureDataModel pdm = new PictureDataModel();
                        pdm.setLocalFile(bitmapFile);
                        pictureDataList.add(pdm);
                        adapter.notifyDataSetChanged();
                        //上传图片
                        LinkedList<PictureDataModel> pictures = new LinkedList<PictureDataModel>();
                        pictures.add(pdm);
                        final PictureDataModel pictureDataModel = pictures.pop();
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), pictureDataModel.getLocalFile());
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", pictureDataModel.getLocalFile().getName(), requestFile);
                        HttpClient.Builder.getZhiXiuServer().uploadImage(part)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<RequestUploadBean>() {

                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toast.makeText(BookingActivity.this, "系统错误，请您稍后再试。", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNext(RequestUploadBean resultBean) {
                                        if (resultBean.getSuccess().equals("true")) {
                                            pictureDataModel.setRemoteFile(resultBean.getUrl());
                                            remotePictureUrls+= resultBean.getUrl()+",";
                                            //Toast.makeText(BookingActivity.this, "上传图片成功", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(BookingActivity.this,"上传完成", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(BookingActivity.this, "上传图片失败，请稍候重试。", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void submitOrder()
    {
        bindingView.btnSubmit.setClickable(false);
        String name = bindingView.etAcceptName.getText().toString();
        String phone = bindingView.etAcceptMobile.getText().toString();
        String address = bindingView.etAddress.getText().toString();
        String remark = bindingView.etRemark.getText().toString();
        String uploadImg="";
        if(remotePictureUrls.endsWith(",")){
            remotePictureUrls = remotePictureUrls.substring(0,remotePictureUrls.length()-1);
        }
        uploadImg = remotePictureUrls;
        String strLat = String.valueOf(lat);
        String strLng = String.valueOf(lng);
        String token = AccountHelper.getUser().token;
        if(name.isEmpty()){
            ToastUtil.showToast("姓名不可为空");
            return;
        }
        if(phone.isEmpty()){
            ToastUtil.showToast("电话不可为空");
            return;
        }
        if(address.isEmpty()){
            ToastUtil.showToast("地址不可为空");
            return;
        }
        if(token.isEmpty()){
            LoginActivity.start(BookingActivity.this);
            return;
        }
        Subscription get = HttpClient.Builder.getZhiXiuServer().addOrder(uploadImg,phone,this.serviceId,shopId
        ,address,strLng,strLat,remark,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>(){

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("系统错误，请您稍后再试");

                        bindingView.btnSubmit.setClickable(true);
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if(resultBean.getSuccess().equals("true")){

                            final NormalDialog dialog=new NormalDialog(BookingActivity.this);
                            dialog.content("订单提交成功")
                                    .btnNum(1)
                                    .titleLineHeight(0)
                                    .cornerRadius(10)
                                    .btnText("确认")
                                    .show();
                            dialog.setOnBtnClickL(new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {

                                    BookingActivity.this.finish();
                                    ShopListActivity.instance.finish();
                                }
                            });
                        }else {
                            ToastUtil.showToast(String.valueOf(resultBean.getMsg()));
                            bindingView.btnSubmit.setClickable(true);
                        }

                    }
                });
    }

    public static void start(Activity context, String serviceId) {

        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra("serviceId", serviceId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }
    public static void start(Activity context, String serviceId, String shopId) {

        Intent intent = new Intent(context, BookingActivity.class);
        intent.putExtra("serviceId", serviceId);
        intent.putExtra("shopId", shopId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeBasic();
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }


    class PictureAdapter extends RecyclerView.Adapter<BookingActivity.PictureAdapter.PictureViewHolder> {
        private Context context;
        private List<PictureDataModel> pictureDataList;

        public PictureAdapter(Context context, List<PictureDataModel> pictureList) {
            this.context = context;
            this.pictureDataList = pictureList;
        }

        @NonNull
        @Override
        public BookingActivity.PictureAdapter.PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemShopPictureListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_shop_picture_list, parent, false);
            return new BookingActivity.PictureAdapter.PictureViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BookingActivity.PictureAdapter.PictureViewHolder holder, int position) {
            if (position < pictureDataList.size()) {
                holder.binding.addButton.setVisibility(View.GONE);
                holder.binding.deleteButton.setVisibility(View.GONE);
                holder.binding.picture.setVisibility(View.VISIBLE);

                //加载图片
                PictureDataModel data = pictureDataList.get(position);
                if (data.isLocalFile()) {
                    Picasso.get().load(data.getLocalFile()).into(holder.binding.picture);
                } else if (data.isRemoteFile()) {
                    Picasso.get().load(data.getRemoteFile()).into(holder.binding.picture);
                }
            } else {
                holder.binding.addButton.setVisibility(View.VISIBLE);
                holder.binding.deleteButton.setVisibility(View.GONE);
                holder.binding.picture.setVisibility(View.GONE);

                holder.binding.addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new AlertDialog.Builder(v.getContext()).setItems(R.array.logo_file_source, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    ((Activity) v.getContext()).startActivityForResult(intent, 0x41);
                                } else if (which == 1) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                                    try {
                                        ((Activity) v.getContext()).startActivityForResult(intent, 0x44);
                                    } catch (Exception e) {
                                        new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("本机没有安装文件管理器。").setPositiveButton("确定", null).show();
                                    }
                                }
                            }
                        }).setTitle("来源").show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return pictureDataList.size() + 1;
        }

        class PictureViewHolder extends RecyclerView.ViewHolder {

            ItemShopPictureListBinding binding;

            public PictureViewHolder(ItemShopPictureListBinding binding) {
                super(binding.getRoot());

                this.binding = binding;
            }
        }
    }
}
