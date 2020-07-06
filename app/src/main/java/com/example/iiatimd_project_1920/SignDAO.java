package com.example.iiatimd_project_1920;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


//Dao voor de Room database
@Dao
public interface SignDAO{

    //Alle vragen ophalen vanuit Questions
    @Query("SELECT * FROM questions_table")
    LiveData<List<Questions>> getAllQuestions();

    @Insert
    void insert(Questions questions);

}
