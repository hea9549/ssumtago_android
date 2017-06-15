package com.lovepago.ssumtago.Data;

import android.content.Context;

import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.exceptions.RealmMigrationNeededException;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

public class RealmDBServiceImpl implements RealmDBService {
    private Context context;
    private Realm mRealm;

    public RealmDBServiceImpl(Context context) {
        this.context = context;
        initController();
    }

    @Override
    public void initController() {
        Realm.init(context);

        mRealm = Realm.getDefaultInstance();

    }

    @Override
    public boolean isConnected() {
        return mRealm != null;
    }

    @Override
    public Observable<Survey> getSurveyBySurveyId(int surveyId) {
        initController();
        return mRealm.where(Survey.class).equalTo("id", surveyId).findFirst().asObservable();
    }

    @Override
    public int getSurveyCount() {
        initController();
        return mRealm.where(Survey.class).findAll().size();
    }

    @Override
    public void inputDatas(List datas) {
        initController();
        mRealm.beginTransaction();
        mRealm.copyToRealm(datas);
        mRealm.commitTransaction();
    }

    @Override
    public <E extends RealmObject> E inputData(E data) {
        initController();
        mRealm.beginTransaction();
        mRealm.copyToRealm(data);
        mRealm.commitTransaction();
        return data;
    }
}
