package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 12-02-2017.
 */
import android.content.Context;
import android.content.Intent;
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
public class InsertFeed extends AppCompatActivity  {


    EditText etTitle,etDescription;
    Button submit;
    private static final String BASE_URL = "http://10.0.2.2/SMS/insertFeed/";
    //private static final String BASE_URL = "http://192.168.1.4/SMS/insertFeed/";
    private static final OkHttpClient okHttpClient = new OkHttpClient();
    public static final String TAG = "Mytag";


    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.insert_feed);

        etTitle = (EditText) findViewById(R.id.editText6);
        etDescription = (EditText) findViewById(R.id.editText9);
        submit = (Button) findViewById(R.id.button6);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();

                if(title.isEmpty() || description.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InsertFeed.this,"Neither of the fields can be empty!!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    insert(title,description);
                }
            }
        });
    }

    public void insert(String title,String description){
        RequestBody requestBody = new FormBody.Builder()
                .add("title",title)
                .add("description",description)
                .build();
        Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InsertFeed.this,"Error in connection.Try again!!",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();
                Log.e(TAG,resp);
                if(resp.contains("Inserted!!")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InsertFeed.this,"Feed inserted succesfully!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(InsertFeed.this,AdminFeedPage.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InsertFeed.this,"Error in insertion.Try again!!",Toast.LENGTH_LONG).show();
                        }
                    });
                    Intent intent = new Intent(InsertFeed.this,AdminFeedPage.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

}
