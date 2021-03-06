package com.example.admin.bellezatimes;

/*
 * Created by ADMIN on 01-12-2016.
 */
import android.content.Context;
import android.content.Intent;
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

public class FeedPage extends AppCompatActivity {
    SessionManager manager;
    String myJson;
    ArrayList<HashMap<String,String>> newsItems;
    SharedPreferences sharedPreferences;
    ListView listView;
    Button logout;
   private static final String BASE_URL = "http://10.0.2.2/SMS/showFeed/";
  //private static final String BASE_URL = "http://192.168.1.4/SMS/showFeed/";
  //  private static final String BASE_URL1 = "http://10.0.2.2/SMS/logout/";
   // private static final String BASE_URL1 = "http://192.168.1.2/SMS/logout/";


    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private OkHttpClient okHttpClient1 = new OkHttpClient();
    public static final String TAG = "Mytag";



    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setTheme(R.style.AppTheme_AppBarOverlay);
        setContentView(R.layout.feed_activity);
        manager = new SessionManager();

        listView = (ListView) findViewById(R.id.listView);
       // logout = (Button) findViewById(R.id.button5);
        newsItems = new ArrayList<HashMap<String, String>>();
        getData();

/*        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Logout();
            }
        });
*/

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

  @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(FeedPage.this,MainActivity.class);
      startActivity(intent);


       /* AlertDialog alertDialog = new AlertDialog.Builder(FeedPage.this).create();
        alertDialog.setTitle("Look out!!");
        alertDialog.setMessage("Please log out before leaving!!!");
        alertDialog.show();*/

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
                        Toast.makeText(FeedPage.this,"Error in connection.Try again",Toast.LENGTH_LONG).show();
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

            final ListAdapter listAdapter = new SimpleAdapter(FeedPage.this,newsItems,R.layout.list_text_views,new String[]{"title","description"},new int[]{R.id.title,R.id.description});
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String data =((TextView) view.findViewById(R.id.description)).getText().toString();
                    Intent intent = new Intent(FeedPage.this,DetailFeed.class);
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
        manager.setPreferences(FeedPage.this,"status","0");
        Intent intent = new Intent(FeedPage.this,MainActivity.class);
        startActivity(intent);

       /* Request request1 = new Request.Builder()
                .url(BASE_URL1)
                .build();
        Call call1 = okHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FeedPage.this,"Error in connection.Try again!",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String response1 = response.body().toString();
                Log.e(TAG,response1);
                if(response1.contains("Loggedout")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FeedPage.this,"Logging out",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent logIntent = new Intent(FeedPage.this,MainActivity.class);
                    startActivity(logIntent);
                }

                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(FeedPage.this,"Error logging out.Try again",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });*/
    }

}

