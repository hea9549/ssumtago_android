package com.lovepago.ssumtago.Presentation.Activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.lovepago.ssumtago.CustomClass.CustomView.STGAlertDialog;
import com.lovepago.ssumtago.CustomClass.CustomView.STGWaveView;
import com.lovepago.ssumtago.CustomClass.CustomView.SurveySelectDialog;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Data.Model.ResultFormat;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.Model.ValueFormat;
import com.lovepago.ssumtago.Presentation.Presenter.LobyActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class LobyActivity extends STGBaseActivity implements LobyActivityPresenter.View {
    @Inject
    LobyActivityPresenter presenter;
    @BindViews({R.id.cb_percent_1, R.id.cb_percent_2, R.id.cb_percent_3, R.id.cb_percent_4, R.id.cb_percent_5, R.id.cb_percent_6, R.id.cb_percent_7, R.id.cb_percent_8, R.id.cb_percent_9, R.id.cb_percent_10, R.id.cb_percent_11, R.id.cb_percent_12, R.id.cb_percent_13, R.id.cb_percent_14, R.id.cb_percent_15, R.id.cb_percent_16, R.id.cb_percent_17, R.id.cb_percent_18, R.id.cb_percent_19, R.id.cb_percent_20, R.id.cb_percent_21, R.id.cb_percent_22, R.id.cb_percent_23, R.id.cb_percent_24, R.id.cb_percent_25, R.id.cb_percent_26, R.id.cb_percent_27, R.id.cb_percent_28, R.id.cb_percent_29, R.id.cb_percent_30, R.id.cb_percent_31, R.id.cb_percent_32, R.id.cb_percent_33, R.id.cb_percent_34, R.id.cb_percent_35, R.id.cb_percent_36, R.id.cb_percent_37, R.id.cb_percent_38, R.id.cb_percent_39, R.id.cb_percent_40, R.id.cb_percent_41, R.id.cb_percent_42, R.id.cb_percent_43, R.id.cb_percent_44, R.id.cb_percent_45, R.id.cb_percent_46, R.id.cb_percent_47, R.id.cb_percent_48, R.id.cb_percent_49, R.id.cb_percent_50})
    List<CheckBox> cbs_percents;
    @BindView(R.id.tv_loby_percent)
    TextView tv_percent;
    @BindView(R.id.tv_loby_date)
    TextView tv_reportDate;
    @BindView(R.id.tv_loby_percentDesc)
    TextView tv_percentDesc;
    @BindView(R.id.wrapper_loby_expandablePercent)
    ExpandableRelativeLayout wrapper_expandablePercent;
    @BindView(R.id.wrapper_loby_expandablePredictLove)
    ExpandableRelativeLayout wrapper_expandablePredictLove;

    private long backButtonMills;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_loby);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
        wrapper_expandablePercent.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                super.onPreOpen();
                presenter.onPercentOpened();
            }
        });
        wrapper_expandablePredictLove.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                super.onPreOpen();
                presenter.onPredictLoveOpened();
            }
        });
    }



    @OnClick(R.id.btn_loby_addReport)
    void onAddReportClick() {
        presenter.onAddReportClick();
    }

    @OnClick(R.id.btn_loby_menu)
    void onMenuClick() {
        popActivity(EtcActivity.class);
    }

    @Override
    public void setPercent(int percent) {
        if (percent == -1 || percent == -100) {
            tv_percent.setText("?__?");
            tv_percentDesc.setText("연필 모양을 눌러 썸포트를 작성해주세요!");
            return;
        }
        if (percent == -200) {
            tv_percent.setText("?__?");
            tv_percentDesc.setText("결과를 기다리고있어요!");
            return;
        }
        for (CheckBox checkBox : cbs_percents) {
            checkBox.setChecked(false);
        }
        Observable.interval(20, TimeUnit.MILLISECONDS)
                .take(percent)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    tv_percent.setText(d + "%");
                    cbs_percents.get(d.intValue() / 2).setChecked(true);
                });
        tv_percentDesc.setText("당신의 썸 성공확률은...");
    }

    @Override
    public void setPredictLove(List<ValueFormat> results){

    }

    @Override
    public void makeSelectSurveyDialog(List<Survey> surveys) {
        SurveySelectDialog dialog = new SurveySelectDialog(this, surveys);
        dialog.show();
    }

    @Override
    public void setDate(String date) {
        tv_reportDate.setText(date);
    }

    @Override
    public void makeAlertDialg(String message) {
        new STGAlertDialog(this, message).show();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backButtonMills < 3000) finish();
        backButtonMills = System.currentTimeMillis();
        Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
