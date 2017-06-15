package com.lovepago.ssumtago.Presentation.Presenter;

import com.lovepago.ssumtago.Data.Model.Question;
import com.lovepago.ssumtago.Data.Model.RequestAnswer;

import java.util.List;

/**
 * Created by ParkHaeSung on 2017-05-15.
 */

public interface JoinActivityPresenter {
    void setView(View view);

    void register(String email, String pw, String name, String sex, int age);

    interface View extends BaseViewPresenter{

    }
}
