package com.example.iiatimd_project_1920;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreDialog {

    private Context mContext;
    private Dialog finalScoreDialog;
    private QuizActivity quizActivityob;

    private TextView textViewFinalScore;

    public FinalScoreDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void finalScoreDialog(int correctAns, int wrongAns, int totalSizeofQuiz){

        finalScoreDialog = new Dialog(mContext);
        quizActivityob = new QuizActivity();

        finalScoreDialog.setContentView(R.layout.final_score_dialog);

        final Button btFinalScoreDialog = (Button) finalScoreDialog.findViewById(R.id.bt_finalDialog);

        finalScore(correctAns, wrongAns, totalSizeofQuiz);

        btFinalScoreDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalScoreDialog.dismiss();
                Intent intent = new Intent(mContext, QuizActivity.class);
                mContext.startActivity(intent);
            }
        });

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);


    }

    private void finalScore(int correctAns, int wrongAns, int totalSizeofQuiz) {
        int tempScore = 0;
        textViewFinalScore = (TextView)finalScoreDialog.findViewById(R.id.textView_final_score);

        if(correctAns == totalSizeofQuiz){
            /*
            * 20 als het goed is, als het fout is 5 punten minder;
            *
            * */
            tempScore = (correctAns * 20) - (wrongAns * 5);
            textViewFinalScore.setText("Final Score : " + String.valueOf(tempScore));

        }else if (wrongAns == totalSizeofQuiz){

            tempScore = 0;
            textViewFinalScore.setText("Final Score : " + String.valueOf(tempScore));

        }else if(correctAns > wrongAns){
            tempScore = (correctAns * 20) - (wrongAns * 5);
            textViewFinalScore.setText("Final Score : " + String.valueOf(tempScore));

        }else if(wrongAns > correctAns){

            tempScore = (wrongAns * 5) - (correctAns * 20);
            textViewFinalScore.setText("Final Score : " + String.valueOf(tempScore));

        }else if(wrongAns == correctAns){
            tempScore = (correctAns * 20) - (wrongAns * 5);
            textViewFinalScore.setText("Final Score : " + String.valueOf(tempScore));
        }

    }
}
