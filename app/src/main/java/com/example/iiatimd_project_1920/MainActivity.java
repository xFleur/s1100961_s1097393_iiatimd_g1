package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.iiatimd_project_1920.Fragments.SignInFragment;


public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pauze 1,5sec
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                isFirstTime();
            }
        },1500);

    }

    private void isFirstTime(){
        SharedPreferences preferences =getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime",true);

        if(isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();

            //start Onboard
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
            finish();
        }else {
            //start auth activity
            startActivity(new Intent(MainActivity.this,AuthActivity.class));
            finish();
        }
    }
}



