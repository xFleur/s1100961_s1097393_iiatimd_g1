package com.example.iiatimd_project_1920;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ButtonFragment extends Fragment {

    public Button btn;

    public OnButtonClickedListener obcl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.button_fragment, container, false);

        btn = v.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obcl.onButtonClicked();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            obcl = (OnButtonClickedListener) context;
        } catch (Exception e) {
            Log.d("wrong class", "Interface implemented?");
        }
    }

    public interface OnButtonClickedListener{
        public void onButtonClicked();
    }


}
