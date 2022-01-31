package com.sammoore.finalproject.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answered_questions",
        foreignKeys = {
        @ForeignKey(entity = QuestionModel.class,
            parentColumns = "id",
            childColumns = "question_id",
            onDelete = CASCADE
        ),
        @ForeignKey(entity = AnswerModel.class,
            parentColumns = "id",
            childColumns = "answer_selected_id",
            onDelete = CASCADE
        )
})
public class AnsweredQuestionModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "question_id")
    public long questionId;

    @ColumnInfo(name = "answer_selected_id")
    public long answerSelectedId;

    public AnsweredQuestionModel(long questionId, long answerSelectedId) {
        this.questionId = questionId;
        this.answerSelectedId = answerSelectedId;
    }
}
