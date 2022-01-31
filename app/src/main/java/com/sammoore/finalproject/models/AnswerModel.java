package com.sammoore.finalproject.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers",
        foreignKeys = { @ForeignKey(
                entity = QuestionModel.class,
                parentColumns = "id",
                childColumns = "question_id",
                onDelete = CASCADE)
})
public class AnswerModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "question_id")
    public long questionId;

    @ColumnInfo
    public String answer;

    @ColumnInfo(name = "is_correct")
    public boolean isCorrect;

    public AnswerModel(long questionId, String answer, boolean isCorrect) {
        this.questionId = questionId;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public AnswerModel() {}
}
