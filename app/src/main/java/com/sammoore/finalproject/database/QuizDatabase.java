package com.sammoore.finalproject.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sammoore.finalproject.models.*;

@Database(entities = {
        QuestionModel.class,
        AnswerModel.class,
        AnsweredQuestionModel.class,
        TagModel.class,
        DatabaseSeededFlag.class
}, version = 1)
public abstract class QuizDatabase extends RoomDatabase {
    public abstract QuizDao getQuizDao();
}
