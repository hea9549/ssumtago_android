package com.lovepago.ssumtago.Presentation.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lovepago.ssumtago.Data.Model.Answer;
import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.RequestObject;
import com.lovepago.ssumtago.R;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-05-28.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final int TYPE_SINGLE = 0;
    public final int TYPE_MULTI = 1;
    public final int TYPE_OPEN_ENDED = 2;
    private List<Question> questions;
    private RequestAnswer requestAnswer = new RequestAnswer();
    private Context context;
    public HomeRecyclerAdapter(List<Question> questions, Context context){
        this.questions = questions;
        this.context = context;
        for(int i = 0 ; i<questions.size();i++){
            RequestObject object = new RequestObject();
            object.setQuestionCode(questions.get(i).getCode());
            requestAnswer.getData().add(object);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.survey_single_item, parent, false);
        switch (viewType){
            case TYPE_SINGLE:
                view = inflater.inflate(R.layout.survey_single_item,parent,false);
                return new QuestionSingleHolder(view);
            case TYPE_MULTI:
                return new QuestionMultiHolder(inflater.inflate(R.layout.survey_multi_item,parent,false));
            case TYPE_OPEN_ENDED:
                return new QuestionOpenEndedHolder(inflater.inflate(R.layout.survey_open_ended_item,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Question question = questions.get(position);
        Log.e("adapter","뷰타입은 ? 포지션 = "+position+", 뷰타입 : "+getItemViewType(position));
        switch (getItemViewType(position)){
            case TYPE_SINGLE:
                QuestionSingleHolder singleHolder = (QuestionSingleHolder)holder;
                singleHolder.tv_questionName.setText(question.getName().get(0).getContent());
                if(singleHolder.rg_answers.getChildCount() != 0)singleHolder.rg_answers.removeAllViews();
                for(int i = 0 ;i<question.getAnswers().size();i++){
                    Answer answer = question.getAnswers().get(i);
                    RadioButton rb = new RadioButton(context);
                    RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,60);
                    params.setMargins(0,10,0,10);
                    rb.setLayoutParams(params);
                    rb.setText(question.getAnswers().get(i).getName().get(0).getContent());
                    rb.setOnClickListener(v->requestAnswer.getData().get(position).setAnswerCode(answer.getCode()));
                    singleHolder.rg_answers.addView(rb);
                }
                break;
            case TYPE_MULTI:
                QuestionMultiHolder multiHolder = (QuestionMultiHolder) holder;
                multiHolder.tv_questionName.setText(question.getName().get(0).getContent());
                if(multiHolder.wrapper_answer.getChildCount() != 0)multiHolder.wrapper_answer.removeAllViews();
                for(int i =0 ;i<question.getAnswers().size();i++){
                    CheckBox cb = new CheckBox(context);
                    Answer answer = question.getAnswers().get(i);
                    RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,60);
                    params.setMargins(0,10,0,10);
                    cb.setLayoutParams(params);
                    cb.setText(question.getAnswers().get(i).getName().get(0).getContent());
                    cb.setOnClickListener(v->{
                        requestAnswer.getData().get(position).setAnswerCode(answer.getCode());
                    });
                    multiHolder.wrapper_answer.addView(cb);
                }
                break;
            case TYPE_OPEN_ENDED:
                QuestionOpenEndedHolder openEndedHolder = (QuestionOpenEndedHolder)holder;
                openEndedHolder.tv_questionName.setText(question.getName().get(0).getContent());
                openEndedHolder.edt_answer.setOnFocusChangeListener((v,focus)->{
                    if(openEndedHolder.edt_answer.getText().toString()!= null){
                        int input = -1;
                        String inputString = "999";
                        input = Integer.valueOf(openEndedHolder.edt_answer.getText().toString());
                        if(0<input && input<10){
                            inputString = "00"+String.valueOf(input);
                        }else if(10<=input && input< 100){
                            inputString = "0"+String.valueOf(input);
                        }else if(100<=input && input < 1000){
                            inputString = String.valueOf(input);
                        }
                        String code = question.getAnswers().get(0).getCode().substring(0,5)+inputString;
                        requestAnswer.getData().get(position).setAnswerCode(code);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        String answerType = questions.get(position).getCode().substring(3,4);
        String multiType = questions.get(position).getCode().substring(6,7);
        Log.e("type","앤써타입 = "+answerType+"멀티타입은 ? ="+multiType);
        if (answerType.equals("1"))return TYPE_OPEN_ENDED;
        if (multiType.equals("2"))return TYPE_SINGLE;
        if (multiType.equals("1"))return TYPE_MULTI;
        return TYPE_SINGLE;
    }

    private static class QuestionSingleHolder extends RecyclerView.ViewHolder {
        TextView tv_questionName;
        RadioGroup rg_answers;
        public QuestionSingleHolder(View itemView) {
            super(itemView);
            tv_questionName = (TextView)itemView.findViewById(R.id.tv_surveySingleItem_question);
            rg_answers = (RadioGroup)itemView.findViewById(R.id.rg_surveySingleItem_answerGroup);
        }
    }
    public RequestAnswer getRequestAnswer(){
        return requestAnswer;
    }

    private static class QuestionMultiHolder extends RecyclerView.ViewHolder {
        TextView tv_questionName;
        LinearLayout wrapper_answer;
        public QuestionMultiHolder(View itemView) {
            super(itemView);
            tv_questionName = (TextView)itemView.findViewById(R.id.tv_surveyMultiItem_question);
            wrapper_answer = (LinearLayout)itemView.findViewById(R.id.wrapper_surveyMultiItem_answers);
        }
    }
    private static class QuestionOpenEndedHolder extends RecyclerView.ViewHolder {
        TextView tv_questionName;
        EditText edt_answer;
        public QuestionOpenEndedHolder(View itemView) {
            super(itemView);
            tv_questionName = (TextView)itemView.findViewById(R.id.tv_surveyOpenEnded_question);
            edt_answer = (EditText)itemView.findViewById(R.id.edt_surveyOpenEnded_answer);
        }
    }


}
