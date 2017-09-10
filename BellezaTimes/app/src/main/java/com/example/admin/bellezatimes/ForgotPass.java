package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 30-12-2016.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
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

public class ForgotPass extends AppCompatActivity {
    public static final String TAG = "MyTag";
   //private static final String BASE_URL = "http://192.168.1.4/SMS/sendMail/";
    private static final String BASE_URL = "http://10.0.2.2/SMS/sendMail/";
    private OkHttpClient okHttpClient = new OkHttpClient();
    private EditText forgotPass;
    Button submit;
    TextView textView;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.forgot_pass);

        forgotPass = (EditText) findViewById(R.id.editText4);

        submit = (Button) findViewById(R.id.button2);
        textView = (TextView) findViewById(R.id.textView4);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = forgotPass.getText().toString();

                if(email.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ForgotPass.this,"Email address can't be empty!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                sendMail(email);
            }
        });

    }

    public void sendMail(String email) {
        RequestBody requestBody = new FormBody.Builder()

                .add("email", email)
                .build();

        final Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgotPass.this, "Error in connection.Try again", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String resp = response.body().string();
                    Log.e(TAG, resp);
                    if (resp.contains("Message has been sent successfully")) {
                        /*AlertDialog alertDialog = new AlertDialog.Builder(forgotPass.this).create();
                        alertDialog.setTitle("Confirmation");
                        alertDialog.setMessage("Mail sent to your account.Please check your mail.");
                        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(forgotPass.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        alertDialog.show();*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ForgotPass.this, "Mail sent", Toast.LENGTH_LONG).show();
                            }
                        });
                        Intent intent = new Intent(ForgotPass.this,MainActivity.class);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ForgotPass.this, "Error in sending mail", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
 }
