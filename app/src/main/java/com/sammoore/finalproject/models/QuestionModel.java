package com.sammoore.finalproject.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class QuestionModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String question;

    public QuestionModel(String question) {
        this.question = question;
    }

    public QuestionModel() {}
}
