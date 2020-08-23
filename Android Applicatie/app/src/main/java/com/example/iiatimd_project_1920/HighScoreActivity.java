package com.example.iiatimd_project_1920;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;



public class HighScoreActivity extends AppCompatActivity {

    private TextView dataview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);


        dataview = findViewById(R.id.textView2);


    //API: maakt singelton aan en vraag request gueue op
    RequestQueue queue = VolleySingelton.getInstance(this.getApplicationContext()).getRequestQueue();
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://mmherokuapp.herokuapp.com/api/leaderboard", null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
            try {
                Log.d("geluktdan", response.toString());
                dataview.setText(response.toString());
                 response.get("leadname").toString();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error){
            Log.d("gefaald", error.getMessage());
        }
    }
    );
        VolleySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest);

//    mAdapter = new VillagerAdapter(villagers);
//        recyclerView.setAdapter(mAdapter);




}
}