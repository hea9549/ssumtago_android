package com.lovepago.ssumtago.CustomClass.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepago.ssumtago.Adapter.SelectSurveyAdapter;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Presentation.Activity.SurveyActivity;
import com.lovepago.ssumtago.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public class STGAlertDialog extends Dialog {

    @BindView(R.id.btn_stgAlertDialog_accept)
    Button btn_accept;

    @BindView(R.id.tv_stgAlertDialog_content)
    TextView tv_content;

    private Context context;
    private String content;

    public STGAlertDialog(Context context, String content) {
        super(context);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_stg_basic_alert);
        ButterKnife.bind(this);
        if (getWindow()!=null)
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tv_content.setText(content);
        btn_accept.setOnClickListener(v->this.dismiss());
    }
}
