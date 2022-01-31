package com.sammoore.finalproject.models;

public class Answer {
    private String answer;

    private boolean isCorrect;

    private long id;

    public Answer(AnswerModel model) {
        this.answer = model.answer;
        this.isCorrect = model.isCorrect;
        this.id = model.id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public long getId() {
        return id;
    }
}
