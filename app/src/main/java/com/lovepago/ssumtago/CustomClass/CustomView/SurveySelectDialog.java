package com.lovepago.ssumtago.CustomClass.CustomView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lovepago.ssumtago.Adapter.SelectSurveyAdapter;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Presentation.Activity.SurveyActivity;
import com.lovepago.ssumtago.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public class SurveySelectDialog extends Dialog {
    @BindView(R.id.view_selectSurvey_list)
    ListView view_list;
    @BindView(R.id.btn_selectSurvey_close)
    ImageButton btn_close;

    private Activity activity;
    private List<Survey> surveys;
    private SelectSurveyAdapter adapter;

    public SurveySelectDialog(Activity activity, List<Survey> surveys) {
        super(activity);
        this.activity = activity;
        this.surveys = surveys;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_survey);
        ButterKnife.bind(this);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adapter = new SelectSurveyAdapter(surveys, activity);
        view_list.setAdapter(adapter);
        view_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).getIsAvailable().equals("N")) return;
                Intent intent = new Intent(activity, SurveyActivity.class);
                intent.putExtra("surveyId", adapter.getItem(position).getSurveyId());
                activity.startActivity(intent);
                dismiss();
                activity.finish();
            }
        });
    }

    @OnClick(R.id.btn_selectSurvey_close)
    void onCloseclick() {
        if (this.isShowing())
            dismiss();
    }
}
