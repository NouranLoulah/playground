package com.example.nouran.playground;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nouran on 3/31/2018.
 */


public class SharedPref {



    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    public SharedPref(Context context) {
        this.context = context;


    }

    public void createsharedpref(String id) {

        pref = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("user_id", id);
        editor.apply();

    }

    public void clear() {
        SharedPreferences pref = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.clear();
        editor.apply();

    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


}
