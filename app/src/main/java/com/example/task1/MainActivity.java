package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import android.os.Handler;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String JSON_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    ListView listView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    ArrayList<String> valuteName;
    ArrayList< JSONObject> listItems = new ArrayList<JSONObject>();;
    int swipecount = 0;
    JSONObject object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if (swipecount == 0){
            loadJSONFromURL(JSON_URL);
        }


        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        valuteName = new ArrayList<String>();
        valuteName.add("AUD");
        valuteName.add("AZN");
        valuteName.add("GBP");
        valuteName.add("AMD");
        valuteName.add("BYN");
        valuteName.add("BGN");
        valuteName.add("BRL");
        valuteName.add("HUF");
        valuteName.add("HKD");
        valuteName.add("DKK");
        valuteName.add("USD");
        valuteName.add("EUR");
        valuteName.add("INR");
        valuteName.add("KZT");
        valuteName.add("CAD");
        valuteName.add("KGS");
        valuteName.add("CNY");
        valuteName.add("MDL");
        valuteName.add("NOK");
        valuteName.add("PLN");
        valuteName.add("RON");
        valuteName.add("XDR");
        valuteName.add("SGD");
        valuteName.add("TJS");
        valuteName.add("TRY");
        valuteName.add("TMT");
        valuteName.add("UZS");
        valuteName.add("UAH");
        valuteName.add("CZK");
        valuteName.add("SEK");
        valuteName.add("CHF");
        valuteName.add("ZAR");
        valuteName.add("KRW");
        valuteName.add("JPY");
        

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String charNameIntent = "";
                String nameIntent = "";
                double valueIntent = 1.1;
                try {
                    charNameIntent = listItems.get(position).getString("CharCode");
                    nameIntent = listItems.get(position).getString("Name");
                    valueIntent = listItems.get(position).getDouble("Value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),ConvertActivity.class);
                intent.putExtra("charCodeIntent",charNameIntent);
                intent.putExtra("nameIntent",nameIntent);
                intent.putExtra("valueIntent",valueIntent);
                startActivity(intent);
            }
        });

        //Автоматическое обновление
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadJSONFromURL(JSON_URL);
                handler.postDelayed(this,60000);//60 second delay
            }
        };handler.postDelayed(runnable, 60000);
    }
    //Обновление по свайпу вниз
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);
                loadJSONFromURL(JSON_URL);
            }

        }, 500);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try {
            String jsonString = savedInstanceState.getString("savedValute");
            JSONObject obj = new JSONObject(jsonString);
            loadJson(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("savedValute",object.toString());
        swipecount++;
    }

    private void loadJSONFromURL(String url){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
        new Response.Listener< String>(){
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    object = new JSONObject(response);
                    loadJson(object);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void loadJson(JSONObject obj) throws JSONException {
        JSONObject jsonObject = obj.getJSONObject("Valute");
        listItems.clear();
        for(int i = 0; i<jsonObject.length();i++){
            listItems.add(jsonObject.getJSONObject(valuteName.get(i)));
        }
        ListAdapter adapter = new ListViewAdapter(getApplicationContext(),R.layout.row,R.id.textViewName,listItems);
        listView.setAdapter(adapter);
    }
    //Обновление по кнопке
    public void btUpdateValute(View view) {
        loadJSONFromURL(JSON_URL);
    }

}