package com.lovepago.ssumtago.Model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by lg on 2016-11-08.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class Exercise implements Serializable {
    int exNo;
    String exName;
    int restSecond;
    String method;
    String exImage;
    String exUrl;
    String exImgSysName;
    String exDefaultSet;
    String exDataName;
    String exEtc;
    String exDesc;
    int exLevel;
    int exOrder;
    int exSecond;
    String exDepth1;
    String exDepth2;
    String exDepth3;
}
