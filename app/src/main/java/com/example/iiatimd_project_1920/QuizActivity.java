package com.example.iiatimd_project_1920;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

    List<Questions> quesList;
    Questions currentQ;

    boolean answerd = false;

    private QuestionViewModel questionViewModel;
    private ColorStateList textColorofButtons;
    private Handler handler = new Handler();

    private int questionCounter=0, questionTotalCount;
    private int correctAns = 0, wrongAns = 0;
    private int score = 0;

    private TimerDialog timerDialog;
    private WrongDialog wrongDialog;
    private CorrectDialog correctDialog;

    private int totalSizeofQuiz;

    private static final long COUNTDOWN_IN_MILLIS = 30000;
    private CountDownTimer countDownTimer;
    private long timeLeftinMillis;
    private long backPressedTime;

    //onCreate functie die alles aanroept en instelt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Het instellen van de user interface voor interactie
        setupUI();

        //Om de kleur van de knop te veranderen als deze geselecteerd is
        textColorofButtons = rb1.getTextColors();

        timerDialog = new TimerDialog(this);
        wrongDialog = new WrongDialog(this);
        correctDialog = new CorrectDialog(this);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        questionViewModel.getmAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(@Nullable List<Questions> questions) {
                fetchContent(questions);
            }
        });
        Log.i("DATATA","onCreate() in QuizActivity");
    }

    //De UI instellen voor de verschillende inputs.
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
                *   SetQuestionView methode die aangesproken bij een nieuwe vraag.
                *   Reset de UI indien er een nieuwe vraag is.
                *   Anders dan zal de methode zichzelf niet meer resetten en de Quiz stoppen.
                *   Deze methode zet alle vragen op 0 en kiest een vraag uit vanuit de Room database
                *
                *
                * */

    public void setQuestionView() {

        rbGroup.clearCheck();

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_option_bg));

        rb1.setTextColor(Color.WHITE);
        rb2.setTextColor(Color.WHITE);
        rb3.setTextColor(Color.WHITE);
        rb4.setTextColor(Color.WHITE);

        questionTotalCount = quesList.size();
        Collections.shuffle(quesList);

        // UI resetten als er een nieuwe quizvraag is.
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

        // Aanroepen als quiz afgelopen is
        } else {
            Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();

            //Zorgen dat de knoppen niet meer aanklikbaar zijn
            rb1.setClickable(false);
            rb2.setClickable(false);
            rb3.setClickable(false);
            rb4.setClickable(false);
            buttonNext.setClickable(false);
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

        //Layout van knop veranderen als deze aangeklikt is.
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

        // Vraagvalidatie
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

                    correctDialog.CorrectDialog(score, this);

                } else {
                    changetoIncorrectColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));

                    final String correctAnswer = (String)rb1.getText();
                    wrongDialog.WrongDialog(correctAnswer, this);
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

    //Countdown timer updaten en quiz stoppen als de tijd om is.
    private void updateCountDownText() {

        int minutes = (int) (timeLeftinMillis/1000) / 60;
        int seconds = (int) (timeLeftinMillis/1000) %60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDownTimer.setText(timeFormatted);

        if(timeLeftinMillis <10000){
            textViewCountDownTimer.setTextColor(Color.RED);
        }else{
            textViewCountDownTimer.setTextColor(Color.BLACK);
        }
        if(timeLeftinMillis == 0){
            Toast.makeText(this, "Time is up!", Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    timerDialog.timerDialog();
                }
            }, 2000);

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop called", "onStop() in QuizActivity");
        finish();
    }

    // Methode die de resultaatdata doorstuurt naar de ResultActivity
    private void resultData(){
        Intent resultOfQuiz = new Intent(QuizActivity.this, ResultActivity.class);
        resultOfQuiz.putExtra("UserScore", score);
        resultOfQuiz.putExtra("TotalQuizQuestions", questionTotalCount -1);
        resultOfQuiz.putExtra("CorrectQuestions", correctAns);
        resultOfQuiz.putExtra("WrongQuestions", wrongAns);
        startActivity(resultOfQuiz);
    }

    // Methode om een backpress af te handelen. Zodat de quiz niet direct gestopt is.
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else{
            Toast.makeText(this, "PRESS AGAIN TO EXIT`", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
