package com.kibtechnologies.captainshieid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Khushboo Jha on 5/22/21.
 */
public class PreferenceUtils {
    private static PreferenceUtils preferenceUtils;
    private SharedPreferences.Editor edit;
    private SharedPreferences preferences;
    public static final String KEY_TRACKID = "trackid";
    public static final String USER_PHONE_NO = "user_number";

    private PreferenceUtils(Context context) {

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        edit = preferences.edit();

    }


    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null)
            preferenceUtils = new PreferenceUtils(context);

        return preferenceUtils;
    }

    public String getString(String key, String defaultValue) {
        return preferences.getString(key, defaultValue);
    }


    public void saveString(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }
    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        edit.putInt(key, value);
        edit.commit();
    }

    public String getToken() {
        return preferences.getString(Key.TOKEN.name(), null);
    }

    public enum Key {
        TOKEN,
        USER_NAME,
        UID,
        PHONE_NUMBER,
        NUMBER_OPERATORS,
        CODE_OPERATORS,
        CIRCLE_CODE_OPERATORS,
        TYPE_OPERATORS
    }
}
