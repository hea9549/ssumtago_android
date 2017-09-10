package com.lovepago.ssumtago.Presentation.Fragment.Survey;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lovepago.ssumtago.Data.Model.Answer;
import com.lovepago.ssumtago.Presentation.Activity.SurveyActivity;
import com.lovepago.ssumtago.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by ParkHaeSung on 2017-08-08.
 */

public class Answer3 extends Fragment implements AnswerInterface{
    @BindViews({R.id.btn_answer3_1,R.id.btn_answer3_2,R.id.btn_answer3_3})
    List<Button> btn_answers;
    private List<Answer> answers;
    private String selectAnswerCode;
    private long clickMills;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer3,container,false);
        ButterKnife.bind(this,view);
        for (Button btn : btn_answers) {
            btn.setOnClickListener(v -> {
                if (System.currentTimeMillis()-clickMills<500)return;
                clickMills= System.currentTimeMillis();
                buttonSelect((Button) v);
                ((SurveyActivity) getActivity()).onNextClick();
            });
        }
        return view;
    }

    private void buttonSelect(Button button) {
        for (int i = 0; i < btn_answers.size(); i++) {
            if (btn_answers.get(i) == button)
                selectAnswerCode = answers.get(i).getCode();
            btn_answers.get(i).setEnabled(true);
        }
        button.setEnabled(false);
        ((SurveyActivity)getActivity()).setNextEnabled(true);
    }

    @Override
    public String getSelectedAnswerCode() {
        return selectAnswerCode;
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
        for(int i = 0 ; i < answers.size();i++){
            btn_answers.get(i).setText(answers.get(i).getName().get(0).toString());
            btn_answers.get(i).setEnabled(true);
        }
    }
    @Override
    public void setSelectedAnswer(String answerCode) {
        if (answerCode==null)return;
        for(int i = 0; i < answers.size();i++){
            if (answerCode.equals(answers.get(i).getCode())){
                buttonSelect(btn_answers.get(i));
                break;
            }
        }
    }
}
