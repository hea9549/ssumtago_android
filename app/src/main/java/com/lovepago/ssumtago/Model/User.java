package com.lovepago.ssumtago.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jun on 2016. 11. 8..
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class User {
    String email;
    String userName;
    String userTel;
    String userGender;
    String userType;
    int height;
    int weight;
    String exPrefer;
    String userBirthday;
    String withdrawYN;
    String questYN;
    String userLevel;
    String joinType;
    String weightPurpose;
    String exSplitType;
    String exBodyType;
    String key;
}
