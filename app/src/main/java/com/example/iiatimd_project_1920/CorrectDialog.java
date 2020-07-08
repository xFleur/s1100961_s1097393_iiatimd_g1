package com.example.iiatimd_project_1920;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CorrectDialog {

    private Context mContext;
    private Dialog correctDialog;
    private QuizActivity mquizActivity;
    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void CorrectDialog(int score, QuizActivity quizActivity){

        mquizActivity = quizActivity;
        correctDialog = new Dialog(mContext);
        correctDialog.setContentView(R.layout.correct_dialog);

        final Button btcorrectDialog = (Button) correctDialog.findViewById(R.id.bt_correctDialog);
        Score(score);

        btcorrectDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctDialog.dismiss();
                mquizActivity.setQuestionView();
            }
        });
        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);
    }

    private void Score(int score){
        TextView textScore = (TextView)correctDialog.findViewById(R.id.textView_score);
        textScore.setText("Score: " + String.valueOf(score));
    }
}
