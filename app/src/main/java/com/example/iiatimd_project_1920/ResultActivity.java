package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuizQuestion, txtCorrectQuestion, txtWrongQuestion;
    Button btStartQuizAgain, btMainMenu, btHighScore;
    private long backPressedTime;
    private ProgressDialog dialog;

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
        btHighScore = findViewById(R.id.bt_result_main_menu_upload);

        dialog = new ProgressDialog(getApplicationContext());
        dialog.setCancelable(false);


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


        btHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(ResultActivity.this);
            }
        });

        //Highscore ophalen
        loadHighScore();

        //resetScore
        //updateScore(0);

        //Ophalen van data vanuit QuizActivity
        Intent intent = getIntent();
        if (intent.hasExtra("UserScore")) {
            int score = intent.getIntExtra("UserScore", 0);
            int totalQuestion = intent.getIntExtra("TotalQuizQuestions", 0);
            int correctQuestions = intent.getIntExtra("CorrectQuestions", 0);
            int wrongQuestions = intent.getIntExtra("WrongQuestions", 0);

            txtTotalQuizQuestion.setText("Total Questions: " + String.valueOf(totalQuestion));
            txtCorrectQuestion.setText("Correct Questions: " + String.valueOf(correctQuestions));
            txtWrongQuestion.setText("Wrong Questions: " + String.valueOf(wrongQuestions));

            //Is score hoger dan highScore? Update dan de nieuwe score
            if (score > highScore) {
                updateScore(score);
            }
        } else {
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
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }


    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Highscore: " + String.valueOf(highScore) + " uploaden");
        builder.setMessage("Onder welke naam wil je op het leaderboard staan?");
        builder.setView(taskEditText);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nummer = String.valueOf(highScore);
                String name = String.valueOf(taskEditText.getText());
                Log.d("Activity", "User input : " + name + highScore);

                GoLeaderboard(nummer,name);

                //


//
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder
                .create();
        dialog.show();
    }

    /////
    private void GoLeaderboard(String score, String naam) {

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://mmherokuapp.herokuapp.com/api/save_user_score",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response on Success
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("highscore", score.toString());
                params.put("leadname", naam.toString());

                return params;
            }
        };
//        queue.add(postRequest);
        VolleySingelton.getInstance(this).addToRequestQueue(postRequest);
    }
}







