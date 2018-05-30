package com.zxxapp.mall.maintenance.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yinyy on 2017/8/21.
 */

public class CameraUtil{
    public static File saveBitmap(Context context, Bitmap bitmap){
        File file = createPreviewImageFile(context);

        try(FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
        }catch (Exception e){
            e.printStackTrace();
        }

        return file;
    }

    public static File createPreviewImageFile(Context context){
        File imagePath = new File(context.getFilesDir(), "images");
        if(!imagePath.exists()){
            imagePath.mkdirs();
        }

        File newFile = new File(imagePath, Long.toHexString(System.currentTimeMillis()) + ".jpg");
        if(!newFile.exists()){
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFile;
    }

    public static Uri createPreviewImageUri(Context context, File file){
        Uri contentUri = FileProvider.getUriForFile(context, "com.zxxapp.mall.maintenance.fileprovider", file);
        return contentUri;
    }
}
