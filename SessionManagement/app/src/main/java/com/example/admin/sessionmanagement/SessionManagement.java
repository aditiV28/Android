package com.example.admin.sessionmanagement;

/**
 * Created by ADMIN on 29-10-2017.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "My_Pref";
    private static final String Is_Login ="IsLoggedIn";
    public static final String Name = "name";
    public static final String Email = "email";

    //Initialize the session
    public SessionManagement(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    //Create the session by putting session variables
    public void createLoginSession(String name,String email){
        editor.putBoolean(Is_Login,true);
        editor.putString(Name,name);
        editor.putString(Email,email);
        editor.commit();
    }

    //Function to retrieve session variables
    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String, String>();
        user.put(Name,sharedPreferences.getString(Name,null));
        user.put(Email,sharedPreferences.getString(Email,null));
        return user;
    }

    /*Function to check the login status
    * if not logged in re-direct to login activity
    * else do nothing
    */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(context,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }


    }

    //Function to logout user
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    //Initially set the login status
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(Is_Login,false);
    }
}
