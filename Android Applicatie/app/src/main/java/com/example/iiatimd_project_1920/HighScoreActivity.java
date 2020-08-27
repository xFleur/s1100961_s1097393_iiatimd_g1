package com.example.iiatimd_project_1920;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.textfield.TextInputLayout;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HighScoreActivity extends AppCompatActivity{


    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        Button buttonParse = findViewById(R.id.parseButton);


        jsonCall();

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Datacon.checkInternetConnection(HighScoreActivity.this)){
                    jsonCall();
                }
                else{
                    Context context_new = getApplicationContext();
                    CharSequence text = "Er is geen internet geconstateerd. Probeer te verbinden.";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context_new, text, duration);
                    toast.show();
                }
                Toast.makeText(HighScoreActivity.this, "We refreshen de tabel voor je", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void jsonCall(){
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        BasicNetwork network = new BasicNetwork(new HurlStack());
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarChart barChart = findViewById(R.id.barchart);

        RequestQueue request  = new RequestQueue(cache, network);
        request.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://mmherokuapp.herokuapp.com/api/leaderboard", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Vraag alle posts op en stop ze in een JSONArray
                    JSONArray jsonArray = response.getJSONArray("posts");
                    Log.d("dit is de JSONArray", jsonArray.toString());

                    //Maak een Lijst aan van de Array. Dit is nodig om het te kunnen sorteren.
                    List<JSONObject> jsonList = new ArrayList<JSONObject>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        jsonList.add(jsonArray.getJSONObject(i));
                    }

                    //Sorteer de ontvangen lijst op highscore in SortedBasedOnHighScore()
                    Collections.sort(jsonList, new SortedBasedOnHighScore());
                    Collections.reverse(jsonList);

                    Log.d("dit is gesorteerd", jsonList.toString());

                    JSONArray jsArray = new JSONArray(jsonList);

                    Log.d("JSON", jsArray.toString());

                    //Voor elke instantie in de JSONArray, loop deze door.
                    for(int i = 0; i < jsArray.length(); i++) {
                        JSONObject posts = jsArray.getJSONObject(i);
                        String name = posts.getString("leadname");
                        int highscore = posts.getInt("highscore");
                        Log.d("Instance", posts.toString());

                        nameList.add(name);
                        barEntries.add(new BarEntry(i, highscore));
                    }

                    //Maak de barchart met de ontvangen data aan
                    BarDataSet barDataSet = new BarDataSet(barEntries, "All time highscore");
                    barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(nameList));
                    barChart.getXAxis().setGranularityEnabled(true);
                    BarData barData = new BarData(barDataSet);
                    barChart.getDescription().setEnabled(false);
                    barChart.setData(barData);
                    barChart.invalidate();
                    barChart.notifyDataSetChanged();
                    barChart.setVisibleXRangeMaximum(5);


                    Toast.makeText(HighScoreActivity.this, "De highscore is opgehaald!", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        request.add(jsonObjectRequest);


        barChart.getXAxis().setGranularityEnabled(true);
        barChart.setVisibleXRangeMaximum(10);

    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{
            Toast.makeText(this, "PRESS AGAIN TO EXIT`", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();

    
    }



}


