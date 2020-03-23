package com.Group6.checkup.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = context.getSharedPreferences("Checkup",Context.MODE_PRIVATE);
    }

    public String getCurrentUsername(){
        return  preferences.getString("loginId",null);
    }

    public boolean setCurrentUsername(String loginId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loginId",loginId);

        return editor.commit();
    }

    public int getUserId(){
        return  preferences.getInt("actorId",0);
    }

    public boolean setUserId(int loginId){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("actorId",loginId);

        return editor.commit();
    }

}
