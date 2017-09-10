package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by haesung on 2017-09-09.
 */

@Data
public class ReportResultBody extends RealmObject{
    String reportId;
    RealmList<ResultFormat> results;

}
