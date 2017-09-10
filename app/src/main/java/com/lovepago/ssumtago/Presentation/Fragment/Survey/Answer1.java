package com.lovepago.ssumtago.Presentation.Fragment.Survey;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lovepago.ssumtago.Data.Model.Answer;
import com.lovepago.ssumtago.Presentation.Activity.SurveyActivity;
import com.lovepago.ssumtago.R;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by ParkHaeSung on 2017-08-08.
 */

public class Answer1 extends Fragment implements AnswerInterface {
    @BindView(R.id.btn_answer1_1)
    Button btn_answer;
    private Answer answer;
    private String selectAnswerCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer1, container, false);
        ButterKnife.bind(this, view);
        btn_answer.setOnClickListener(v -> {
            showNumberPicker();
        });
        return view;
    }

    private void showNumberPicker() {
        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog);
        Button btn_set = (Button) d.findViewById(R.id.btn_numberPickerDialog_set);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_answer.setText(String.valueOf(np.getValue())); //set the value to textview
                selectAnswerCode = "020250"+np.getValue();
                ((SurveyActivity)getActivity()).setNextEnabled(true);
                d.dismiss();
                ((SurveyActivity)getActivity()).onNextClick();
            }
        });
        d.show();
    }

    @Override
    public String getSelectedAnswerCode() {
        return selectAnswerCode;
    }

    @Override
    public void setAnswers(List<Answer> answers) {
        this.answer = answers.get(0);
        btn_answer.setText("나이를 입력해주세요");
    }


    @Override
    public void setSelectedAnswer(String answerCode) {
        if (answerCode == null)return;
        btn_answer.setText(""+Integer.valueOf(answerCode.substring(5)));
        ((SurveyActivity)getActivity()).setNextEnabled(true);
    }
}
