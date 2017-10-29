package com.example.admin.sessionmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText userName,Password;
    Button Login;
    AlertDialogManager alertDialogManager = new AlertDialogManager();
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManagement = new SessionManagement(getApplicationContext());

        userName = (EditText) findViewById(R.id.editText2);
        Password = (EditText) findViewById(R.id.editText3);

        Toast.makeText(getApplicationContext(),"User login status:" + sessionManagement.isLoggedIn(),Toast.LENGTH_LONG).show();

        Login = (Button) findViewById(R.id.button);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String password = Password.getText().toString();

                if(username.trim().length() > 0 && password.trim().length() > 0){
                    if(username.equals("test") && password.equals("test")){
                        sessionManagement.createLoginSession("test","test@gmail.com");
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        alertDialogManager.showAlertDialog(LoginActivity.this,"Login failed","Incorrect credentials",false);

                    }
                }else{
                    alertDialogManager.showAlertDialog(LoginActivity.this,"login failed","Please enter details",false);
                }
            }
        });
    }
}
