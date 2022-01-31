package com.sammoore.finalproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sammoore.finalproject.models.AnswerModel;
import com.sammoore.finalproject.models.AnsweredQuestionModel;
import com.sammoore.finalproject.models.DatabaseSeededFlag;
import com.sammoore.finalproject.models.QuestionModel;
import com.sammoore.finalproject.models.TagModel;

import java.util.List;

@Dao
public interface QuizDao {
    @Insert
    long insertQuestion(QuestionModel questionModel);

    @Insert
    long insertAnswer(AnswerModel answerModel);

    @Insert
    long insertTag(TagModel tagModel);

    @Insert
    long insertDatabaseSeededFlag(DatabaseSeededFlag flag);

    @Query("SELECT COUNT(*) FROM database_seeded_flags")
    int getDatabaseSeededFlagCount();

    @Query("SELECT MAX(question_id) FROM answers")
    int getMaxQuestionId();

    @Query("SELECT * FROM questions")
    List<QuestionModel> getAllQuestions();

    @Query("SELECT * FROM answers")
    List<AnswerModel> getAllAnswers();

    @Query("SELECT * FROM answered_questions")
    List<AnsweredQuestionModel> getAllAnsweredQuestions();

    @Query("SELECT * FROM tags")
    List<TagModel> getAllTags();

    @Insert
    long insertAnsweredQuestion(AnsweredQuestionModel answeredQuestion);
}
