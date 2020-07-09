package com.example.iiatimd_project_1920;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions_table")
public class Questions {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "optA")
    private String optA;

    @ColumnInfo(name = "optB")
    private String optB;

    @ColumnInfo(name = "optC")
    private String optC;

    @ColumnInfo(name = "optD")
    private String optD;

    @ColumnInfo(name = "answer")
    private int answer;

    public Questions( String question, String optA, String optB, String optC, String optD, int answer) {
        this.question = question;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.answer = answer;
    }

    Questions() {
        this.id = id;
        this.question = question;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.answer = answer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptA() {
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptB() {
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptC() {
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptD() {
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}