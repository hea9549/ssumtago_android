package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Presentation.Adapter.HomeRecyclerAdapter;
import com.lovepago.ssumtago.Presentation.Presenter.HomeActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends STGBaseActivity implements HomeActivityPresenter.View{
    @Inject
    HomeActivityPresenter presenter;

    @BindView(R.id.rv_home_questions)
    RecyclerView rv_questions;
    @BindView(R.id.btn_home_submit)
    Button btn_submit;
    private HomeRecyclerAdapter adapter;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
        presenter.init();
        initButtonAction();
    }

    @Override
    public void setList(List<Question> questions){
        adapter = new HomeRecyclerAdapter(questions,this);
        rv_questions.setAdapter(adapter);
    }

    private void initButtonAction(){
        btn_submit.setOnClickListener(v->presenter.onSubmitClick(adapter.getRequestAnswer()));
    }
}
