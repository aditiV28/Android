package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 08-01-2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
//import com.example.admin.bellezatimes.R;

public class SplashScreen extends AppCompatActivity {

    SessionManager manager;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.splash_screen);
        manager = new SessionManager();

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    String status = manager.getPreferences(SplashScreen.this,"status");
                    String uname = manager.getPreferences(SplashScreen.this,"uname");
                    //Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    //startActivity(intent);
                    Log.d("status",status);
                    if(status.equals("1")) {
                        if(uname.equals("admin")){
                            Intent intent = new Intent(SplashScreen.this,AdminFeedPage.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(SplashScreen.this,FeedPage.class);
                            startActivity(intent);
                        }
                    }
                    else if(status.equals("0")){
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }

                    //finish();


                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
}
