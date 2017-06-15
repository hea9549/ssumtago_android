package com.lovepago.ssumtago.Data.Model;

import lombok.Data;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */
@Data
public class LoginDTO {
    private String id;
    private String pw;
    private String token;
    private String loginType;
    public LoginDTO(String loginType){
        this.loginType = loginType;
    }
}
