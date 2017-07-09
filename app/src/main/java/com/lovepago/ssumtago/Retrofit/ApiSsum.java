package com.lovepago.ssumtago.Retrofit;

import com.lovepago.ssumtago.Data.Model.RequestAnswer;
import com.lovepago.ssumtago.Data.Model.Ssum;
import com.lovepago.ssumtago.Data.Model.SsumReport;
import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ParkHaeSung on 2017-07-09.
 */

public interface ApiSsum {
    @POST("ssums")
    Observable<String> createSsum(@Body Ssum ssum);

    @GET("ssums/{ssumId}")
    Observable<Ssum> getSsum(@Path("ssumId")int ssumId);

    @PATCH("ssums/{ssumId}")
    Observable<String> updateSsum(@Path("ssumId")int ssumId,@Body Ssum ssum);

    @DELETE("ssums/{ssumId}")
    Observable<String> deleteSsum(@Path("ssumId")int ssumId);

    @POST("ssums/{ssumId}/predictReports")
    Observable<String> requestSsumExpect(@Path("ssumId")int ssumId,@Body RequestAnswer requestAnswer);

    @POST("ssums/{ssumId}/predictReports/{reportId}")
    Observable<SsumReport> getReport(@Path("ssumId")int ssumId, @Path("reportId")int reportId);
}
