package com.zxxapp.mall.maintenance.ui.shop;

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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestUploadBean;
import com.zxxapp.mall.maintenance.databinding.ActivityShopCreateBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.utils.CameraUtil;
import com.zxxapp.mall.maintenance.utils.FileUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CreateActivity extends AppCompatActivity {

    private ActivityShopCreateBinding binding;
    private File logoFile = null;
    private String logoFileUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_create);
        binding.locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LocationActivity.class);
                startActivityForResult(intent, 0x08);
            }
        });
        binding.logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext()).setItems(R.array.logo_file_source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //File previewImageFile = CameraKit.createPreviewImageFile(view.getContext());
                            //Uri uri = CameraKit.createPreviewImageUri(view.getContext(), previewImageFile);

//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
//                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) v.getContext()).startActivityForResult(intent, 0x01);
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                            intent.addCategory(Intent.CATEGORY_OPENABLE);

                            try {
                                ((Activity) v.getContext()).startActivityForResult(intent, 0x04);
                            } catch (Exception e) {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("本机没有安装文件管理器。").setPositiveButton("确定", null).show();
                            }
                        }
                    }
                }).setTitle("来源").show();
            }
        });
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logoFile != null) {
                    uploadFile(logoFile);
                }else{
                    Toast.makeText(CreateActivity.this, "请先选择商铺Logo。", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //商铺顶部图片为：500*130
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 0x08) {
                String position = data.getStringExtra("position");
                binding.locationText.setText(position);
                return;
            }

            //其它code
            if (requestCode == 0x01) {
                Bundle extras = data.getExtras();
                Bitmap bm = (Bitmap) extras.get("data");
                logoFile = CameraUtil.saveBitmap(this, bm);
            } else if (requestCode == 0x04) {
                Uri uri = data.getData();
                logoFile = FileUtil.getFile(this, uri);
            }

            if (logoFile != null && logoFile.exists()) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(logoFile));
                    binding.logoImage.setImageBitmap(bitmap);
                    int height = 130 * binding.logoImage.getWidth() / 500;
                    binding.logoImage.setAdjustViewBounds(true);
                    binding.logoImage.setMaxHeight(height);

                    Log.d("TEST", binding.logoImage.getWidth() + "px - " + binding.logoImage.getHeight() + "px");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadFile(File file){
        Toast.makeText(CreateActivity.this, "开始上传商铺图片...", Toast.LENGTH_LONG).show();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), logoFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", logoFile.getName(), requestFile);
        HttpClient.Builder.getZhiXiuServer().uploadImage(part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestUploadBean>() {
                    private boolean shouldNext = false;

                    @Override
                    public void onCompleted() {
                        if(shouldNext){
                            saveInfo();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CreateActivity.this, "系统错误，请您稍后再试。", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(final RequestUploadBean resultBean) {
                        if (resultBean.getSuccess().equals("true")) {
                            logoFileUrl = resultBean.getUrl();

                            shouldNext = true;

                            Toast.makeText(CreateActivity.this, "上传商铺图片成功，开始保存商铺信息...", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CreateActivity.this, "上传商铺图片失败，请稍候重试。", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveInfo(){
        HttpClient.Builder.getZhiXiuServer().createShop(
                AccountHelper.getUser().token,
                binding.titleEdit.getText().toString(),
                logoFileUrl,
                binding.addressEdit.getText().toString(),
                binding.introEdit.getText().toString(),
                binding.noticeEdit.getText().toString(),
                binding.locationText.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RequestBaseBean>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CreateActivity.this, "系统错误，请您稍后再试。", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(RequestBaseBean requestBaseBean) {
                        new AlertDialog.Builder(CreateActivity.this)
                                .setMessage("提示")
                                .setMessage(requestBaseBean.getSuccess().equals("true") ? "商铺创建成功。" : "商铺创建失败。")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        setResult(RESULT_OK);
                                        finish();//关闭窗口
                                    }
                                }).show();
                    }
                });
    }
}
