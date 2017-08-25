package com.lovepago.ssumtago.Data.Model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ParkHaeSung on 2017-05-24.
 */

public class RealmString extends RealmObject{
    String content;
    @Override
    public String toString(){
        return content;
    }

    public RealmString(){

    }
    public RealmString(String content){
        this.content = content;
    }
}
