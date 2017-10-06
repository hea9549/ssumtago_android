package com.lovepago.ssumtago.Presentation.Presenter;


import com.lovepago.ssumtago.Data.Model.Survey;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface LobyActivityPresenter {
    void setView(View view);

    void onAddReportClick();

    void onPrevClick();

    void onNextClick();

    interface View extends BaseViewPresenter{
        void setPercent(int percent);

        void makeSelectSurveyDialog(List<Survey> surveys);

        void setDate(String date);

        void makeAlertDialg(String message);
    }
}
