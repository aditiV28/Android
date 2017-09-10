package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 01-12-2016.
 */


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
//import com.example.admin.bellezatimes.R;
import android.content.SharedPreferences;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MYTAG";


  // private static final String BASE_URL = "http://192.168.1.4/SMS/login/";
   private static final String BASE_URL = "http://10.0.2.2/SMS/login/";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    SessionManager manager;

    Button login;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private EditText etUserName;
    private EditText etPassword;
    CheckBox show_password;



    TextView tv1;
    TextView forgotPass,changePass;



    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.login_activity);
        manager = new SessionManager();

        login = (Button) findViewById(R.id.button4);
        etUserName = (EditText) findViewById(R.id.editText);
        etPassword = (EditText) findViewById(R.id.editText2);
        tv1 = (TextView) findViewById(R.id.textView);
        forgotPass = (TextView) findViewById(R.id.textView3);
        changePass = (TextView) findViewById(R.id.textView6);
        show_password = (CheckBox) findViewById(R.id.checkBox);






        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String user_name = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                //editor.putString(etPassword.toString(),password);

                if(user_name.isEmpty() || password.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"Neither of the fields can be empty",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{


                    login(user_name,password);
                }

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this,ForgotPass.class);
                startActivity(intent1);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this,ChangePass.class);
                startActivity(intent2);
            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                else{
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });



    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      //  finish();
      //  System.exit(0);
    }



    public void login(final String user_name, String password){
        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",user_name)
                .add("password",password)
                .build();
        Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"Error in connection.Try again",Toast.LENGTH_LONG).show();
                    }
                });
                //Toast.makeText(MainActivity.this,"Login error!!" + e.getMessage(),Toast.LENGTH_LONG).show();


            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String resp = response.body().string();
                    System.out.print(resp);
                    Log.e(TAG,resp);
                    if(resp.contains("Logged in")){

                        manager.setPreferences(MainActivity.this,"status","1");
                        manager.setPreferences(MainActivity.this,"uname",user_name);
                        String status = manager.getPreferences(MainActivity.this,"status");
                        String uname = manager.getPreferences(MainActivity.this,"uname");
                        Log.d("status",status);
                        Log.d("uname",uname);
                        if(user_name.contains("admin")){
                            Intent intent = new Intent(MainActivity.this,AdminFeedPage.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(MainActivity.this,FeedPage.class);
                            startActivity(intent);
                        }




                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"Incorrect credentials!!!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                   // Toast.makeText(MainActivity.this,"Done"+resp,Toast.LENGTH_LONG).show();

                }
                catch (IOException e){

                    Log.e(TAG,e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }







}

