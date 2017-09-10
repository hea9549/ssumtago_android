package com.lovepago.ssumtago.Service;


import com.lovepago.ssumtago.Data.Model.PredictReport;
import com.lovepago.ssumtago.Data.Model.User;
import com.lovepago.ssumtago.Data.Model.VersionDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */

public interface UserService {
    Observable<User> login(String email, String pw, String joinType);
    Observable<User> register(String email, String pw, String joinType,String name,String sex, String birthDay);

    Observable<User> updateUserFCM(String fcmToken);

    Observable<User> updateUser(User user);

    Observable<User> getUserObservable();

    User getUser();

    Observable<ResponseBody> checkFacebook(String token);

    void alertPredictReportChange();

    Observable<List<PredictReport>> getPredictReportsObservable();

    Observable<ResponseBody> withDraw();

    Observable<VersionDTO> getVersions();
}
