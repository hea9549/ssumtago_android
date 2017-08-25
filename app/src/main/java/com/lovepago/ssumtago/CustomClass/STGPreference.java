package com.lovepago.ssumtago.CustomClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ParkHaeSung on 2017-08-20.
 */

public class STGPreference {
    private Context mContext;
    private static final String PREF_NAME = "come.lovepago.ssumtago";
    public static final String PREF_LAST_SURVEYED = "PREF_LAST_SURVEYED";
    public static final String PREF_ID = "PREF_ID";
    public static final String PREF_PW= "PREF_PW";

    public STGPreference(Context c) {
        mContext = c;
    }

    public String put(String key, String value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
        return value;
    }

    public boolean put(String key, boolean value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return value;
    }

    public void put(String key, int value) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public String getValue(String key, String dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getString(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public int getValue(String key, int dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getInt(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }

    public boolean getValue(String key, boolean dftValue) {
        SharedPreferences pref = mContext.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        try {
            return pref.getBoolean(key, dftValue);
        } catch (Exception e) {
            return dftValue;
        }
    }
}
