package com.example.easynaukri;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionMangament {
    public String PREF_KEY="login";
    public String PREF_LOGGED="logged",PREF_PROFILE;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SessionMangament(Context context) {
        sp=context.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        editor=sp.edit();
        editor.apply();
    }
    public void setsession(boolean log){
        editor.putBoolean(PREF_LOGGED,log);
        editor.commit();
    }
    public void setsessionprofile(boolean profile){
        editor.putBoolean(PREF_PROFILE,profile);
        editor.commit();
    }
    public boolean getsession(){
        return sp.getBoolean(PREF_LOGGED,false);
    }
    public boolean getsessionprofile(){
        return sp.getBoolean(PREF_PROFILE,false);
    }
}
