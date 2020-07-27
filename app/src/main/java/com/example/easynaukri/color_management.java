package com.example.easynaukri;

import android.content.Context;
import android.content.SharedPreferences;

public class color_management {
    public String PREF_KEY="Color";
    public String COLOR="color";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public color_management(Context context){
        sp=context.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        editor=sp.edit();
        editor.apply();
    }
    public void setColor(String color){
        editor.putString(COLOR,color);
        editor.commit();
    }
    public String getColor(){
        return  sp.getString(COLOR,"blue");
    }
}
