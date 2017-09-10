package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 12-01-2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
public class DetailFeed extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.detail_feed);

        TextView descr = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        String selected = intent.getStringExtra("description");
        descr.setText(selected);
    }
}
