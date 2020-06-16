package com.example.iiatimd_project_1920;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

class WrongDialog {

    private Context mContext;
    private Dialog WrongDialog;

    private TextView textViewFinalScore;

    WrongDialog(Context mContext) {
        this.mContext = mContext;
    }

    void WrongDialog(String correctAns){

        WrongDialog = new Dialog(mContext);
        WrongDialog.setContentView(R.layout.wrong_dialog);
        final Button btWrongDialog = (Button) WrongDialog.findViewById(R.id.bt_wrongDialog);
        TextView textView = (TextView)WrongDialog.findViewById(R.id.textView_wrong_answer);

        textView.setText("Correct ans: " + correctAns);

        btWrongDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WrongDialog.dismiss();
            }
        });

        WrongDialog.show();
        WrongDialog.setCancelable(false);
        WrongDialog.setCanceledOnTouchOutside(false);


    }
}
