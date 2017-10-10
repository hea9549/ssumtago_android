package com.lovepago.ssumtago.Presentation.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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


public class TestViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);
        FoldingCell foldingCell = (FoldingCell)findViewById(R.id.testFoldingCell);
        LineChart lineChart = (LineChart)findViewById(R.id.testChart);
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });


        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0,2));
        entries.add(new Entry(1,5));
        entries.add(new Entry(2,9));
        entries.add(new Entry(3,10));
        entries.add(new Entry(4,7));

        LineDataSet lineDataSet = new LineDataSet(entries,"datas");
        lineDataSet.setCircleColor(ResourceUtil.getColor(this,R.color.ssumtago_orange));
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(getResources().getDrawable(R.drawable.background_orange_gradient));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setData(lineData);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGridColor(ResourceUtil.getColor(this,R.color.white));
        lineChart.getAxisLeft().setGridColor(ResourceUtil.getColor(this,R.color.white));
        lineChart.invalidate();
    }

}
