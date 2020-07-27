package com.example.easynaukri.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetConnection {
    public boolean connected(Context context){
        boolean connnection=false;
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = conn.getActiveNetworkInfo();
            connnection=info!=null&&info.isAvailable()&&info.isConnected();
            return connnection;
        }
        catch (Exception e){
            Toast.makeText(context,"Some error",Toast.LENGTH_SHORT).show();
        }
        return connnection;
    }
}
