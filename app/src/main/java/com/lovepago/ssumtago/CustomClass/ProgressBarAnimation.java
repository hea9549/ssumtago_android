package com.lovepago.ssumtago.CustomClass;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Created by ParkHaeSung on 2017-08-19.
 */

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float to;
    public ProgressBarAnimation(ProgressBar progressBar, float from, float to){
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to-from)  * interpolatedTime;
        progressBar.setProgress((int)value);
    }
}