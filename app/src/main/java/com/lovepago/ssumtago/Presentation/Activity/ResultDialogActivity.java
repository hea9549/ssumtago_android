package com.lovepago.ssumtago.Presentation.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lovepago.ssumtago.CustomClass.CustomView.STGResultDialog;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.R;

public class ResultDialogActivity extends STGBaseActivity {
    STGResultDialog stgResultDialog;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_result_dialog);

        if (getWindow()!=null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(stgResultDialog==null){
            stgResultDialog= new STGResultDialog(this,null);
        }
        if(stgResultDialog.isShowing()){
            finish();
            return;
        }
        stgResultDialog.setOnDismissListener(dialog -> {
            this.finish();
        });
        stgResultDialog.show();
    }
}
