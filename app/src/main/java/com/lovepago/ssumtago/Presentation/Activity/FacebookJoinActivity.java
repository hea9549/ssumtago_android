package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.ResourceUtil;
import com.lovepago.ssumtago.Presentation.Presenter.FacebookJoinActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class FacebookJoinActivity extends STGBaseActivity implements FacebookJoinActivityPresenter.View {
    @Inject
    FacebookJoinActivityPresenter presenter;
    @BindView(R.id.edt_facebookJoin_nick)
    TextView tv_nick;
    @BindView(R.id.btn_facebookJoin_male)
    Button btn_male;
    @BindView(R.id.btn_facebookJoin_female)
    Button btn_female;
    @BindView(R.id.tv_facebookJoin_birthday)
    TextView tv_birthday;
    @BindView(R.id.btn_facebookJoin_submit)
    Button btn_submit;

    private String accountId;
    private String token;
    private String sex;
    private String birthday;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_facebook_join);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        getIntentData();
        presenter.setView(this);
    }

    @Override
    public void joinSuccess() {
        navigateActivity(StartGuideActivity.class);
    }

    private void getIntentData() {
        accountId = getIntent().getStringExtra("accountId");
        token = getIntent().getStringExtra("token");
    }

    @OnClick(R.id.btn_facebookJoin_submit)
    void onSubmitClick() {
        if (sex == null) {
            makeToast("성별을 선택해 주세요");
            return;
        }
        if (birthday == null) {
            makeToast("생일을 입력해 주세요");
            return;
        }
        if (tv_nick.getText().toString().isEmpty()) {
            makeToast("닉네임을 입력해 주세요");
            return;
        }
        presenter.register(accountId, token, tv_nick.getText().toString(), sex, birthday);
    }

    @OnClick(R.id.btn_facebookJoin_male)
    void onMaleClick() {
        btn_male.setEnabled(false);
        btn_female.setEnabled(true);
        sex = "man";
    }

    @OnClick(R.id.btn_facebookJoin_female)
    void onFemaleClick() {
        btn_female.setEnabled(false);
        btn_male.setEnabled(true);
        sex = "woman";
    }

    @OnClick(R.id.tv_facebookJoin_birthday)
    void onBirthdayClick() {
        TimePickerView birthdayPicker = new TimePickerView.Builder(this, (date, view) -> {
            SimpleDateFormat viewBirthdayFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
            SimpleDateFormat dataBirthdayFormat = new SimpleDateFormat("yyyyMMdd");
            tv_birthday.setText(viewBirthdayFormat.format(date));
            birthday = dataBirthdayFormat.format(date);
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("취소")
                .setSubmitText("선택")
                .setLabel("년", "월", "일", "시", "분", "초")
                .setOutSideCancelable(false)
                .build();
        birthdayPicker.setDate(Calendar.getInstance());
        birthdayPicker.show();
    }

    @OnCheckedChanged(R.id.cb_facebookJoin_privacyPolicy)
    void onPrivacyCheckChange(boolean isChecked) {
        if (isChecked) {
            btn_submit.setTextColor(ResourceUtil.getColor(this, R.color.ssumtago_orange));
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setTextColor(ResourceUtil.getColor(this, R.color.ssumtago_gray));
            btn_submit.setEnabled(false);
        }
    }
}
