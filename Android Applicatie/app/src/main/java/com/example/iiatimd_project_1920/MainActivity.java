package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;

import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.iiatimd_project_1920.Fragments.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity  {

    private String firstName = "Jeroen";
    private static final String TAG = "PlayActivity";
    private long backPressedTime;

    //SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView tv_name = findViewById(R.id.home_Welcome);
        tv_name.setText("Welkom, " + firstName);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            TextView tv = findViewById(R.id.home_Goal);
            tv.setText("To-do: " + bundle.get("leerdoel").toString());
        } else{
            TextView tv = findViewById(R.id.home_Goal);
            tv.setText("To-do: geen to-do");
        }

        Button buttonPlay = findViewById(R.id.bt_play);

        //Transitie toegevoegd voor Intent
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Transitie toegevoegd voor Intent Uitloggen
        Button buttonLogout = findViewById(R.id.bt_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        //Transitie toegevoegd voor Intent Progess
        Button buttonProgess = findViewById(R.id.bt_progress);
        buttonProgess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        //Transitie toegevoegd voor Intent Highscores
        Button buttonHighscores = findViewById(R.id.bt_highscores);
        buttonHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScoreActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }
//    private void logout(){
//
//        StringRequest request = new StringRequest(Request.Method.GET, "https://mmherokuapp.herokuapp.com/api/logout", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("HttpClient", "success! response: " + response.toString());
//                try {
//                    JSONObject object = new JSONObject(response);
//                    if(object.getBoolean("success")){
//                        JSONObject user = object.getJSONObject("user");
//                        //make shared pref user
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.clear();
//                        editor.apply();
//                        //if success
////                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
////                        startActivity(intent);
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("HttpClient", "error: " + error.toString());
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                String token = preferences.getString("token","");
//                HashMap<String, String> map = new HashMap<String, String>();
//                map.put("Authorization","Bearer"+token );
//                return map;
//            }
//
//
//        };
//        //add this request to requestqueue
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
//    }

    //onBackPressed afhandeling bij afsluiten
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){

            new AlertDialog.Builder(this)
                    .setTitle("Do you want to Exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_OK, new Intent().putExtra("Exit", true));
                            finish();
                        }
                    }).create().show();
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("DATATA", "onStop() in QuizActivity");
        finish();

    }

}



