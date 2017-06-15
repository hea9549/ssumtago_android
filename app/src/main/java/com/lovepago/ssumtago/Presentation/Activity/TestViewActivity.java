package com.lovepago.ssumtago.Presentation.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.lovepago.ssumtago.CustomClass.CustomView.STGWaveView;
import com.lovepago.ssumtago.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestViewActivity extends AppCompatActivity {
    AnimatorSet mAnimatorSet;
    STGWaveView waveView;
    STGListener listener = new STGListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Animator> animators = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        waveView = (STGWaveView)findViewById(R.id.waveView);
        waveView.setShapeType(STGWaveView.ShapeType.SQUARE);
        waveView.setWaterLevelRatio(0.5f);
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
                waveView, "waveShiftRatio", 0f, 1f);
        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        animators.add(waveShiftAnim);
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
                waveView, "amplitudeRatio", 0.02f, 0.05f);
        amplitudeAnim.setDuration(2000);
        amplitudeAnim.addListener(listener);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        animators.add(amplitudeAnim);
        waveView.setWaveColor(
                Color.parseColor("#50019A"),
                Color.parseColor("#3901D4"));

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animators);
        waveView.setShowWave(true);
        mAnimatorSet.start();
    }
    class STGListener implements Animator.AnimatorListener{
        float before = 0;
        float after = 0;
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Random random = new Random();
            animation.cancel();
            float randomFloat = random.nextFloat();
            if(before == 0)before = 0.05f;
            else before = after;
            randomFloat = random.nextFloat();
            after = randomFloat - (int)randomFloat;
            while (after>0.08f)after%=0.08f;
            while (after<0.02f)after+=0.02f;
            Log.e("waveView","here = "+before+","+after);
            ObjectAnimator animator = ObjectAnimator.ofFloat(waveView,"amplitudeRatio",before,after);
            Log.e("waveView","tick!!");
            animator.setDuration(2000);
            animator.addListener(this);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }
}
