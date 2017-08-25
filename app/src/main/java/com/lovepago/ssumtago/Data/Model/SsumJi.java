package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-06-04.
 */
@Data
public class SsumJi extends RealmObject {
    private String questionCode;
    private String answerCode;
}
