package com.project.goosegame.model.pojo;

/**
 * Created by Adam on 19/01/2018.
 */

public class Player {
  private  String name;
  private  int image;
  private  int answerTime;
  private  int currentCase;
  private  int caseMoved;
  private  int score;


    public Player(String name, int image, int answerTime) {
        this.name = name;
        this.image = image;
        this.answerTime = answerTime;
        this.currentCase = 0;
        this.caseMoved = 0;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    public int getCurrentCase() {
        return currentCase;
    }

    public void setCurrentCase(int currentCase) {
        this.currentCase = currentCase;
    }

    public int getCaseMoved() {
        return caseMoved;
    }

    public void setCaseMoved(int caseMoved) {
        this.caseMoved = caseMoved;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
