package com.example.admin.bellezatimes;

/**
 * Created by ADMIN on 22-01-2017.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.SharedPreferences;
import okhttp3.ResponseBody;

import android.support.v7.app.AlertDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.SimpleAdapter;
import java.io.IOException;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Button;
import com.example.admin.bellezatimes.R;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class AdminFeedPage extends AppCompatActivity implements View.OnClickListener{

    SessionManager manager;
    String myJson;
    ArrayList<HashMap<String,String>> newsItems;
    SharedPreferences sharedPreferences;
    ListView listView;
    Button logout;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private static final String BASE_URL = "http://10.0.2.2/SMS/showFeed/";
   // private static final String BASE_URL = "http://192.168.1.4/SMS/showFeed/";



    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient okHttpClient1 = new OkHttpClient();
    public static final String TAG = "Mytag";



    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setTheme(R.style.AppTheme_AppBarOverlay);
        setContentView(R.layout.admin_feed_page);
        manager = new SessionManager();

        listView = (ListView) findViewById(R.id.listView);
        //logout = (Button) findViewById(R.id.button5);
        newsItems = new ArrayList<HashMap<String, String>>();
        getData();


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);


        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


    /*    logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Logout();
            }
        });*/


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(AdminFeedPage.this,MainActivity.class);
        startActivity(intent);


       /* AlertDialog alertDialog = new AlertDialog.Builder(FeedPage.this).create();
        alertDialog.setTitle("Look out!!");
        alertDialog.setMessage("Please log out before leaving!!!");
        alertDialog.show();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.logout:
                Logout();
                break;
        }
        return true;
    }
    public void getData(){


        Request request = new Request.Builder().url(BASE_URL).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AdminFeedPage.this,"Error in connection.Try again",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String resp = response.body().string();
                    myJson = resp;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showList();
                        }
                    });


                }
                catch (IOException e){
                    Log.e(TAG,e.getMessage());
                }

            }
        });
    }
    protected void showList(){
        try{
            //JSONObject jsonObject = new JSONObject(myJson);
            //myArray = jsonObject.optJSONArray("results");
            final JSONArray jsonArray = new JSONArray(myJson);



            for(int i=0;i<jsonArray.length();i++){
                JSONObject jo = jsonArray.getJSONObject(i);
                String title = jo.optString("title");
                String description = jo.optString("description");

                HashMap<String,String> news = new HashMap<String, String>();
                news.put("title",title);
                news.put("description",description);
                newsItems.add(news);
                Log.e(TAG,newsItems.toString());

            }

            final ListAdapter listAdapter = new SimpleAdapter(AdminFeedPage.this,newsItems,R.layout.list_text_views,new String[]{"title","description"},new int[]{R.id.title,R.id.description});
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String data =((TextView) view.findViewById(R.id.description)).getText().toString();
                    Intent intent = new Intent(AdminFeedPage.this,DetailFeed.class);
                    intent.putExtra("description",data);
                    startActivity(intent);

                }
            });


        }
        catch (JSONException j){
            Log.e(TAG,j.getMessage());
        }
    }

    public void Logout(){
        manager.setPreferences(AdminFeedPage.this,"status","0");
        Intent intent = new Intent(AdminFeedPage.this,MainActivity.class);
        startActivity(intent);


    }

    @Override
    public void onClick(View v){

        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFab();
                break;
            case R.id.fab1:
                Intent intent = new Intent(AdminFeedPage.this,InsertFeed.class);
                startActivity(intent);
                Log.d("FAB","fab1");
                break;
            case R.id.fab2:
                Intent intent1 = new Intent(AdminFeedPage.this,DeleteFeed.class);
                startActivity(intent1);
        }
    }

    public void animateFab(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.startAnimation(fab_close);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Fab1","close");

        }
        else{
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.startAnimation(fab_open);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Fab1","open");
        }
    }



}

