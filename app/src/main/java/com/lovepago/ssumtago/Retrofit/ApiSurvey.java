package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public interface ApiSurvey {

    //서버의 모든 서베이를 받아옴 [PostManTest : NOT, UnitTest : NOT ]
    @GET("surveys")
    Observable<List<Survey>> getAllSurvey();

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     *
     * @param surveyId surveyId에 해당하는 survey를 받아옴
     */
    @GET("surveys/{surveyId}")
    Observable<Survey> getSurvey(@Path("surveyId") int surveyId);

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     *
     * @param survey 만들 survey 객체
     * @implNote 클라에서 사용할기능인가 체크
     */
    @POST("surveys")
    Observable<Survey> makeSurvey(@Body Survey survey);

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     *
     * @param surveyId 수정할 survey 의 surveyId
     * @param survey 수정할 survey 의 수정된 객체
     */
    @PATCH("surveys/{surveyId}")
    Observable<Survey> modifySurvey(@Path("surveyId") String surveyId, @Body Survey survey);

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     *
     * @param surveyId 삭제할 survey의 surveyId
     * */
    @DELETE("surveys/{surveyId}")
    Observable<ResponseBody> deleteSurvey(@Path("surveyId") String surveyId);


}
