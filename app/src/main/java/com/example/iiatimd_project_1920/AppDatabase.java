package com.example.iiatimd_project_1920;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SignDAO signDAO();

    private static AppDatabase instance;

    //Altijd runnen
    abstract SignDAO questionDao();

    public static synchronized AppDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                       AppDatabase.class, "questions_database").fallbackToDestructiveMigration()
                       .addCallback(RoomDBCallback)
                       .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{


        private SignDAO questionDao;


        private PopulateDbAsyncTask(AppDatabase db){
            questionDao = db.questionDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            questionDao.insert(new Questions("What is Android?", "OS", "Browser", "Software", "Hard Drive", 1));
            return null;
        }
    }
}

