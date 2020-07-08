package com.example.iiatimd_project_1920;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private QuestionsRepository mRepository;
    private LiveData<List<Questions>> mAllQuestions;

    public QuestionViewModel(Application application){
        super(application);
        mRepository = new QuestionsRepository(application);
        mAllQuestions = mRepository.getmAllQuestions();
    }
    LiveData<List<Questions>> getmAllQuestions()
    {
        return mAllQuestions;
    }
}