package com.lovepago.ssumtago.Presentation.Activity;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.lovepago.ssumtago.CustomClass.STGBaseActivity;
import com.lovepago.ssumtago.CustomClass.STGPreference;
import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.Model.VersionDTO;
import com.lovepago.ssumtago.R;
import com.lovepago.ssumtago.STGApplication;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.UserService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class SplashActivity extends STGBaseActivity{
    private final String TAG = "SplashActivity";
    @BindView(R.id.lottie_splash_splashView)
    LottieAnimationView lottie_splashView;
    @Inject
    SurveyService surveyService;
    @Inject
    UserService userService;
    private boolean isAnimFinish = false;
    private boolean isVersionCheckFinish = false;
    private STGPreference preference;
    @Override
    public void STGOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        STGApplication.getComponent().inject(this);
        preference = new STGPreference(this);
        userService.getVersions()
                .subscribe(versionDTO -> {
                    if (versionDTO.getAppMajorVersion()>STGApplication.majorVersion){
                        makeMarketDialog("필수 업데이트가 발견되었습니다. 마켓으로 이동하겠습니까?",true,versionDTO);
                    }else if(versionDTO.getAppSubVersion()>STGApplication.subVersion){
                        makeMarketDialog("새로운 기능이 추가된 버전이 발견되었습니다. 마켓으로 이동하겠습니까?",false,versionDTO);
                    }else{
                        checkDB(versionDTO);
                    }

                },fail->{
                    Log.e(TAG,"fial in get version = "+fail.toString());
                    Toast.makeText(this, "버전 정보를 받아오는데 실패했습니다. 페이스북 럽파고 페이지를 확인해주세요", Toast.LENGTH_SHORT).show();
                    finish();
                });

        lottie_splashView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimFinish = true;
                checkFinishSplash();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkDB(VersionDTO versionDTO) {
        if (versionDTO.getDbMajorVersion()>preference.getValue(STGPreference.PREF_DB_VERSION_MAJOR,-1)){
            downAndSyncDB(versionDTO);
        }else{
            isVersionCheckFinish = true;
            checkFinishSplash();
        }
    }

    private void downAndSyncDB(VersionDTO versionDTO) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Survey.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        surveyService.getAllSurvey()
                .subscribe(s->{
                    Log.e(TAG,"suscess updateSurveyDB");
                    isVersionCheckFinish = true;
                    preference.put(STGPreference.PREF_DB_VERSION_MAJOR,versionDTO.getDbMajorVersion());
                    checkFinishSplash();
                },f->{
                    Log.e(TAG, "fail in downAndSynchDB() , fail - >"+f.toString());
                    Toast.makeText(this, "DB업데이트중 에러가발생했습니다. 앱을 재실행 해 주세요", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void checkFinishSplash() {
        if (isAnimFinish&&isVersionCheckFinish) navigateActivity(LoginActivity.class);
    }

    void makeMarketDialog(String message,boolean isForce,VersionDTO versionDTO){
        new AlertDialog.Builder(this)
                .setTitle("업데이트 발견")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("이동", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    dialog.dismiss();
                    if (isForce){
                        finish();
                    }else{
                        checkDB(versionDTO);
                    }
                })
                .show();
    }
}
