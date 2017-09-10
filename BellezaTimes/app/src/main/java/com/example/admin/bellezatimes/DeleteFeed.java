package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 12-02-2017.
 */
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.ResponseBody;

public class DeleteFeed extends AppCompatActivity {
    EditText etTitle;
    Button submit;
    private static final String BASE_URL = "http://10.0.2.2/SMS/deleteFeed/";
    //private static final String BASE_URL = "http://192.168.1.4/SMS/deleteFeed/";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    public static final String TAG = "Mytag";

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.delete_feed);

        etTitle = (EditText) findViewById(R.id.editText10);
        submit = (Button) findViewById(R.id.button7);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                if(title.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteFeed.this,"Field cannot be empty!!",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    delete(title);
                }
            }
        });
    }

    public void delete(String title){
        RequestBody requestBody = new FormBody.Builder()
                .add("title",title)
                .build();
        Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeleteFeed.this,"Error in connection.Try again!!",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.d(TAG,resp);
                if(resp.contains("Deleted!")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteFeed.this,"Feed deleted successfully!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(DeleteFeed.this,AdminFeedPage.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteFeed.this,"Error in deletion.Try again!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(DeleteFeed.this,AdminFeedPage.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
