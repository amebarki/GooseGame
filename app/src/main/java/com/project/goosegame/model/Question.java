package com.project.goosegame.model;

import java.util.Arrays;

/**
 * Created by Adam on 17/01/2018.
 */

public class Question {
    private int idQuestion;
    private String title;
    private String type;
    private int level;
    private String correctAnswer;
    private String[] falseAnswers;
    private int isImage;

    public Question(int idQuestion,String type, int level, String title, String correctAnswer, String[] falseAnswers,int isImage) {
        this.idQuestion = idQuestion;
        this.title = title;
        this.type = type;
        this.level = level;
        this.correctAnswer = correctAnswer;
        this.falseAnswers = falseAnswers;
        this.isImage = isImage;
    }


    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", level=" + level +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", falseAnswers=" + Arrays.toString(falseAnswers) +
                ", isImage=" + isImage +
                '}';
    }
}
