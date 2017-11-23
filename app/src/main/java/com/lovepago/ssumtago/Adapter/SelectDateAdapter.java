package com.lovepago.ssumtago.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.lovepago.ssumtago.Data.Model.PredictReport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ParkHaeSung on 2017-11-04.
 */

public class SelectDateAdapter extends ArrayAdapter<String>{
    private List<PredictReport> reports;
    private Context context;

    public SelectDateAdapter(Context context, List<PredictReport> reports,int resourceId){
        super(context,resourceId);
        this.context = context;
        this.reports = reports;
        for(int i = 0 ;i<reports.size();i++)add(getDateString(i));
        notifyDataSetInvalidated();
    }

    public PredictReport getReport(int position){
        return reports.get(position);
    }

    public String getDateString(int position){
        PredictReport predictReport = getReport(position);
        SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String strDate = predictReport.getRequestTime();
        try {
            Date responseDate = serverDateFormat.parse(strDate);
            SimpleDateFormat viewDate = new SimpleDateFormat("yyyy.MM.dd");
            return viewDate.format(responseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
