package com.lovepago.ssumtago.Retrofit;


import com.lovepago.ssumtago.Data.Model.LoginDTO;
import com.lovepago.ssumtago.Data.Model.UpdateFcmDTO;
import com.lovepago.ssumtago.Data.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-14.
 */

public interface ApiUser {

    //회원가입 [PostManTest : NOT, UnitTest : NOT ]
    @POST("users")
    Observable<User> register(@Body User user);

    //회원 정보 조회? [PostManTest : YES, UnitTest : NOT ]
    @GET("users")
    Observable<User> getUser();

    //페북 토큰 확인 [PostManTest : NOT, UnitTest : NOT ] // 200 이 유저존재함인거
    @GET("users")
    Observable<ResponseBody> checkFaceBook(@Query("token") String token);

    //회원 정보 수정 [PostManTest : NOT, UnitTest : NOT ]
    @PATCH("users/{userEmail}")
    Observable<User> modifyUser(@Path("userEmail")String userEmail,@Body User user);

    //fcm 수정 메서드, 위랑 합쳐져야되지않나? [PostManTest : NOT, UnitTest : NOT ]
    @PATCH("fcm")
    Observable<User> updateFcm(@Body UpdateFcmDTO fcmToken);

    //회원탈퇴 [PostManTest : NOT, UnitTest : NOT ]
    @DELETE("users")
    Observable<ResponseBody> deleteUser();

    //로그인 [PostManTest : NOT, UnitTest : NOT ]
    @POST("sessions")
    Observable<User> login(@Body User user);
}
