package com.lovepago.ssumtago.Presentation.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.lovepago.ssumtago.Domain.ResourceUtil;
import com.lovepago.ssumtago.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TestViewActivity extends AppCompatActivity {
    @BindView(R.id.test_toggle)
    TextView tv_toggle;
    @BindView(R.id.testFoldingCell)
    ExpandableRelativeLayout test_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        ButterKnife.bind(this);
        tv_toggle.setOnClickListener(v->test_layout.toggle());

        LineChart lineChart = (LineChart)findViewById(R.id.testChart);
        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0,2));
        entries.add(new Entry(1,5));
        entries.add(new Entry(2,9));
        entries.add(new Entry(3,10));
        entries.add(new Entry(4,7));

        LineDataSet lineDataSet = new LineDataSet(entries,"datas");
        lineDataSet.setCircleColor(ResourceUtil.getColor(this,R.color.blank));
        lineDataSet.setDrawFilled(true);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.chart_orange_gradient));
        lineChart.setData(lineData);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGridColor(ResourceUtil.getColor(this,R.color.white));
        lineChart.getXAxis().setGridLineWidth(2f);
        lineChart.getAxisLeft().setDrawGridLines(true);
        lineChart.getAxisLeft().setGridLineWidth(2f);
        lineChart.getAxisLeft().setGridColor(ResourceUtil.getColor(this,R.color.white));
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setDrawGridBackground(true);
        lineChart.setDrawingCacheBackgroundColor(ResourceUtil.getColor(this,R.color.white));
        lineChart.invalidate();
    }

}
