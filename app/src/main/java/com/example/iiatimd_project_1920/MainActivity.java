package com.example.iiatimd_project_1920;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        Sign[] sign = new Sign[5];

        sign[0] = new Sign("B6", "Voorrang", "Verleen voorrang aan bestuurders op de kruisende weg",1);
        sign[1] = new Sign("B7", "Voorrang", "Stop en verleen voorrang aan bestuurders op de kruisende weg.", 2);
        sign[2] = new Sign("B1", "Voorrang", "Voorrangsweg.", 3);
        sign[3] = new Sign("B2", "Voorrang", "Einde voorrangsweg. ", 4);
        sign[4] = new Sign("J9", "Rotonde", "Rotonde.", 5);


        recyclerViewAdapter = new SignAdapter(sign);
        recyclerView.setAdapter(recyclerViewAdapter);

        AppDatabase db = AppDatabase.getInstance(this.getApplicationContext());

        new Thread(new InsertSignTask(db, sign[2])).start();
    }
}
