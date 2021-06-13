package com.example.mobile_immanueljoseph_2301852215.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPref {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SharedPref(Context context){
        pref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void save(String userId, String username, String name){
        editor.putString("userId", userId);
        editor.putString("username", username);
        editor.putString("name", name);
        editor.apply();
    }

    public String getUserId(){
        return pref.getString("userId", "");
    }

    public String getUsername(){
        return pref.getString("username", "");
    }

    public String getName(){
        return pref.getString("name", "");
    }

    public void clearSharedPref(){
        editor.clear().commit();
    }
}
