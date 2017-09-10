package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 02-01-2017.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//import com.example.admin.bellezatimes.R;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.widget.Toast;
import java.io.IOException;
public class ChangePass extends AppCompatActivity{
    public static final String TAG = "Mytag";
    //private static final String BASE_URL = "http://192.168.1.4/SMS/changePassword/";
    private static final String BASE_URL = "http://10.0.2.2/SMS/resetPassword/";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private EditText etUserName,etPassword,etNewPassword,etConfirmPassword;
    TextView textView;
    Button submit;
    CheckBox show_password;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.change_pass);

        etUserName = (EditText) findViewById(R.id.editText5);
        //etEmail = (EditText) findViewById(R.id.editText6);
        etPassword = (EditText) findViewById(R.id.editText7);
        etNewPassword = (EditText) findViewById(R.id.editText8);
        etConfirmPassword = (EditText) findViewById(R.id.editText3);
        submit = (Button) findViewById(R.id.button3);
        textView = (TextView) findViewById(R.id.textView3);
        show_password = (CheckBox) findViewById(R.id.checkBox2);


        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String user_name = etUserName.getText().toString();
                //String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String new_password = etNewPassword.getText().toString();
                String confirm_password = etConfirmPassword.getText().toString();
                if(user_name.isEmpty() || password.isEmpty() || new_password.isEmpty() || confirm_password.isEmpty() ){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePass.this,"Neither of the fields can be empty",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    if(new_password.equals(confirm_password)){
                        changePassword(user_name,password,new_password);
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangePass.this,"New password does not match with confirm password",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }

            }
        });

        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    hidePassword();

                }

                else{
                    showPassword();

                }
            }
        });




    }

    public void showPassword(){
        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    public void hidePassword(){
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void changePassword(String user_name,String password,String new_password){
        RequestBody requestBody = new FormBody.Builder()
                .add("user_name",user_name)
                .add("password",password)
                .add("new_password",new_password)
                .build();

        Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChangePass.this,"Error in connection.Try again",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ChangePass.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String resp = response.body().string();
                    Log.e(TAG,resp);
                    if(resp.contains("Changed")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangePass.this,"Password changed successfully.Login again",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    Intent intent = new Intent(ChangePass.this,MainActivity.class);
                    startActivity(intent);
                }
                catch (IOException e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePass.this, "Error during changing password try again!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
