package com.example.iiatimd_project_1920;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.io.File;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private final static int EXIT_CODE = 100;

    ImageView  imageViewSplashLogo;
    TextView textViewGoQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewSplashLogo = findViewById(R.id.splash_imgView);
        textViewGoQuiz = findViewById(R.id.splash_logo_text);

        Picasso.get().load("https://www.theorieexamen.nl/cdn/b2/j1.png").into(imageViewSplashLogo);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.transitions);
        imageViewSplashLogo.setAnimation(animation);
        textViewGoQuiz.setAnimation(animation);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    sleep(3000);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    GoToPlayActivity();
                }


            }
        });
        thread.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EXIT_CODE){
            if(resultCode == RESULT_OK){
                if(data.getBooleanExtra("EXIT", true)){
                    finish();
                }
            }
        }


    }

    private void GoToPlayActivity() {
        startActivityForResult(new Intent(SplashActivity.this, MainActivity.class), EXIT_CODE);
        //startActivityForResult(new Intent(SplashActivity.this, AuthActivity.class), EXIT_CODE);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("DATATA", "onStop() in QuizActivity");
        finish();
    }


}
