package com.lovepago.ssumtago.Presentation.Presenter;

import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Data.Model.RequestAnswer;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface HomeActivityPresenter {
    void setView(View view);
    void init();
    void onSubmitClick(RequestAnswer requestAnswer);

    interface View extends BaseViewPresenter{
        void setList(List<Question> questions);
    }
}
