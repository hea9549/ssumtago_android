package com.lovepago.ssumtago.Domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ParkHaeSung on 2017-05-25.
 */

public class StringFormatChecker {
    public static boolean isValidEmail(String email) {
        //check Email
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    public static boolean isValidPassword(String pw) {
        //check pw format. english+number 8~16
        String Passwrod_PATTERN = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{8,16}$";
        Pattern pattern = Pattern.compile(Passwrod_PATTERN);
        Matcher matcher = pattern.matcher(pw);
        return matcher.matches();
    }
}
