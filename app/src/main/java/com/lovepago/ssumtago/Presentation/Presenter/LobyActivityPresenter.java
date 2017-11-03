package com.lovepago.ssumtago.Presentation.Presenter;


import com.lovepago.ssumtago.Data.Model.Survey;
import com.lovepago.ssumtago.Data.Model.ValueFormat;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface LobyActivityPresenter {
    void setView(View view);

    void onAddReportClick();

    void onPercentOpened();

    void onPredictLoveOpened();

    interface View extends BaseViewPresenter{
        void setPercent(int percent);

        void setPredictLove(List<ValueFormat> results);

        void makeSelectSurveyDialog(List<Survey> surveys);


        void setDate(String date);

        void makeAlertDialg(String message);
    }
}
