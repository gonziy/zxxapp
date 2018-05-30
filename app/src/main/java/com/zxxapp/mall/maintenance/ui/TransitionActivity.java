package com.zxxapp.mall.maintenance.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.bumptech.glide.Glide;
import com.zxxapp.mall.maintenance.MainActivity;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.app.ConstantsImageUrl;
import com.zxxapp.mall.maintenance.bean.article.ArticleBean;
import com.zxxapp.mall.maintenance.databinding.ActivityTransitionBinding;
import com.zxxapp.mall.maintenance.http.RequestImpl;
import com.zxxapp.mall.maintenance.model.AppUpdateModel;
import com.zxxapp.mall.maintenance.model.ArticleModel;
import com.zxxapp.mall.maintenance.utils.BaseTools;
import com.zxxapp.mall.maintenance.utils.CommonUtils;
import com.zxxapp.mall.maintenance.utils.PerfectClickListener;
import com.zxxapp.mall.maintenance.utils.ToastUtil;

import java.util.Random;

import rx.Subscription;

public class TransitionActivity extends AppCompatActivity {

    private ActivityTransitionBinding mBinding;
    private boolean animationEnd;
    private boolean isIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getVersion();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transition);

        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        mBinding.ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.img_transition_default));
        //getAD();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.ivDefultPic.setVisibility(View.GONE);
                mBinding.ivLogo.setVisibility(View.GONE);
            }
        }, 1500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, 5000);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transition_anim);
//        animation.setAnimationListener(animationListener);
//        mBinding.ivPic.startAnimation(animation);

        mBinding.tvJump.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                toMainActivity();
//                animationEnd();
            }
        });
    }

    /**
     * 实现监听跳转效果
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            animationEnd();
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };


    private void getAD(){
        new ArticleModel().getADData(new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                ArticleBean bean = (ArticleBean)object;
                if(bean!=null) {
                    if (bean.getResults().size() > 0) {
                        Glide.with(TransitionActivity.this)
                                .load(bean.getResults().get(0).getImg_url())
                                .placeholder(R.drawable.img_transition_default)
                                .error(R.drawable.img_transition_default)
                                .into(mBinding.ivPic);
                    }
                }
            }

            @Override
            public void loadFailed() {
                Glide.with(TransitionActivity.this)
                        .load(R.drawable.img_transition_default)
                        .placeholder(R.drawable.img_transition_default)
                        .error(R.drawable.img_transition_default)
                        .into(mBinding.ivPic);
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }

    private void animationEnd() {
        synchronized (TransitionActivity.this) {
            if (!animationEnd) {
                animationEnd = true;
                mBinding.ivPic.clearAnimation();
                toMainActivity();
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }
}
