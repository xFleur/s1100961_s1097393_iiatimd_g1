package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuizQuestion, txtCorrectQuestion, txtWrongQuestion;
    Button btStartQuizAgain, btMainMenu;
    private long backPressedTime;

    int highScore = 0;

    //Shared preferences waar de highscore in opgeslagen is.
    private static final String SHARED_PREFERENCE = "shared_preferences";
    private static final String SHARED_PREFERENCE_HIGHSCORE = "shared_preferences_high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Instellen textview
        txtHighScore = findViewById(R.id.result_tv_HighScore);
        txtCorrectQuestion = findViewById(R.id.result_tv_correct_Ques);
        txtWrongQuestion = findViewById(R.id.result_tv_wrong_Ques);
        txtTotalQuizQuestion = findViewById(R.id.result_tv_Num_of_Ques);

        //Instellen buttons
        btStartQuizAgain = findViewById(R.id.bt_result_play_again);
        btMainMenu = findViewById(R.id.bt_result_main_menu);

        btMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btStartQuizAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        //Highscore ophalen
        loadHighScore();

        //resetScore
        //updateScore(0);

        //Ophalen van data vanuit QuizActivity
        Intent intent = getIntent();
        if(intent.hasExtra("UserScore")) {
            int score = intent.getIntExtra("UserScore", 0);
            int totalQuestion = intent.getIntExtra("TotalQuizQuestions", 0);
            int correctQuestions = intent.getIntExtra("CorrectQuestions", 0);
            int wrongQuestions = intent.getIntExtra("WrongQuestions", 0);

            txtTotalQuizQuestion.setText("Total Questions: " + String.valueOf(totalQuestion));
            txtCorrectQuestion.setText("Correct Questions: " + String.valueOf(correctQuestions));
            txtWrongQuestion.setText("Wrong Questions: " + String.valueOf(wrongQuestions));

            //Is score hoger dan highScore? Update dan de nieuwe score
            if (score > highScore){
                updateScore(score);
            }
        }
        else{
            txtTotalQuizQuestion.setText("Je bent goed bezig!");
            txtCorrectQuestion.setText("");
            txtWrongQuestion.setText("");
        }
//        int score = intent.getIntExtra("UserScore", 0);
//        int totalQuestion = intent.getIntExtra("TotalQuizQuestions", 0);
//        int correctQuestions = intent.getIntExtra("CorrectQuestions", 0);
//        int wrongQuestions = intent.getIntExtra("WrongQuestions", 0);
//
//        txtTotalQuizQuestion.setText("Total Questions: " + String.valueOf(totalQuestion));
//        txtCorrectQuestion.setText("Correct Questions: " + String.valueOf(correctQuestions));
//        txtWrongQuestion.setText("Wrong Questions: " + String.valueOf(wrongQuestions));
//
//        //Is score hoger dan highScore? Update dan de nieuwe score
//        if (score > highScore){
//            updateScore(score);
//        }
    }

    //Score updaten
    private void updateScore(int score) {
        highScore = score;

        txtHighScore.setText("High Score: " + String.valueOf(highScore));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERENCE_HIGHSCORE, highScore);
        editor.apply();
    }

    //Highscore vanuit SharedPreferences laden
    private void loadHighScore() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERENCE_HIGHSCORE, 0);

        txtHighScore.setText("High Score: " + String.valueOf(highScore));

    }

    //onBackPressed afhandeling
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
