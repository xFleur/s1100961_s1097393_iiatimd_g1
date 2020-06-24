package com.example.iiatimd_project_1920;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {


    TextView txtQuestion;
    TextView textViewScore, textViewQuestionCount, textViewCountDownTimer;
    TextView textViewCorrect, textViewWrong;

    RadioButton rb1, rb2, rb3, rb4;
    RadioGroup rbGroup;
    Button buttonNext;

    boolean answerd = false;

    List<Questions> quesList;
    Questions currentQ;

    private int questionCounter=0, questionTotalCount;

    private QuestionViewModel questionViewModel;

    private ColorStateList textColorofButtons;

    private Handler handler = new Handler();

    private int correctAns = 0, wrongAns = 0;

    private int score = 0;

    private FinalScoreDialog finalScoreDialog;
    private WrongDialog wrongDialog;
    private CorrectDialog correctDialog;

    private int totalSizeofQuiz;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        setupUI();

        textColorofButtons = rb1.getTextColors();  // this is used to change the text colors of the buttons

        finalScoreDialog = new FinalScoreDialog(this);
        wrongDialog = new WrongDialog(this);
        correctDialog = new CorrectDialog(this);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(@Nullable List<Questions> questions) {
                Toast.makeText(QuizActivity.this, "Get IT :)", Toast.LENGTH_SHORT).show();

                fetchContent(questions);

            }
        });
        Log.i("DATATA","onCreate() in QuizActivity");

    }

    void setupUI() {
        textViewScore = findViewById(R.id.txtScore);
        textViewCountDownTimer = findViewById(R.id.txtTimer);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        txtQuestion = findViewById(R.id.txtQuestionContainer);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);

        buttonNext = findViewById(R.id.button_Next);
    }

    private void fetchContent(List<Questions> questions) {
        quesList = questions;
        startQuiz();
    }

                /*
                *
                *   SetQuestionView metho
                *
                * */

    public void setQuestionView() {

        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        questionTotalCount = quesList.size();
        Collections.shuffle(quesList);

        if (questionCounter < questionTotalCount - 1) {

            currentQ = quesList.get(questionCounter);

            txtQuestion.setText(currentQ.getQuestion());
            rb1.setText(currentQ.getOptA());
            rb2.setText(currentQ.getOptB());
            rb3.setText(currentQ.getOptC());
            rb4.setText(currentQ.getOptD());


            questionCounter++;
            answerd = false;
            buttonNext.setText("Confirm");
            textViewQuestionCount.setText("Questions : " + questionCounter + "/" + (questionTotalCount - 1));

            timeLeftinMillis = COUNTDOWN_IN_MILLIS;

            startCountdown();

        } else {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultData();
                }
            }, 2000);

        }


    }



    private void startQuiz() {

        setQuestionView();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.radio_button1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        break;

                    case R.id.radio_button2:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        break;

                    case R.id.radio_button3:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));

                        break;
                    case R.id.radio_button4:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_selected));

                        break;
                }

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!answerd) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        quizOperation();


                    } else {
                        Toast.makeText(QuizActivity.this, "Please Select Answer", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }



    private void quizOperation() {


        answerd = true;

        countDownTimer.cancel();

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());

        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);

    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        switch (currentQ.getAnswer()) {
            case 1:
                if (currentQ.getAnswer() == answerNr) {
                    rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb1.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10; //score toevoegen
                    textViewScore.setText("Score: " + String.valueOf(score));


                } else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    final String correctAnswer = (String)rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer, this);

                    correctDialog.CorrectDialog(score, this);

                }
                break;
            case 2:
                if (currentQ.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb2.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10; //score toevoegen
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.CorrectDialog(score, this);


                } else {
                    changetoIncorrectColor(rbselected);
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    final String correctAnswer = (String)rb2.getText();
                    wrongDialog.WrongDialog(correctAnswer, this);


                }
                break;
            case 3:
                if (currentQ.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb3.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10; //score toevoegen
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.CorrectDialog(score, this);


                } else {
                    changetoIncorrectColor(rbselected);
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    final String correctAnswer = (String)rb3.getText();
                    wrongDialog.WrongDialog(correctAnswer, this);

                }
                break;
            case 4:
                if (currentQ.getAnswer() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_correct));
                    rb4.setTextColor(Color.WHITE);

                    correctAns++;
                    textViewCorrect.setText("Correct: " + String.valueOf(correctAns));

                    score += 10; //score toevoegen
                    textViewScore.setText("Score: " + String.valueOf(score));

                    correctDialog.CorrectDialog(score, this);



                } else {
                    changetoIncorrectColor(rbselected);
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    final String correctAnswer = (String)rb4.getText();
                    wrongDialog.WrongDialog(correctAnswer, this);

                }
                break;

        }

        if(questionCounter  == questionTotalCount ){
            buttonNext.setText("Finish Quiz");
        }
    }


    private void changetoIncorrectColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.when_answer_wrong));
        rbselected.setTextColor(Color.WHITE);
    }

    private void startCountdown() {
     countDownTimer = new CountDownTimer(timeLeftinMillis, 1000) {
         @Override
         public void onTick(long millisUntilFinished) {
            timeLeftinMillis = millisUntilFinished;
            updateCountDownText();
         }

         @Override
         public void onFinish() {
             timeLeftinMillis = 0;
             updateCountDownText();
         }
     }.start();
    }

    private void updateCountDownText() {

        int minutes = (int) (timeLeftinMillis/1000) / 60;
        int seconds = (int) (timeLeftinMillis/1000) %60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDownTimer.setText(timeFormatted);

        if(timeLeftinMillis <10000){
            textViewCountDownTimer.setTextColor(Color.RED);

        }else{
            textViewCountDownTimer.setTextColor(textColorofButtons);
        }

        if(timeLeftinMillis == 0){
            Toast.makeText(this, "Time is up!", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                    startActivity(intent);
                }
            }, 2000);

        }

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("DATATA", "onStop() in QuizActivity");
        finish();
    }

    private void resultData(){
        Intent resultOfQuiz = new Intent(QuizActivity.this, ResultActivity.class);
        resultOfQuiz.putExtra("UserScore", score);
        resultOfQuiz.putExtra("TotalQuizQuestions", questionTotalCount -1);
        resultOfQuiz.putExtra("CorrectQuestions", correctAns);
        resultOfQuiz.putExtra("WrongQuestions", wrongAns);
        startActivity(resultOfQuiz);
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){

            Intent intent = new Intent(QuizActivity.this, PlayActivity.class);
        }else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
