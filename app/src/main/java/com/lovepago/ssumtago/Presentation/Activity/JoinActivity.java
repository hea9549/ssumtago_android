package com.lovepago.ssumtago.Presentation.Activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.ResourceUtil;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Presenter.JoinActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class JoinActivity extends STGBaseActivity implements JoinActivityPresenter.View {
    @Inject
    JoinActivityPresenter presenter;

    @BindView(R.id.edt_join_eamil)
    EditText edt_email;
    @BindView(R.id.edt_join_nick)
    EditText edt_nick;
    @BindView(R.id.edt_join_pw)
    EditText edt_pw;
    @BindView(R.id.edt_join_pw2)
    EditText edt_pw2;
    @BindView(R.id.btn_join_male)
    Button btn_male;
    @BindView(R.id.btn_join_female)
    Button btn_femail;
    @BindView(R.id.tv_join_birthday)
    TextView tv_birthday;
    @BindView(R.id.btn_join_submit)
    Button btn_submit;
    @BindView(R.id.wrapper_join_joinSuccess)
    RelativeLayout wrapper_joinSuccess;
    @BindView(R.id.wrapper_join_info)
    ConstraintLayout wrapper_info;
    @BindView(R.id.btn_join_close)
    ImageButton btn_close;
    @BindView(R.id.img_join_valid)
    ImageView img_valid;

    private String sex;
    private String birthday;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        presenter.setView(this);
    }

    @OnCheckedChanged(R.id.cb_join_privacyPolicy)
    void onPrivacyCheckChange(boolean isChecked){
        if(isChecked){
            btn_submit.setTextColor(ResourceUtil.getColor(this,R.color.ssumtago_orange));
            btn_submit.setEnabled(true);
        }else{
            btn_submit.setTextColor(ResourceUtil.getColor(this,R.color.ssumtago_gray));
            btn_submit.setEnabled(false);
        }
    }

    /**
     * onClick Implementaion >-<
     */
    @OnTextChanged(R.id.edt_join_pw)
    void onPw1Change(CharSequence s){
        if (edt_pw.getText().toString().isEmpty() || edt_pw2.getText().toString().isEmpty())return;
        if (edt_pw.getText().toString().equals(edt_pw2.getText().toString())){
            img_valid.setImageResource(R.drawable.valid_ok);
        }else{
            img_valid.setImageResource(R.drawable.valid_fail);
        }
        img_valid.setVisibility(View.VISIBLE);
    }
    @OnTextChanged(R.id.edt_join_pw2)
    void onPw2Change(CharSequence s){
        if (edt_pw.getText().toString().isEmpty() || edt_pw2.getText().toString().isEmpty())return;
        if (edt_pw.getText().toString().equals(edt_pw2.getText().toString())){
            img_valid.setImageResource(R.drawable.valid_ok);
        }else{
            img_valid.setImageResource(R.drawable.valid_fail);
        }
        img_valid.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.btn_join_male)
    void onMaleClick(){
        btn_male.setEnabled(false);
        btn_femail.setEnabled(true);
        sex="man";
    }

    @OnClick(R.id.btn_join_female)
    void onFemaleClick(){
        btn_femail.setEnabled(false);
        btn_male.setEnabled(true);
        sex="woman";
    }

    @OnClick(R.id.tv_join_birthday)
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
                .setLabel("년","월","일","시","분","초")
                .setOutSideCancelable(false)
                .build();
        birthdayPicker.setDate(Calendar.getInstance());
        birthdayPicker.show();
    }

    @OnClick(R.id.btn_join_submit)
    void onSubmitClick() {
        if (!StringFormatChecker.isValidId(edt_email.getText().toString())) {
            makeToast("아이디를 확인해주세요 (4~30글자)");
            return;
        }
        if (!StringFormatChecker.isValidPassword(edt_pw.getText().toString())) {
            makeToast("비밀번호는 영문 + 숫자 조합으로 8자 이상으로 해주세요");
            return;
        }
        if (!edt_pw.getText().toString().equals(edt_pw2.getText().toString())) {
            makeToast("비밀번호가 일치하지 않습니다");
            return;
        }
        if (sex == null) {
            makeToast("성별을 선택해 주세요");
            return;
        }
        if (birthday == null) {
            makeToast("생일을 입력해 주세요");
            return;
        }
        presenter.register(
                edt_email.getText().toString(),
                edt_pw.getText().toString(),
                edt_nick.getText().toString(),
                sex,
                birthday);
    }


    @Override
    public void joinSuccess(){
        wrapper_info.setVisibility(View.INVISIBLE);
        btn_close.setVisibility(View.INVISIBLE);
        wrapper_joinSuccess.setVisibility(View.VISIBLE);
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tick-> navigateActivity(StartGuideActivity.class)
                        ,fail-> Log.e("joinActivity","failToNavigate = "+fail.toString())
                );
    }

}
