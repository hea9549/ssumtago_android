package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-08-18.
 */

@Data
public class RealmDouble extends RealmObject{
    double val;
    @Override
    public String toString(){
        return String.valueOf(val);
    }

    public RealmDouble(){

    }
    public RealmDouble(double val){
        this.val= val;
    }
}
