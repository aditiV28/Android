package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 18-01-2017.
 */
import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    public void setPreferences(Context context,String key,String value){
        SharedPreferences.Editor editor = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String  getPreferences(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginStatus",Context.MODE_PRIVATE);
        String position =  sharedPreferences.getString(key,"");
        return position;

    }
}
