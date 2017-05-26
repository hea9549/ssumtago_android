package com.lovepago.ssumtago.Dagger;

import com.lovepago.ssumtago.Presentation.Presenter.HomeActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.HomeActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.Service.SurveyService;
import com.lovepago.ssumtago.Service.SurveyServiceImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by ParkHaeSung on 2017-05-23.
 */
@Module
public abstract class InterfaceBindModule {
    @Binds
    public abstract MainActivityPresenter bindMainPresenter(MainActivityPresenterImpl mainActivityPresenter);
    @Binds
    public abstract HomeActivityPresenter bindHomePresenter(HomeActivityPresenterImpl homeActivityPresenter);
    @Binds
    public abstract SurveyService bindSurveyService(SurveyServiceImpl surveyService);
}
