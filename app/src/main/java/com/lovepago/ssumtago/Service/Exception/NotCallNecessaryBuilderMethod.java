package com.lovepago.ssumtago.Service.Exception;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public class NotCallNecessaryBuilderMethod extends NullPointerException{
    public NotCallNecessaryBuilderMethod(Class builderClass,String methodName){
        super("you must call '"+methodName+"' method in "+builderClass.getName() + " class");
    }
    private NotCallNecessaryBuilderMethod(){

    }
}
