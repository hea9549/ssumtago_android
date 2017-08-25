package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lovepago.ssumtago.CustomClass.CustomView.STGHorizontalPicker;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetSsumActivity extends STGBaseActivity{

    @BindView(R.id.view_addSsum_agePicker)
    STGHorizontalPicker view_agePicker;

    String[] ageValue = new String[50];

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_set_ssum);
        ButterKnife.bind(this);
        for(int i = 0 ; i<50;i++)ageValue[i]=""+i;
        view_agePicker.setValues(ageValue);
        view_agePicker.setOnItemSelectedListener(new STGHorizontalPicker.OnItemSelected(){
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(SetSsumActivity.this, ""+index+":"+ageValue[index], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
