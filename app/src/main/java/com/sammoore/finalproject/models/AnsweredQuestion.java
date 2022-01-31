package com.sammoore.finalproject.models;

import java.util.ArrayList;

public class AnsweredQuestion {
    private Question question;

    private Answer selectedAnswer;

    public AnsweredQuestion(AnsweredQuestionModel answeredQuestionModel, QuestionModel questionModel, ArrayList<AnswerModel> answerModels, ArrayList<TagModel> tagModels) {
        if (answeredQuestionModel.questionId == questionModel.id) {
            question = new Question(questionModel, answerModels, tagModels);
            for (Answer a : question.getAnswers()) {
                if (a.getId() == answeredQuestionModel.answerSelectedId) selectedAnswer = a;
            }
        } else {
            System.out.println("An answered question is being assigned the wrong question");
        }
    }

    public AnsweredQuestion(Question question, Answer selectedAnswer) {
        this.question = question;
        this.selectedAnswer = selectedAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    public boolean isCorrect() {
        return selectedAnswer.isCorrect();
    }
}
