package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.Data.Model.PredictReport;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public interface ApiReport {
    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     *
     * */
    @GET("predictReports")
    Observable<List<PredictReport>> getReports();
    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     * @param reportId 얻어올 리포트 아이디
     * */
    @GET("predictReports/{reportId}")
    Observable<List<PredictReport>> getReport(@Path("reportId")String reportId);

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     * @param predictReport 만들 리포트
     * */
    @POST("predictReports")
    Observable<PredictReport> makeReport(@Body PredictReport predictReport);

    /**
     * [PostManTest : NOT, UnitTest : NOT ]
     * @param predictReport 만들 리포트
     * */
    @POST("previousReports")
    Observable<ResponseBody> makePreviousReports(@Body PredictReport predictReport);

}
