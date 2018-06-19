package com.zxxapp.mall.maintenance.ui.mine.child;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.BaseApplication;
import com.zxxapp.mall.maintenance.base.BaseActivity;
import com.zxxapp.mall.maintenance.bean.RequestUploadBean;
import com.zxxapp.mall.maintenance.bean.ResultBean;
import com.zxxapp.mall.maintenance.bean.UserLoginBean;
import com.zxxapp.mall.maintenance.bean.account.LoginResult;
import com.zxxapp.mall.maintenance.databinding.ActivityEditUserInfoBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.model.PictureDataModel;
import com.zxxapp.mall.maintenance.ui.shop.AuthActivity;
import com.zxxapp.mall.maintenance.utils.CameraUtil;
import com.zxxapp.mall.maintenance.utils.FileUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditUserInfoActivity extends BaseActivity<ActivityEditUserInfoBinding> {

    private PictureDataModel faceModel = new PictureDataModel();
    private String remoteImageUrl ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        setTitle("修改信息");
        initView();
    }

    private void initView() {
        if(AccountHelper.isLogin())
        {
            Subscription get = HttpClient.Builder.getZhiXiuServer().getMyMessageAPI(AccountHelper.getUser().token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UserLoginBean>(){

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                            ToastUtil.showToast("系统错误，请您稍后再试");
                        }

                        @Override
                        public void onNext(UserLoginBean userLoginBean) {
                            if (userLoginBean.getSuccess().equals("true")) {
                                bindingView.etNickname.setText(userLoginBean.getData().getNickName());
                                bindingView.etPhone.setText(userLoginBean.getData().getPhone());
                                if (userLoginBean.getData().getAvatarImg()==null) {
                                    Picasso.get().load(R.mipmap.user_face).into(bindingView.ivUserFace);
                                } else {
                                    Picasso.get().load(userLoginBean.getData().getAvatarImg().toString()).into(bindingView.ivUserFace);
                                }
                                showContentView();
                            } else {
                                ToastUtil.showToast("获取信息失败");

                            }
                        }
                    });

        }else {
            ToastUtil.showToast("请登录");
            EditUserInfoActivity.this.finish();
        }
        bindingView.ivUserFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext()).setItems(R.array.logo_file_source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) v.getContext()).startActivityForResult(intent, 0x11);
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                            intent.addCategory(Intent.CATEGORY_OPENABLE);

                            try {
                                ((Activity) v.getContext()).startActivityForResult(intent, 0x14);
                            } catch (Exception e) {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("本机没有安装文件管理器。").setPositiveButton("确定", null).show();
                            }
                        }
                    }
                }).setTitle("来源").show();
            }
        });

        bindingView.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String nickname = bindingView.etNickname.getText().toString();
                 String phone = bindingView.etPhone.getText().toString();

                 if(nickname.isEmpty()){
                     ToastUtil.showToast("昵称不可为空");
                     return;
                 }
                if(phone.isEmpty()){
                    ToastUtil.showToast("手机不可为空");
                    return;
                }

                Subscription get = HttpClient.Builder.getZhiXiuServer().editAccountAPI(AccountHelper.getUser().token,nickname,phone,remoteImageUrl)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>(){

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {

                                ToastUtil.showToast("系统错误，请您稍后再试");
                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                if (resultBean.getSuccess().equals("true")) {
                                    ToastUtil.showToast("修改成功");
                                    EditUserInfoActivity.this.finish();

                                } else {
                                    ToastUtil.showToast("获取信息失败");

                                }
                            }
                        });
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
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
                    if ((requestCode & 0xf0) == 0x10) {
                        faceModel.setLocalFile(bitmapFile);
                        LinkedList<PictureDataModel> pictures = new LinkedList<>();
                        if (faceModel.isLocalFile()) {
                            pictures.add(faceModel);
                        }
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
                                        Toast.makeText(EditUserInfoActivity.this, "系统错误，请您稍后再试。", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNext(RequestUploadBean resultBean) {
                                        if (resultBean.getSuccess().equals("true")) {

                                            remoteImageUrl = resultBean.getUrl();
                                            Toast.makeText(EditUserInfoActivity.this, remoteImageUrl, Toast.LENGTH_LONG).show();
                                            pictureDataModel.setRemoteFile(remoteImageUrl);
                                            Picasso.get().load(remoteImageUrl).into(bindingView.ivUserFace);
                                        } else {
                                            Toast.makeText(EditUserInfoActivity.this, "上传图片失败，请稍候重试。", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, EditUserInfoActivity.class);
        mContext.startActivity(intent);
    }

}
