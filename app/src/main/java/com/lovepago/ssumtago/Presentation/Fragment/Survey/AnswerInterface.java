package com.lovepago.ssumtago.Presentation.Fragment.Survey;

import com.lovepago.ssumtago.Data.Model.Answer;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-08-10.
 */

public interface AnswerInterface {
    String getSelectedAnswerCode();
    void setAnswers(List<Answer> answers);

    void setSelectedAnswer(String answerCode);
}
