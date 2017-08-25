package com.lovepago.ssumtago.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Domain.ResourceUtil;
import com.lovepago.ssumtago.R;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public class SelectSurveyAdapter extends BaseAdapter {
    private List<Survey> surveys;
    private Context context;

    public SelectSurveyAdapter(List<Survey> surveyList, Context context) {
        this.surveys = surveyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return surveys.size();
    }

    @Override
    public Survey getItem(int position) {
        return surveys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_select_survey, parent, false);
        }
        TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item_surveyList);
        tv_item.setText(getItem(position).getDesc());
        if (getItem(position).getIsAvailable().equals("N"))
            tv_item.setTextColor(ResourceUtil.getColor(context, R.color.ssumtago_gray));

        return convertView;
    }


}
