package com.lovepago.ssumtago.Presentation.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends STGBaseActivity{
    @BindView(R.id.lottie_splash_splashView)
    LottieAnimationView lottie_splashView;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        lottie_splashView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                navigateActivity(LoginActivity.class);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
/*        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

            }
        }, 2000);*/
    }

}
