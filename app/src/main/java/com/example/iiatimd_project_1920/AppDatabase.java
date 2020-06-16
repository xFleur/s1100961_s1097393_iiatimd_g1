package com.example.iiatimd_project_1920;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {


    private static AppDatabase INSTANCE;


    public abstract SignDAO questionDao();

    public static synchronized AppDatabase getInstance(final Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }

        return INSTANCE;

    }

    private static RoomDatabase.Callback RoomDBCallback = new RoomDatabase.Callback(){


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{

        private SignDAO questionDao;


        private PopulateDbAsyncTask(AppDatabase db){

            questionDao = db.questionDao();

        }

        @Override
        protected Void doInBackground(Void... voids){


            questionDao.insert(new Questions("What is Android?","OS","Browser","Software","Hard Drive",1));
            questionDao.insert(new Questions("RAM Stands for what ?","Operating System","Browser","Random Access Memory","CD Project",3));
            questionDao.insert(new Questions("Chrome is what ?","System Software","Browser","Middle Ware","Windows",2));
            questionDao.insert(new Questions("HTML is what ?","Scipting Language","Programming Language","Software","Hyper Text Markup Language",4));
            questionDao.insert(new Questions("Unity is used for ?","Game Developement","Web Development","Graphics Design","3-D Modling",2));
            questionDao.insert(new Questions("What is OS","Hardware","System Software","PC Software","Hard Drive",2));
            questionDao.insert(new Questions("IP stand for what? ","Language","Intenet Protocol","Graphics","Random",2));
            return null;
        }
    }
}

