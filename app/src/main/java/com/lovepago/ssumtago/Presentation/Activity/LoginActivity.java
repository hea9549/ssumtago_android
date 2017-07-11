package com.lovepago.ssumtago.Presentation.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lovepago.ssumtago.ArrayElement;
import com.lovepago.ssumtago.ArrayMockTest;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.Domain.StringFormatChecker;
import com.lovepago.ssumtago.Presentation.Presenter.LoginActivityPresenter;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class LoginActivity extends STGBaseActivity {
    @Inject
    LoginActivityPresenter presenter;

    @BindView(R.id.edt_login_id)
    EditText edtId;
    @BindView(R.id.edt_login_pw)
    EditText edtPw;
    @BindView(R.id.cb_login_autologin)
    CheckBox cbAutoLogin;
    @BindView(R.id.btn_login_join)
    Button btnJoin;
    @BindView(R.id.btn_login_findPw)
    Button btnFindPw;
    @BindView(R.id.btn_login_login)
    Button btnLogin;
    @BindView(R.id.btn_login_facebook)
    Button btnFacebook;

    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(StringFormatChecker.isValidEmail(s.toString()) && StringFormatChecker.isValidPassword(edtPw.getText().toString()))
                    btnLogin.setEnabled(true);
                else
                    btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(StringFormatChecker.isValidPassword(s.toString()) && StringFormatChecker.isValidEmail(edtId.getText().toString()))
                    btnLogin.setEnabled(true);
                else
                    btnLogin.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnJoin.setOnClickListener(i->navigateActivity(JoinActivity.class));

        Realm realm = Realm.getDefaultInstance();
        ArrayMockTest testData1 = new ArrayMockTest();
        List<ArrayElement> listData = new ArrayList<>();
        listData.add(new ArrayElement("1"));
        listData.add(new ArrayElement("2"));
        listData.add(new ArrayElement("3"));
        testData1.datas = new RealmList<ArrayElement>(listData.toArray(new ArrayElement[3]));
        realm.beginTransaction();
        realm.copyToRealm(testData1);
        realm.commitTransaction();
        Log.e("test",testData1.datas.toString());

        testData1 = realm.where(ArrayMockTest.class).findFirst();
        Log.e("test2",testData1.datas.toString());

        realm.beginTransaction();
        testData1.datas.get(0).deleteFromRealm();
        realm.commitTransaction();

        Log.e("test3",testData1.datas.toString());
    }
}
