package com.example.iiatimd_project_1920;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerDialog {

    private Context mContext;
    private Dialog finalScoreDialog;
    private QuizActivity quizActivityob;


    TimerDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void timerDialog(){

        finalScoreDialog = new Dialog(mContext);
        quizActivityob = new QuizActivity();

        finalScoreDialog.setContentView(R.layout.timer_dialog);

        final Button btFinalScoreDialog = (Button) finalScoreDialog.findViewById(R.id.bt_timerDialog);

        btFinalScoreDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalScoreDialog.dismiss();
                Intent intent = new Intent(mContext, PlayActivity.class);
                mContext.startActivity(intent);
            }
        });

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);
        finalScoreDialog.setCanceledOnTouchOutside(false);


    }
}
