package com.zxxapp.mall.maintenance.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zxxapp.mall.maintenance.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by jingbin on 2016/11/26.
 */

public class ImgLoadUtil {

    private static ImgLoadUtil instance;

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }




    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {

        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.img_one_bi_one)
                .error(R.drawable.img_one_bi_one)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .animate(R.anim.transition_anim)
                .centerCrop()
//                .skipMemoryCache(true) //跳过内存缓存
//                .crossFade(1000)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .placeholder(getDefaultPic(type))
                .error(getDefaultPic(type))
                .into(imageView);
    }

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 电影
                return R.drawable.img_default_movie;
            case 1:// 妹子
                return R.drawable.img_default_meizi;
            case 2:// 书籍
                return R.drawable.img_default_book;
        }
        return R.drawable.img_default_meizi;
    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(Context context, String url, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        Glide.with(context)
                .load(url)
                .error(R.drawable.stackblur_default)
                .placeholder(R.drawable.stackblur_default)
                .crossFade(500)
                .bitmapTransform(new BlurTransformation(context, 23, 4))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircle(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .crossFade(500)
                .error(R.drawable.ic_avatar_default)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType);
    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(100)
                .error(getDefaultPic(0))
                .into(imageView);
    }

    @BindingAdapter("android:showImg")
    public static void showBg(final View view, String url) {

        Glide.with(view.getContext())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.img_one_bi_one)
                .error(R.drawable.img_one_bi_one)
                .animate(R.anim.transition_anim)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(drawable);
                        }
                    }
                });
    }

    @BindingAdapter("android:showImg")
    public static void showImgNoAnimator(ImageView imageView, String url) {
        if(!url.equals(imageView.getTag())) {
            imageView.setTag(null);
            Glide.with(imageView.getContext())
                    .load(url)
                    .crossFade(500)
                    .error(getDefaultPic(0))
                    .into(imageView);
            imageView.setTag(url);
        }
    }

    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .into(imageView);
    }

    /**
     * 书籍列表图片
     */
    @BindingAdapter("android:showBookImg")
    public static void showBookImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .crossFade(500)
                .override((int) CommonUtils.getDimens(R.dimen.book_detail_width), (int) CommonUtils.getDimens(R.dimen.book_detail_height))
                .placeholder(getDefaultPic(2))
                .error(getDefaultPic(2))
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(imageView.getContext(), url, imageView);
    }
}
