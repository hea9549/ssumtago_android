package com.lovepago.ssumtago.CustomClass.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lovepago.ssumtago.Presentation.Activity.LobyActivity;
import com.lovepago.ssumtago.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public class STGLoadingDialog extends Dialog {

    private Context context;
    private String content;

    public STGLoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_dialog);
        if (getWindow()!=null){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        }
        setCancelable(false);
    }

}
