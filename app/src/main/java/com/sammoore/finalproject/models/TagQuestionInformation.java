package com.sammoore.finalproject.models;

public class TagQuestionInformation {
    private String tagName;
    private int numQuestions = 0;
    private int numQuestionsCorrect = 0;

    public TagQuestionInformation(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public double getPercentageCorrect() {
        return numQuestionsCorrect/((float)numQuestions);
    }

    public void addCorrectQuestionToCount() {
        numQuestions++;
        numQuestionsCorrect++;
    }
    public void addIncorrectQuestionToCount() {
        numQuestions++;
    }
}
