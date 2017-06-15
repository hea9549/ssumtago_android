package com.lovepago.ssumtago.Data.Model;

import java.util.List;

import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */
@Data
public class User {
    private String email;
    private String password;
    private String name;
    private String sex;
    private int age;
    private String joinType;
    private String fcmToken;
    private List<Ssum> ssums;
}
