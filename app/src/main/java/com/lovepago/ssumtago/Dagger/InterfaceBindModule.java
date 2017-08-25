package com.lovepago.ssumtago.Dagger;

import com.lovepago.ssumtago.Presentation.Activity.FacebookJoinActivity;
import com.lovepago.ssumtago.Presentation.Presenter.FacebookJoinActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.FacebookJoinActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.JoinActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.JoinActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.LobyActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.LobyActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.LoginActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.LoginActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;
import com.lovepago.ssumtago.Presentation.Presenter.SurveyPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.SurveyPresenterImpl;
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
    public abstract SurveyPresenter bindSurveyPresenter(SurveyPresenterImpl surveyPresenter);
    @Binds
    public abstract JoinActivityPresenter bindJoinPresenter(JoinActivityPresenterImpl joinActivityPresenter);
    @Binds
    public abstract LoginActivityPresenter bindLoginPresenter(LoginActivityPresenterImpl loginActivityPresenter);
    @Binds
    public abstract FacebookJoinActivityPresenter bindFacebookJoinActivityPresenter(FacebookJoinActivityPresenterImpl facebookJoinActivityPresenter);
    @Binds
    public abstract LobyActivityPresenter bindLobyActivityPresenter(LobyActivityPresenterImpl lobyActivityPresenter);
}
