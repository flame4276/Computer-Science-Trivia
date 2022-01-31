package com.sammoore.finalproject.viewmodels;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.sammoore.finalproject.database.QuizDao;
import com.sammoore.finalproject.database.QuizDatabase;
import com.sammoore.finalproject.models.Answer;
import com.sammoore.finalproject.models.AnswerModel;
import com.sammoore.finalproject.models.AnsweredQuestion;
import com.sammoore.finalproject.models.AnsweredQuestionModel;
import com.sammoore.finalproject.models.DatabaseSeededFlag;
import com.sammoore.finalproject.models.Question;
import com.sammoore.finalproject.models.QuestionModel;
import com.sammoore.finalproject.models.Tag;
import com.sammoore.finalproject.models.TagModel;
import com.sammoore.finalproject.models.TagQuestionInformation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class QuizViewModel extends AndroidViewModel {
    private QuizDatabase db;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<AnsweredQuestion> answeredQuestions = new ArrayList<>();
    private ArrayList<Question> lastQuestions = new ArrayList<>();
    private HashMap<String, TagQuestionInformation> tagQuestionInformation = new HashMap<>();

    private Handler handler = new Handler();

    private final int QUESTION_REPEAT_DISTANCE = 5;

    public QuizViewModel(@NonNull Application application) {
        super(application);

        db = Room.databaseBuilder(application, QuizDatabase.class, "quizdb").build();

        new Thread(() -> {
            if (db.getQuizDao().getDatabaseSeededFlagCount() > 0) {
                loadData();
            } else {
                seedDatabase();
            }
        }).start();
    }

    private void seedDatabase() {
        String[] questions = {
                "What is the name of the operating system developed by Bell Labs beginning in the 1960s?",
                "What is the name of the operating system that most web servers use?",
                "What is the name of the commercial operating system that was in most competition with Apple in the early days of computing?",
                "Who created the C language?",
                "How long did it take for the first version of Javascript to be built?",
                "What language was built by Microsoft after they were sued by Sun Microsystems?",
                "What is considered the predecessor to the internet?",
                "Who invented the World Wide Web?",
                "What main change came with the emergence of Web 2.0?",
                "Who was Steve Jobs’ partner when Apple was founded?",
                "What is the CPU architecture used in Apple’s M1 chip?",
                "What is the CPU architecture most commonly used in desktop computers?",
                "Who wrote the first compiler?",
                "What tool is used to design the structure of web pages?",
                "What is one internet protocol used to ensure packets arrive correctly?",
                "What is the internet protocol used to identify computers on the internet?",
                "What system is used to convert human readable names to IP addresses?",
                "Where was the transistor invented?",
                "What year was google.com launched?",
                "Where was the language Java developed?",
        };
        String[][] answers = {
                { "Unix", "Linux", "Windows", "MacOS" },
                { "Linux", "Unix", "Windows", "MacOS" },
                { "Windows", "Unix", "Linux", "OpenBSD" },
                { "Dennis Ritchie", "Bill Gates", "Brandon Eich", "Steve Wozniak" },
                { "10 days", "2 years", "6 months", "12 hours" },
                { "C#", "Java", "Haskell", "Python" },
                { "ARPANET", "NPL Network", "Merit Network", "CYCLADES" },
                { "Tim Berners-Lee", "Bill Gates", "Mark Zuckerberg", "Brain Kernighan" },
                { "Websites became more interactive", "Developers began focusing on backend development", "People spent less time on the web", "The demand for web developers decreased" },
                { "Steve Wozniak", "Ken Thompson", "Bill Gates", "Larry Page" },
                { "ARM", "x86", "6502", "Alpha" },
                { "x86", "ARM", "6502", "Alpha" },
                { "Grace Hopper", "Alan Turing", "Ken Thompson", "Richard Hamming" },
                { "HTML", "CSS", "Basic", "Python" },
                { "TCP", "IP", "DNS", "HTTPS" },
                { "IP", "DNS", "TCP", "UDP" },
                { "DNS", "IP", "TCP", "UDP" },
                { "Bell Labs", "General Electric", "Western Electric", "LG" },
                { "1997", "1983", "2001", "1960" },
                { "Sun Microsystems", "Microsoft", "Google", "JetBrains" },
        };
        String[][] tags = {
                { "Companies", "Technologies" },
                { "Technologies" },
                { "Companies", "Technologies" },
                { "People", "Technologies" },
                { "Technologies" },
                { "Companies", "Technologies" },
                { "Technologies" },
                { "People", "Technologies" },
                { "Technologies" },
                { "People", "Companies" },
                { "Companies", "Technologies" },
                { "Technologies" },
                { "People", "Technologies" },
                { "Technologies" },
                { "Technologies" },
                { "Technologies" },
                { "Technologies" },
                { "Companies", "Technologies" },
                { "Companies", "Technologies" },
                { "Companies", "Technologies" },
        };
        new Thread(() -> {
            for (int i=0; i<questions.length; i++) {
                long questionId = db.getQuizDao().insertQuestion(new QuestionModel(questions[i]));
                for (int j=0; j<answers[i].length; j++) {
                    db.getQuizDao().insertAnswer(new AnswerModel(questionId, answers[i][j], j==0));
                }
                System.out.println("Inserted answers");
                for (int j=0; j<tags[i].length; j++) {
                    db.getQuizDao().insertTag(new TagModel(questionId, tags[i][j]));
                }
            }
            db.getQuizDao().insertDatabaseSeededFlag(new DatabaseSeededFlag());
            loadData();
        }).start();
    }

    public void loadData() {
        new Thread(() -> {
            QuizDao quizDao = db.getQuizDao();

            // Load database data into cache
            List<QuestionModel> questions = quizDao.getAllQuestions();
            int maxId = quizDao.getMaxQuestionId();
            List<AnswerModel> answers = quizDao.getAllAnswers();
            List<AnsweredQuestionModel> answeredQuestions = quizDao.getAllAnsweredQuestions();
            List<TagModel> tags = quizDao.getAllTags();

            // Bucket sort of AnswerModel's by question id
            ArrayList<AnswerModel>[] sortedAnswerModels = new ArrayList[maxId+1];
            for (int i=0; i<sortedAnswerModels.length; i++) {
                sortedAnswerModels[i] = new ArrayList<AnswerModel>();
            }
            for (AnswerModel answerModel : answers) {
                sortedAnswerModels[(int)answerModel.questionId].add(answerModel);
            }
            //Bucket sort of TagModel's by question id
            ArrayList<TagModel>[] sortedTagModels = new ArrayList[maxId+1];
            for (int i=0; i<sortedTagModels.length; i++) {
                sortedTagModels[i] = new ArrayList<TagModel>();
            }
            for (TagModel tagModel : tags) {
                sortedTagModels[(int)tagModel.questionId].add(tagModel);
            }

            // Using sorted AnswerModel and TagModel data to get database data into more usable format for Question's
            // Bucket sort of QuestionModel's by id
            QuestionModel[] sortedQuestionModels = new QuestionModel[maxId+1];
            for (QuestionModel questionModel : questions) {
                Question q = new Question(questionModel, sortedAnswerModels[(int)questionModel.id], sortedTagModels[(int)questionModel.id]);
                this.questions.add(q);
                sortedQuestionModels[(int)questionModel.id] = questionModel;
            }

            // Using sorted AnswerModel, TagModel, QuestionModel data to get database data into for usable format for AnsweredQuestion's
            for (AnsweredQuestionModel answeredQuestionModel : answeredQuestions) {
                AnsweredQuestion q = new AnsweredQuestion(answeredQuestionModel,
                        sortedQuestionModels[(int)answeredQuestionModel.questionId],
                        sortedAnswerModels[(int)sortedQuestionModels[(int)answeredQuestionModel.questionId].id],
                        sortedTagModels[(int)sortedQuestionModels[(int)answeredQuestionModel.questionId].id]
                );
                this.answeredQuestions.add(q);
            }

            // Construct tag info HashMap
            for (AnsweredQuestion question : this.answeredQuestions) {
                System.out.println("Checking on an answered question");
                for (Tag tag : question.getQuestion().getTags()) {
                    System.out.println("Checking for tag " + tag.getTagName());
                    if (!tagQuestionInformation.containsKey(tag.getTagName())) {
                        System.out.println("Adding tag " + tag.getTagName() + " to the map");
                        tagQuestionInformation.put(tag.getTagName(), new TagQuestionInformation(tag.getTagName()));
                    }
                    if (question.isCorrect()) {
                        tagQuestionInformation.get(tag.getTagName()).addCorrectQuestionToCount();
                    }
                    else {
                        tagQuestionInformation.get(tag.getTagName()).addIncorrectQuestionToCount();
                    }
                }
            }
            System.out.println("Tag map created");
        }).start();
    }

    // returns the next question in the quiz
    public Question getNextQuestion() {
        if (questions.size() == 0) return null;

        Question q;
        do {
            q = questions.get((int)(Math.random()*questions.size()));
        } while(lastQuestions.contains(q));

        lastQuestions.add(0, q);
        if (lastQuestions.size() > QUESTION_REPEAT_DISTANCE) lastQuestions.remove(lastQuestions.size()-1);

        return q;
    }

    public boolean gradeQuestion(Question question, Answer selectedAnswer) {
        insertAnsweredQuestion(question, selectedAnswer);
        return selectedAnswer.getId() == question.getCorrectAnswer().getId();
    }

    private void insertAnsweredQuestion(Question question, Answer selectedAnswer) {
        AnsweredQuestion q = new AnsweredQuestion(question, selectedAnswer);
        answeredQuestions.add(q);

        for (Tag tag : q.getQuestion().getTags()) {
            if (!tagQuestionInformation.containsKey(tag.getTagName())) tagQuestionInformation.put(tag.getTagName(), new TagQuestionInformation(tag.getTagName()));
            if (q.isCorrect()) tagQuestionInformation.get(tag.getTagName()).addCorrectQuestionToCount();
            else tagQuestionInformation.get(tag.getTagName()).addIncorrectQuestionToCount();
        }

        new Thread(() -> {
            db.getQuizDao().insertAnsweredQuestion(new AnsweredQuestionModel(question.getId(), selectedAnswer.getId()));
        }).start();
    }

    public HashMap<String, TagQuestionInformation> getTagQuestionInformation() {
        return tagQuestionInformation;
    }
}
