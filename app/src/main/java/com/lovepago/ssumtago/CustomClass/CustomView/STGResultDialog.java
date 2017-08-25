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

public class STGResultDialog extends Dialog {

    @BindView(R.id.btn_resultDialog_getResult)
    Button btn_getResult;

    @BindView(R.id.tv_resultDialog_content)
    TextView tv_content;

    private Context context;
    private String content;

    public STGResultDialog(Context context, String content) {
        super(context);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_result_report);
        if (getWindow()!=null){
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        }
        ButterKnife.bind(this);
        if (content != null)
            tv_content.setText(content);
        btn_getResult.setOnClickListener(v -> {
            Intent intent = new Intent(context, LobyActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
            this.dismiss();
        });
    }

}
