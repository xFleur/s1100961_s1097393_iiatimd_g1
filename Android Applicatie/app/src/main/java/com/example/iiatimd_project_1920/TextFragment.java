package com.example.iiatimd_project_1920;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.text_fragment, container, false);
        textView = v.findViewById(R.id.textField);

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("fragmentClick", "ik ben geklikt");
            }
        });

        return v;

    }

    public void setText(String text){
        textView.setText(text);
    }
}

