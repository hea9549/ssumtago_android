package com.lovepago.ssumtago.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ParkHaeSung on 2017-02-07.
 */

@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class LoginDTO {
    String loginType;
    String email="";
    String pw="";
    String token="";
    String key="";
    public LoginDTO(String loginType, String email, String pw){
        this.loginType = loginType;
        this.email = email;
        this.pw = pw;
    }

}
