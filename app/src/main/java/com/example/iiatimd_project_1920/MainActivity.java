package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toLoginCheckScreen = findViewById(R.id.btnLoginGo);
        toLoginCheckScreen.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent toLoginCheckScreenIntent = new Intent(this, LoginActivity.class);
        startActivity(toLoginCheckScreenIntent);


    }
}
