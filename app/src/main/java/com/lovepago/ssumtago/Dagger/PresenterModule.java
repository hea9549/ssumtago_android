package com.lovepago.ssumtago.Dagger;

import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenter;
import com.lovepago.ssumtago.Presentation.Presenter.MainActivityPresenterImpl;

import dagger.Binds;
import dagger.Module;

/**
 * Created by ParkHaeSung on 2017-05-23.
 */
@Module
public abstract class PresenterModule {
    @Binds
    public abstract MainActivityPresenter bindMainPresenter(MainActivityPresenterImpl mainActivityPresenter);
}
