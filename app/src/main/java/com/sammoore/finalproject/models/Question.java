package com.sammoore.finalproject.models;

import java.util.ArrayList;

public class Question {
    private String question;

    private ArrayList<Answer> answers = new ArrayList<>();

    private Answer correctAnswer;

    private ArrayList<Tag> tags = new ArrayList<>();

    private long id;

    public Question(QuestionModel questionModel, ArrayList<AnswerModel> answerModels, ArrayList<TagModel> tagModels) {
        this.question = questionModel.question;

        for (AnswerModel model : answerModels) {
            if (model.questionId == questionModel.id) {
                Answer a = new Answer(model);
                if (a.isCorrect()) correctAnswer = a;
                answers.add(a);
            } else {
                System.out.println("Some question is being assigned the wrong answer");
            }
        }
        for (TagModel model : tagModels) {
            if (model.questionId == questionModel.id) {
                tags.add(new Tag(model));
            } else {
                System.out.println("Some question is being assigned the wrong tag");
            }
        }

        this.id = questionModel.id;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public long getId() {
        return id;
    }
}
