package com.lovepago.ssumtago.Presentation.Activity;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lovepago.ssumtago.Adapter.SelectDateAdapter;
import com.lovepago.ssumtago.CustomClass.CustomView.STGAlertDialog;
import com.lovepago.ssumtago.CustomClass.CustomView.SurveySelectDialog;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.Model.ValueFormat;
import com.lovepago.ssumtago.Domain.ResourceUtil;
import com.lovepago.ssumtago.Presentation.Presenter.LobyActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Util.SizeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class LobyActivity extends STGBaseActivity implements LobyActivityPresenter.View {
    @Inject
    LobyActivityPresenter presenter;
    @BindViews({R.id.cb_percent_1, R.id.cb_percent_2, R.id.cb_percent_3, R.id.cb_percent_4, R.id.cb_percent_5, R.id.cb_percent_6, R.id.cb_percent_7, R.id.cb_percent_8, R.id.cb_percent_9, R.id.cb_percent_10, R.id.cb_percent_11, R.id.cb_percent_12, R.id.cb_percent_13, R.id.cb_percent_14, R.id.cb_percent_15, R.id.cb_percent_16, R.id.cb_percent_17, R.id.cb_percent_18, R.id.cb_percent_19, R.id.cb_percent_20, R.id.cb_percent_21, R.id.cb_percent_22, R.id.cb_percent_23, R.id.cb_percent_24, R.id.cb_percent_25, R.id.cb_percent_26, R.id.cb_percent_27, R.id.cb_percent_28, R.id.cb_percent_29, R.id.cb_percent_30, R.id.cb_percent_31, R.id.cb_percent_32, R.id.cb_percent_33, R.id.cb_percent_34, R.id.cb_percent_35, R.id.cb_percent_36, R.id.cb_percent_37, R.id.cb_percent_38, R.id.cb_percent_39, R.id.cb_percent_40, R.id.cb_percent_41, R.id.cb_percent_42, R.id.cb_percent_43, R.id.cb_percent_44, R.id.cb_percent_45, R.id.cb_percent_46, R.id.cb_percent_47, R.id.cb_percent_48, R.id.cb_percent_49, R.id.cb_percent_50})
    List<CheckBox> cbs_percents;
    @BindView(R.id.tv_loby_percent)
    TextView tv_percent;
    @BindView(R.id.tv_loby_date)
    Spinner spn_reportDate;
    @BindView(R.id.tv_loby_percentDesc)
    TextView tv_percentDesc;
    @BindView(R.id.wrapper_loby_expandablePercent)
    ExpandableRelativeLayout wrapper_expandablePercent;
    @BindView(R.id.wrapper_loby_expandablePredictLove)
    ExpandableRelativeLayout wrapper_expandablePredictLove;
    @BindView(R.id.wrapper_loby_successPercentTotal)
    RelativeLayout wrapper_successPercentTotal;
    @BindView(R.id.wrapper_loby_predictLoveTotal)
    RelativeLayout wrapper_predictLoveTotal;
    @BindView(R.id.wrapper_loby_predictLove)
    RelativeLayout wrapper_predictLove;
    @BindView(R.id.chart_loby_predictLove)
    LineChart chart_predictLove;
    @BindView(R.id.tv_loby_firstPredictLoveDate)
    TextView tv_firstPredictLoveDate;
    @BindView(R.id.tv_loby_lastPredictLoveDate)
    TextView tv_lastPredictLoveDate;
    @BindView(R.id.tv_loby_dashPredictLove)
    TextView tv_predictLoveDateDash;
    @BindView(R.id.tv_loby_predictLoveDateDesc)
    TextView tv_predictLoveDateDesc;
    @BindView(R.id.img_loby_percentDropDown)
    ImageView img_percentDropDown;
    @BindView(R.id.img_loby_predictDropDown)
    ImageView img_predictDropDown;

    private long backButtonMills;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_loby);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
        wrapper_expandablePredictLove.collapse(0, null);
        wrapper_expandablePercent.collapse(0,null);
        wrapper_expandablePercent.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                super.onPreOpen();
                img_percentDropDown.setImageResource(R.drawable.expandable_view_arrow_up);
                presenter.onPercentOpened();
            }

            @Override
            public void onPreClose() {
                super.onPreClose();
                img_percentDropDown.setImageResource(R.drawable.expandable_view_arrow_down);
            }
        });
        wrapper_expandablePredictLove.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                super.onPreOpen();
                presenter.onPredictLoveOpened();
                img_predictDropDown.setImageResource(R.drawable.expandable_view_arrow_up);
            }

            @Override
            public void onPreClose() {
                super.onPreClose();
                img_predictDropDown.setImageResource(R.drawable.expandable_view_arrow_down);
            }
        });
        wrapper_successPercentTotal.setOnClickListener(v->wrapper_expandablePercent.toggle());
        wrapper_predictLove.setOnClickListener(v->wrapper_expandablePredictLove.toggle());
        ViewCompat.setElevation(wrapper_predictLoveTotal, SizeUtil.convertDpToPixel(this,10));
        ViewCompat.setElevation(wrapper_successPercentTotal, SizeUtil.convertDpToPixel(this,10));
    }



    @OnClick(R.id.btn_loby_addReport)
    void onAddReportClick() {
        presenter.onAddReportClick();
    }

    @OnClick(R.id.btn_loby_menu)
    void onMenuClick() {
        popActivity(EtcActivity.class);
    }

    @Override
    public void setPercent(int percent) {
        if (percent == -1 || percent == -100) {
            tv_percent.setText("?__?");
            tv_percentDesc.setText("연필 모양을 눌러 썸포트를 작성해주세요!");
            return;
        }
        if (percent == -200) {
            tv_percent.setText("?__?");
            tv_percentDesc.setText("결과를 기다리고있어요!");
            return;
        }
        for (CheckBox checkBox : cbs_percents) {
            checkBox.setChecked(false);
        }
        Observable.interval(20, TimeUnit.MILLISECONDS)
                .take(percent)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> {
                    tv_percent.setText(d + "%");
                    cbs_percents.get(d.intValue() / 2).setChecked(true);
                });
    }

    @Override
    public void setPredictLove(List<ValueFormat> results){
        List<Entry> entries = new ArrayList<Entry>();
        List<String> xLabel = new ArrayList<>();
        ValueFormat max = new ValueFormat("default",0);
        for(ValueFormat v : results) if (v.getValue()>max.getValue())max = v;
        String numbers = max.getLabel().replaceAll("[^0-9]", "");
        if (numbers.isEmpty())return;
        if (numbers.length() == 2){
            tv_firstPredictLoveDate.setVisibility(View.VISIBLE);
            tv_predictLoveDateDash.setVisibility(View.VISIBLE);
            tv_firstPredictLoveDate.setText(""+numbers.charAt(0));
            tv_lastPredictLoveDate.setText(""+numbers.charAt(1));
            tv_predictLoveDateDesc.setText("번 째 만남");
        }else if (numbers.length() == 1){
            tv_predictLoveDateDash.setVisibility(View.GONE);
            tv_firstPredictLoveDate.setVisibility(View.GONE);
            tv_lastPredictLoveDate.setText(numbers);
            tv_predictLoveDateDesc.setText("번 째 만남 이상..");
        }

        for (int i = 0 ; i < results.size();i++){
            entries.add(new Entry(i*2,(float)results.get(i).getValue()));
            xLabel.add(""+(i*2+1));
            xLabel.add(""+(i*2+2));
        }
        LineDataSet lineDataSet = new LineDataSet(entries,"datas");
        lineDataSet.setCircleColor(ResourceUtil.getColor(this,R.color.blank));
        lineDataSet.setDrawFilled(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineDataSet.setColor(ResourceUtil.getColor(this,R.color.white));
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.chart_orange_gradient));
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        chart_predictLove.setData(lineData);
        chart_predictLove.getAxisRight().setEnabled(false);
        chart_predictLove.getAxisLeft().setEnabled(true);
        chart_predictLove.getAxisLeft().setTextColor(ResourceUtil.getColor(this,R.color.blank));
        chart_predictLove.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_predictLove.getXAxis().setGridLineWidth(1.5f);
        chart_predictLove.getXAxis().setGridColor(ResourceUtil.getColor(this,R.color.ssumtago_white_gray));
        chart_predictLove.getDescription().setEnabled(false);
        chart_predictLove.getXAxis().setAvoidFirstLastClipping(true);
        chart_predictLove.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value);
            }
        });

        chart_predictLove.getAxisLeft().setDrawGridLines(true);
        chart_predictLove.getAxisLeft().setGridLineWidth(1.5f);
        chart_predictLove.getAxisLeft().setGridColor(ResourceUtil.getColor(this,R.color.ssumtago_white_gray));
        chart_predictLove.getXAxis().setTextColor(Color.rgb(229,229,229));
        chart_predictLove.getXAxis().setTextSize(14);
        chart_predictLove.setDrawGridBackground(true);
        chart_predictLove.setEnabled(false);
        chart_predictLove.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        chart_predictLove.setDrawingCacheBackgroundColor(ResourceUtil.getColor(this,R.color.white));
        chart_predictLove.getLegend().setEnabled(false);
        chart_predictLove.invalidate();
        chart_predictLove.setGridBackgroundColor(ResourceUtil.getColor(this,R.color.blank));
    }

    @Override
    public void makeSelectSurveyDialog(List<Survey> surveys) {
        SurveySelectDialog dialog = new SurveySelectDialog(this, surveys);
        dialog.show();
    }

    @Override
    public void setSpinner(List<PredictReport> reports){
        SelectDateAdapter adapter =new SelectDateAdapter(this,reports,R.layout.support_simple_spinner_dropdown_item);
        spn_reportDate.setAdapter(adapter);
        spn_reportDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wrapper_expandablePercent.collapse(0,null);
                wrapper_expandablePredictLove.collapse(0,null);
                presenter.onPredictClick(adapter.getReport(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void makeAlertDialg(String message) {
        new STGAlertDialog(this, message).show();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backButtonMills < 3000) finish();
        backButtonMills = System.currentTimeMillis();
        Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
