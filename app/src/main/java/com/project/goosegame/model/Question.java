package com.project.goosegame.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;

/**
 * Created by Adam on 17/01/2018.
 */
@Entity(tableName = "questions")
public class Question {
    @ColumnInfo(name = "id_question")
    @PrimaryKey(autoGenerate = false)
    private int idQuestion;
    @ColumnInfo(name = "type_question")
    private String type;
    @ColumnInfo(name = "niveau_question")
    private int level;
    @ColumnInfo(name = "intitule_question")
    private String title;
    @ColumnInfo(name = "reponse_juste")
    private String correctAnswer;
    @ColumnInfo(name = "reponse_fausse1")
    private String falseAnswerOne;
    @ColumnInfo(name = "reponse_fausse2")
    private String falseAnswerTwo;
    @ColumnInfo(name = "reponse_fausse3")
    private String falseAnswerThree;
    @ColumnInfo(name = "is_image")
    private int isImage;

    public Question(int idQuestion,String type, int level, String title, String correctAnswer, String falseAnswerOne,String falseAnswerTwo,String falseAnswerThree,int isImage) {

        this.idQuestion = idQuestion;
        this.title = title;
        this.type = type;
        this.level = level;
        this.correctAnswer = correctAnswer;
        this.falseAnswerOne = falseAnswerOne;
        this.falseAnswerTwo = falseAnswerTwo;
        this.falseAnswerThree = falseAnswerThree;
        this.isImage = isImage;
    }


    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getFalseAnswerOne() {
        return falseAnswerOne;
    }

    public void setFalseAnswerOne(String falseAnswerOne) {
        this.falseAnswerOne = falseAnswerOne;
    }

    public String getFalseAnswerTwo() {
        return falseAnswerTwo;
    }

    public void setFalseAnswerTwo(String falseAnswerTwo) {
        this.falseAnswerTwo = falseAnswerTwo;
    }

    public String getFalseAnswerThree() {
        return falseAnswerThree;
    }

    public void setFalseAnswerThree(String falseAnswerThree) {
        this.falseAnswerThree = falseAnswerThree;
    }

    public int getIsImage() {
        return isImage;
    }

    public void setIsImage(int isImage) {
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
                ", falseAnswerOne='" + falseAnswerOne + '\'' +
                ", falseAnswerTwo='" + falseAnswerTwo + '\'' +
                ", falseAnswerThree='" + falseAnswerThree + '\'' +
                ", isImage=" + isImage +
                '}';
    }
}
