package com.lovepago.ssumtago.Presentation.Activity;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;
import com.skyfishjy.library.RippleBackground;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartGuideActivity extends STGBaseActivity{

    @BindView(R.id.wrapper_startGuide_rippleBack)
    RippleBackground wrapper_rippleBack;
    @BindView(R.id.wrapper_startGuide_guide3)
    ConstraintLayout wrapper_guide3;
    @BindView(R.id.tv_startGuide_guide1)
    TextView tv_guide1;
    @BindView(R.id.tv_startGuide_guide2)
    TextView tv_guide2;
    @BindView(R.id.wrapper_startGuide_guide4)
    ConstraintLayout wrapper_guide4;
    @BindView(R.id.btn_startGuide_tap)
    Button btn_tap;
    @BindViews({R.id.img_startGuide_indicator1,R.id.img_startGuide_indicator2,R.id.img_startGuide_indicator3})
    List<ImageView> imgs_indicator;

    private int indicatorIndex = 0;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_start_guide);
        ButterKnife.bind(this);
        wrapper_rippleBack.startRippleAnimation();
        btn_tap.setOnClickListener(v->{
            Animation fadeOut= new AlphaAnimation(1,0);
            fadeOut.setInterpolator(new AccelerateInterpolator());
            fadeOut.setDuration(500);
            fadeOut.setFillAfter(true);
            Animation fadeIn = new AlphaAnimation(0,1);
            fadeIn.setInterpolator(new DecelerateInterpolator());
            fadeIn.setDuration(500);
            fadeIn.setFillAfter(true);
            switch (indicatorIndex){
                case 0:
                    imgs_indicator.get(0).setImageResource(R.drawable.guide_empty_box);
                    imgs_indicator.get(1).setImageResource(R.drawable.guide_full_box);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_guide2.setVisibility(View.VISIBLE);
                            tv_guide2.startAnimation(fadeIn);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tv_guide1.startAnimation(fadeOut);
                    indicatorIndex++;
                    break;
                case 1:
                    imgs_indicator.get(1).setImageResource(R.drawable.guide_empty_box);
                    imgs_indicator.get(2).setImageResource(R.drawable.guide_full_box);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            wrapper_guide3.setVisibility(View.VISIBLE);
                            wrapper_guide3.startAnimation(fadeIn);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tv_guide2.startAnimation(fadeOut);
                    indicatorIndex++;
                    break;
                case 2:
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                          /*  wrapper_guide4.setVisibility(View.VISIBLE);
                            wrapper_guide4.startAnimation(fadeIn);
                            btn_tap.setVisibility(View.INVISIBLE);
                            wrapper_rippleBack.setVisibility(View.INVISIBLE);*/
                            navigateActivity(SurveyActivity.class,"surveyId",2);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    wrapper_guide3.startAnimation(fadeOut);
                    break;
            }
        });
    }

    @OnClick(R.id.btn_startGuide_startSurvey)
    void onStartSurveyClick(){
        navigateActivity(SurveyActivity.class,"surveyId",2);
    }
}
