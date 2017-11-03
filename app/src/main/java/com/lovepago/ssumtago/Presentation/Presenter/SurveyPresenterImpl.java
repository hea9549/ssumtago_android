package com.lovepago.ssumtago.Presentation.Presenter;

import android.content.Context;
import android.util.Log;

import com.lovepago.ssumtago.CustomClass.STGPreference;
import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Data.Model.RealmDouble;
import com.lovepago.ssumtago.Data.Model.RealmString;
import com.lovepago.ssumtago.Data.Model.ResultFormat;
import com.lovepago.ssumtago.Data.Model.SsumJi;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.Data.Model.ValueFormat;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;

public class SurveyPresenterImpl implements SurveyPresenter {
    private static final String TAG = "SurveyPresenter";
    private Context context;
    private UserService userService;
    private SurveyService surveyService;
    private View view;
    private Survey survey;
    private List<Question> questions;
    private int questionIndex;
    private PredictReport predictReport;
    private Date startDate;
    private Date endDate;
    private STGPreference preference;

    @Inject
    public SurveyPresenterImpl(SurveyService surveyService, UserService userService, Context context) {
        this.surveyService = surveyService;
        this.userService = userService;
        this.context = context;
        this.preference = new STGPreference(context);
    }

    @Override
    public void setView(View view, int surveyId) {
        this.view = view;
        startDate = new Date();
        surveyService.getSurveyById(surveyId)
                .subscribe(surv -> {
                            this.survey = surv;
                            this.questions = survey.getQuestions();
                            // 설문 질문목록에서 excludeCode 질문 제외
                            for (int i = 0; i < questions.size(); i++) {
                                //첫 설문아닐경우 나이 (25번), 성별(26번) 제외
                                if (userService.getUser().isSurveyedYN() && (
                                        questions.get(i).getCode().equals("01010000025") || questions.get(i).getCode().equals("01000120026"))){
                                    questions.remove(i);
                                    i--;
                                    continue;
                                }
                                for (RealmString excludeCode : survey.getExcludeCodes()) {
                                    if (excludeCode.toString().equals(questions.get(i).getCode())) {
                                        questions.remove(i);
                                        i--;
                                        break;
                                    }
                                }
                            }
                            questionIndex = 0;
                            predictReport = new PredictReport();
                            predictReport.setData(new RealmList<>());
                            predictReport.setSurveyId(surveyId);
                            predictReport.setVersion(survey.getVersion());
                            view.setQuestion(survey.getQuestions().get(0));
                        }, error -> {
                            Log.e(TAG, "error in setView = " + error.getMessage());
                            error.printStackTrace();
                            view.makeToast("설문지를 받아오는데 실패했습니다.");
                        }
                );
        if (!userService.getUser().isSurveyedYN()) {
            view.setStartMessage(context.getString(R.string.survey_start_main_first), context.getString(R.string.survey_start_tip_first));
            view.setFinishMessage(context.getString(R.string.survey_finish_main_first), context.getString(R.string.survey_finish_tip_first));
        } else {
            view.setStartMessage(context.getString(R.string.survey_start_main_ssum), context.getString(R.string.survey_start_tip_ssum));
            view.setFinishMessage(context.getString(R.string.survey_finish_main_ssum), context.getString(R.string.survey_finish_tip_ssum));
        }
    }


    @Override
    public void onNextClick(String selectedAnswerCode) {
        //데이터 담을 객체 생성
        SsumJi ssumJi = new SsumJi();
        ssumJi.setQuestionCode(questions.get(questionIndex).getCode());
        ssumJi.setAnswerCode(selectedAnswerCode);

        //뒤로 갔다가 왔을대는 데이터를 추가하는게 아니라 교체해야함
        if (predictReport.getData().size() == questionIndex)
            predictReport.getData().add(questionIndex, ssumJi);
        else
            predictReport.getData().set(questionIndex, ssumJi);

        //인덱스 증가
        questionIndex++;

        //마지막 질문일경우 서베이 종료
        if (survey.getQuestions().size() == questionIndex) {
            view.finishSurvey();
            return;
        }

        // 뒤로갔다가 데이터있는곳으로 왔을때는 기존 데이터 채워서 보여줌
        if (predictReport.getData().size() > questionIndex)
            view.setQuestion(survey.getQuestions().get(questionIndex), predictReport.getData().get(questionIndex).getAnswerCode());
        else
            view.setQuestion(survey.getQuestions().get(questionIndex));

        // 프로그래스 (indicator) 값 변경
        view.progressbarValueChange(
                (float) (questionIndex - 1) / questions.size() * 100
                , (float) questionIndex / questions.size() * 100);
    }

    @Override
    public void onBeforeClick() {
        if (questionIndex == 0) return;
        questionIndex--;
        view.setQuestion(survey.getQuestions().get(questionIndex), predictReport.getData().get(questionIndex).getAnswerCode());
        view.progressbarValueChange(
                (float) (questionIndex + 1) / questions.size() * 100
                , (float) questionIndex / questions.size() * 100);
    }

    @Override
    public void onSubmitClick() {
        view.makeDialog("");
        endDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SZ");
        SimpleDateFormat prefFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        predictReport.setStartTime(simpleDateFormat.format(startDate));
        predictReport.setEndTime(simpleDateFormat.format(endDate));
        if(userService.getUser().isSurveyedYN()){
            //첫설문이 아닐경우 나이,성별 빼줫으므로 답변지에는 넣어서 전송
            SsumJi ageReport = new SsumJi();
            SsumJi sexReport = new SsumJi();
            ageReport.setQuestionCode("01010000025");
            sexReport.setQuestionCode("01000120026");
            //생일로 나이계산하기
            String strBirth = userService.getUser().getBirthday();
            SimpleDateFormat strBirthFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar calendar = Calendar.getInstance();
            int curYear = calendar.get(Calendar.YEAR);
            try {
                calendar.setTime(strBirthFormat.parse(strBirth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int age = curYear - calendar.get(Calendar.YEAR) + 1;
            ageReport.setAnswerCode("020250"+age);

            //성별 코드넣기
            if (userService.getUser().getSex().equals("man")){
                sexReport.setAnswerCode("02026001");
            }else{
                sexReport.setAnswerCode("02026002");
            }

            predictReport.getData().add(ageReport);
            predictReport.getData().add(sexReport);
        }
        Collections.sort(predictReport.getData(), (e1, e2) -> Integer.valueOf(e1.getQuestionCode().substring(8)) - Integer.valueOf(e2.getQuestionCode().substring(8)));
        if (!userService.getUser().isSurveyedYN()) {
            surveyService.submitStartReport(predictReport)
                    .doOnError(e->view.cancelDialog())
                    .doOnNext(n->view.cancelDialog())
                    .subscribe(response -> {
                        view.submitFinish();
                        User user = userService.getUser();
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        user.setSurveyedYN(true);
                        realm.commitTransaction();
                    }, fail -> Log.e(TAG, "fail in submit previous report. error = " + fail.toString()));
        } else {
            surveyService.submitReport(predictReport)
                    .doOnError(e->view.cancelDialog())
                    .doOnNext(n->view.cancelDialog())
                    .subscribe(response -> {
                        view.submitFinish();
                        User user = userService.getUser();
                        response.setResults(new RealmList<>());
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        user.getPredictReports().add(response);
                        realm.commitTransaction();
                        preference.put(STGPreference.PREF_LAST_SURVEYED,prefFormat.format(endDate));
                    }, fail -> Log.e(TAG, "fail in submit report. error = " + fail.getMessage()));
        }
    }

    @Override
    public boolean isUserSruveyYN() {
        return userService.getUser().isSurveyedYN();
    }
}
