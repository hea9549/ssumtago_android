package com.lovepago.ssumtago;

import io.realm.RealmObject;

/**
 * Created by ParkHaeSung on 2017-07-11.
 */

public class ArrayElement extends RealmObject {
    public ArrayElement(String content){
        this.content = content;
    }
    public ArrayElement(){

    }
    public String content;
}