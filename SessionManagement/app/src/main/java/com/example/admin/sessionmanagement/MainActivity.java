package com.example.admin.sessionmanagement;

import android.app.Activity;

/**
 * Created by ADMIN on 29-10-2017.
 */
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends Activity {
    AlertDialogManager alertDialogManager = new AlertDialogManager();
    SessionManagement sessionManagement;
    Button logout;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);

        sessionManagement = new SessionManagement(getApplicationContext());
        TextView name = (TextView) findViewById(R.id.textView3);
        TextView email = (TextView) findViewById(R.id.textView4);
        logout = (Button) findViewById(R.id.button2);

        Toast.makeText(getApplicationContext(),"User Login status:" + sessionManagement.isLoggedIn(),Toast.LENGTH_LONG).show();

        sessionManagement.checkLogin();
        HashMap<String,String> user = sessionManagement.getUserDetails();
        String uname = user.get(SessionManagement.Name);
        String uemail = user.get(SessionManagement.Email);
        name.setText(Html.escapeHtml("Name" + uname));
        email.setText(Html.escapeHtml("Email" + uemail));

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                        sessionManagement.logoutUser();
            }
        });

    }
}
