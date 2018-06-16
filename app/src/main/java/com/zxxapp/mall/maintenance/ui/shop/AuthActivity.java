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
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.bean.RequestBaseBean;
import com.zxxapp.mall.maintenance.bean.RequestUploadBean;
import com.zxxapp.mall.maintenance.databinding.ActivityShopSubmitAuthBinding;
import com.zxxapp.mall.maintenance.databinding.ItemShopPictureListBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;
import com.zxxapp.mall.maintenance.http.HttpClient;
import com.zxxapp.mall.maintenance.model.PictureDataModel;
import com.zxxapp.mall.maintenance.utils.CameraUtil;
import com.zxxapp.mall.maintenance.utils.FileUtil;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthActivity extends AppCompatActivity {

    private ActivityShopSubmitAuthBinding binding;
    private PictureDataModel cardFtontModel = new PictureDataModel();
    private PictureDataModel cardBackModel = new PictureDataModel();
    private PictureDataModel licenseModel = new PictureDataModel();
    private List<PictureDataModel> pictureDataList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_submit_auth);
        binding.cardFront.setOnClickListener(new View.OnClickListener() {
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
        binding.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext()).setItems(R.array.logo_file_source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) v.getContext()).startActivityForResult(intent, 0x21);
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                            intent.addCategory(Intent.CATEGORY_OPENABLE);

                            try {
                                ((Activity) v.getContext()).startActivityForResult(intent, 0x24);
                            } catch (Exception e) {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("本机没有安装文件管理器。").setPositiveButton("确定", null).show();
                            }
                        }
                    }
                }).setTitle("来源").show();
            }
        });
        binding.license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext()).setItems(R.array.logo_file_source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ((Activity) v.getContext()).startActivityForResult(intent, 0x31);
                        } else if (which == 1) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                            intent.addCategory(Intent.CATEGORY_OPENABLE);

                            try {
                                ((Activity) v.getContext()).startActivityForResult(intent, 0x34);
                            } catch (Exception e) {
                                new AlertDialog.Builder(v.getContext()).setTitle("提示").setMessage("本机没有安装文件管理器。").setPositiveButton("确定", null).show();
                            }
                        }
                    }
                }).setTitle("来源").show();
            }
        });
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        binding.recyclerView.setAdapter(new PictureAdapter(this, pictureDataList));
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.realName.getText().toString();
                String no = binding.cardNumber.getText().toString();

                if("".equalsIgnoreCase(name)){
                    ToastUtil.showToast("请输入真实姓名。");
                    return ;
                }

                if("".equalsIgnoreCase(no)){
                    ToastUtil.showToast("请输入身份证号码。");
                    return;
                }

                if(!cardFtontModel.isLocalFile() && !cardFtontModel.isRemoteFile()) {
                    ToastUtil.showToast("请选择身份证正面照片。");
                    return;
                }

                if(!cardBackModel.isLocalFile() && !cardBackModel.isRemoteFile()){
                    ToastUtil.showToast("请选择身份证背面图片。");
                    return ;
                }

                //保存图片
                LinkedList<PictureDataModel> pictures = new LinkedList<>();
                if (cardFtontModel.isLocalFile()) {
                    pictures.add(cardFtontModel);
                }
                if (cardBackModel.isLocalFile()) {
                    pictures.add(cardBackModel);
                }
                for (PictureDataModel p : pictureDataList) {
                    if (p.isLocalFile()) {
                        pictures.add(p);
                    }
                }

                uploadPicturesAndSave(pictures);
            }
        });
    }

    private void uploadPicturesAndSave(final LinkedList<PictureDataModel> pictures) {
        if (pictures.size() == 0) {
            StringBuilder sb = new StringBuilder();
            for(PictureDataModel p : pictureDataList){
                if(sb.length()>0){
                    sb.append(",");
                }
                sb.append(p.getRemoteFile());
            }
            String extpics = sb.toString();

            //开始保存
            HttpClient.Builder.getZhiXiuServer().addAuth(
                    AccountHelper.getUser().token,
                    binding.realName.getText().toString(),
                    binding.cardNumber.getText().toString(),
                    cardFtontModel.getRemoteFile()+","+cardBackModel.getRemoteFile(),
                    licenseModel.getRemoteFile(),
                    extpics)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<RequestBaseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RequestBaseBean requestBaseBean) {
                            if("100".equalsIgnoreCase(requestBaseBean.getCode())){
                                new AlertDialog.Builder(AuthActivity.this)
                                        .setTitle("提示")
                                        .setMessage("成功提交商铺身份验证信息。")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                        }).show();
                            }else if("101".equalsIgnoreCase(requestBaseBean.getCode())){
                                new AlertDialog.Builder(AuthActivity.this)
                                        .setTitle("提示")
                                        .setMessage("提交商铺身份验证信息失败，请稍候重试。")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            }
                        }
                    });

        } else {
            final PictureDataModel pictureDataModel = pictures.pop();

            Toast.makeText(AuthActivity.this, "开始文件上传...", Toast.LENGTH_LONG).show();

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
                            Toast.makeText(AuthActivity.this, "系统错误，请您稍后再试。", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNext(RequestUploadBean resultBean) {
                            if (resultBean.getSuccess().equals("true")) {
                                pictureDataModel.setRemoteFile(resultBean.getUrl());
                                Toast.makeText(AuthActivity.this, "上传图片成功。", Toast.LENGTH_SHORT).show();

                                //继续下一个文件的上传
                                uploadPicturesAndSave(pictures);
                            } else {
                                Toast.makeText(AuthActivity.this, "上传图片失败，请稍候重试。", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
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
                    Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(bitmapFile));

                    if ((requestCode & 0xf0) == 0x10) {
                        binding.cardFront.setImageBitmap(bitmap);
                        cardFtontModel.setLocalFile(bitmapFile);
                    } else if ((requestCode & 0xf0) == 0x20) {
                        binding.cardBack.setImageBitmap(bitmap);
                        cardBackModel.setLocalFile(bitmapFile);
                    } else if ((requestCode & 0xf0) == 0x30) {
                        binding.license.setImageBitmap(bitmap);
                        licenseModel.setLocalFile(bitmapFile);
                    } else if ((requestCode & 0xf0) == 0x40) {
                        PictureAdapter adapter = (PictureAdapter) binding.recyclerView.getAdapter();
                        PictureDataModel pdm = new PictureDataModel();
                        pdm.setLocalFile(bitmapFile);
                        pictureDataList.add(pdm);
                        adapter.notifyDataSetChanged();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
        private Context context;
        private List<PictureDataModel> pictureDataList;

        public PictureAdapter(Context context, List<PictureDataModel> pictureList) {
            this.context = context;
            this.pictureDataList = pictureList;
        }

        @NonNull
        @Override
        public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemShopPictureListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_shop_picture_list, parent, false);
            return new PictureViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
            if (position < pictureDataList.size()) {
                holder.binding.addButton.setVisibility(View.GONE);
                holder.binding.deleteButton.setVisibility(View.VISIBLE);
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
