package com.lovepago.ssumtago.Presentation.Presenter;

import com.lovepago.ssumtago.Data.Model.Question;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface SurveyPresenter {
    void setView(View view, int surveyId);

    void onNextClick(String selectedAnswerCode);

    void onBeforeClick();

    void onSubmitClick();

    boolean isUserSruveyYN();

    interface View extends BaseViewPresenter{
        void finishSurvey();
        void setQuestion(Question question);

        void setQuestion(Question question, String answerCode);


        void progressbarValueChange(float from, float to);

        void setFinishMessage(String mainMessage, String tipMessage);

        void setStartMessage(String mainMessage, String tipMessage);

        void submitFinish();
    }
}
