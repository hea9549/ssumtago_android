package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lovepago.ssumtago.CustomClass.ProgressBarAnimation;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer1;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer2;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer3;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer4;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer5;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer6;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer7;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.Answer8;
import com.lovepago.ssumtago.Presentation.Fragment.Survey.AnswerInterface;
import com.lovepago.ssumtago.Presentation.Presenter.SurveyPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * @see #getIntentData() Intent로 필수로 넘겨줘야되는 것들
 * */
public class SurveyActivity extends STGBaseActivity implements SurveyPresenter.View {
    private static final String TAG = "SurveyActivity";
    private long submitTime;
    @BindView(R.id.wrapper_survey_answer)
    FrameLayout wrapper_answer;
    @BindView(R.id.btn_survey_next)
    ImageButton btn_next;
    @BindView(R.id.btn_survey_before)
    ImageButton btn_before;
    @BindView(R.id.tv_survey_question)
    TextView tv_question;
    @BindView(R.id.tv_survey_section)
    TextView tv_section;
    @BindView(R.id.progress_survey_progress)
    ProgressBar view_progress;
    @BindView(R.id.wrapper_survey_total)
    ConstraintLayout wrapper_total;
    @BindView(R.id.wrapper_survey_finish1)
    ConstraintLayout wrapper_finish1;
    @BindView(R.id.wrapper_survey_finish2)
    ConstraintLayout wrapper_finish2;
    @BindView(R.id.wrapper_survey_start)
    ConstraintLayout wrapper_start;
    @BindView(R.id.tv_survey_finishMain)
    TextView tv_finishMain;
    @BindView(R.id.tv_survey_finishTip)
    TextView tv_finishTip;
    @BindView(R.id.tv_survey_startMain)
    TextView tv_startMain;
    @BindView(R.id.tv_survey_startTip)
    TextView tv_startTip;

    @Inject
    SurveyPresenter presenter;
    private int surveyId;
    private int answerNum;
    private List<AnswerInterface> answerInterfaces;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_survey);
        STGApplication.getComponent().inject(this);
        ButterKnife.bind(this);
        getIntentData();
        btn_before.setEnabled(false);
        btn_next.setEnabled(false);
        answerInterfaces = new ArrayList<>();
        answerInterfaces.add(new Answer1());
        answerInterfaces.add(new Answer2());
        answerInterfaces.add(new Answer3());
        answerInterfaces.add(new Answer4());
        answerInterfaces.add(new Answer5());
        answerInterfaces.add(new Answer6());
        answerInterfaces.add(new Answer7());
        answerInterfaces.add(new Answer8());
        presenter.setView(this,surveyId);
    }

    /**
     * @implNote 인텐트로 넘겨줘야할 것
     *          <br/>surveyId(int) - 서베이 아이디
     * */
    private void getIntentData() {
        surveyId = getIntent().getIntExtra("surveyId",-1);
    }
    public void setNextEnabled(boolean enabled){
        btn_next.setEnabled(enabled);
    }

    @OnClick(R.id.btn_survey_next)
    public void onNextClick(){
        presenter.onNextClick(answerInterfaces.get(answerNum-1).getSelectedAnswerCode());
        btn_before.setEnabled(true);
    }

    @OnClick(R.id.btn_survey_before)
    public void onBeforeClick(){
        presenter.onBeforeClick();
    }

    @Override
    public void finishSurvey() {
        wrapper_finish1.setVisibility(View.VISIBLE);
    }

    @Override
    public void setQuestion(Question question) {
        setQuestion(question,null);
    }

    @Override
    public void setQuestion(Question question, String answerCode) {
        answerNum = question.getAnswers().size();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.wrapper_survey_answer, (Fragment)answerInterfaces.get(answerNum-1));
        ft.commitNowAllowingStateLoss();
        AnswerInterface answerInterface = answerInterfaces.get(answerNum-1);
        answerInterface.setAnswers(question.getAnswers());
        tv_question.setText(question.getName().get(0).toString());
        tv_section.setText(question.getCategory());
        btn_next.setEnabled(false);
        if (answerCode!=null){
            answerInterface.setSelectedAnswer(answerCode);
            btn_next.setEnabled(true);
        }
        Random random = new Random();
        wrapper_total.setBackgroundResource(getResources().getIdentifier("survey_back_" +(random.nextInt(24)+1), "drawable", getPackageName()));
    }

    @Override
    public void progressbarValueChange(float from, float to){
        Log.e(TAG,"in progress Change, to ->"+to);
        ProgressBarAnimation animation= new ProgressBarAnimation(view_progress,from,to);
        animation.setDuration(500);
        view_progress.startAnimation(animation);
    }

    @Override
    public void setFinishMessage(String mainMessage, String tipMessage) {
        tv_finishMain.setText(mainMessage);
        tv_finishTip.setText(tipMessage);
    }

    @Override
    public void setStartMessage(String mainMessage, String tipMessage) {
        tv_startMain.setText(mainMessage);
        tv_startTip.setText(tipMessage);
    }

    @Override
    public void submitFinish() {
        wrapper_finish2.setVisibility(View.VISIBLE);
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(d->{
                    navigateActivity(LobyActivity.class);
                });
    }

    @Override
    public void onBackPressed() {
        if(!presenter.isUserSruveyYN()){
            new AlertDialog.Builder(this)
                    .setTitle("종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setPositiveButton("예",((dialog, which) -> {
                        dialog.dismiss();
                        finish();
                    }))
                    .setNegativeButton("아니오",((dialog, which) -> dialog.dismiss()))
                    .create()
                    .show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("돌아가기")
                    .setMessage("메인화면으로 돌아가겠습니까?")
                    .setPositiveButton("예",((dialog, which) -> {
                        dialog.dismiss();
                        navigateActivity(LobyActivity.class);
                    }))
                    .setNegativeButton("아니오",((dialog, which) -> dialog.dismiss()))
                    .create()
                    .show();
        }
    }

    @OnClick(R.id.btn_survey_start)
    void onStartClick(){
        wrapper_start.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_survey_submit)
    void onSubmitClick(){
        long curTime = System.currentTimeMillis();
        if (curTime - submitTime<3000)return;
        submitTime = curTime;
        presenter.onSubmitClick();
    }

}
