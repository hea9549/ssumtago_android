package com.lovepago.ssumtago.Presentation.Presenter;


import com.lovepago.ssumtago.Data.Model.PredictReport;
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

    void onPredictClick(PredictReport report);

    interface View extends BaseViewPresenter{
        void setPercent(int percent);

        void setPredictLove(List<ValueFormat> results);

        void makeSelectSurveyDialog(List<Survey> surveys);

        void setSpinner(List<PredictReport> reports);

        void makeAlertDialg(String message);
    }
}
