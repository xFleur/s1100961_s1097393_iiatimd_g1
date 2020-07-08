package com.example.iiatimd_project_1920;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionsRepository {

    private SignDAO mQuestionDao;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionsRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        mQuestionDao = db.questionDao();
        mAllQuestions = mQuestionDao.getAllQuestions();
    }

    public LiveData<List<Questions>> getmAllQuestions() {
        return mAllQuestions;
    }
}
