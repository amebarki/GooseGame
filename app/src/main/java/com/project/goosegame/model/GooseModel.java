package com.project.goosegame.model;

import com.project.goosegame.model.pojo.Case;
import com.project.goosegame.model.pojo.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class GooseModel {
    private int numberPlayer;
    private int difficulty;
    private int numberDice;
    private int durationGame;
    private String typeGame;
    private int numberCase;
    private List<Player> playerList;
    private List<Case> boardGame;
    private int currentPlayer;



    public GooseModel(int numberPlayer, int difficulty, int numberDice, int durationGame, String typeGame) {
        this.numberPlayer = numberPlayer;
        this.difficulty = difficulty;
        this.numberDice = numberDice;
        this.durationGame = durationGame;
        this.typeGame = typeGame;
        this.playerList = new ArrayList<>();
        this.boardGame = new ArrayList<>();
        this.numberCase = 0;
        this.currentPlayer = 0;

    }

    public int getNumberPlayer() {
        return numberPlayer;
    }

    public void setNumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberDice() {
        return numberDice;
    }

    public void setNumberDice(int numberDice) {
        this.numberDice = numberDice;
    }

    public int getDurationGame() {
        return durationGame;
    }

    public void setDurationGame(int durationGame) {
        this.durationGame = durationGame;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public String getTypeGame() {
        return typeGame;
    }

    public void setTypeGame(String typeGame) {
        this.typeGame = typeGame;
    }

    public int getNumberCase() {
        return numberCase;
    }

    public void setNumberCase(int numberCase) {
        this.numberCase = numberCase;
    }

    public List<Case> getBoardGame() {
        return boardGame;
    }

    public void setBoardGame(List<Case> boardGame) {
        this.boardGame = boardGame;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public Player getCurrentPlayerObject(){
        return this.playerList.get(currentPlayer);
    }

    public int calculateNumberCases() {

        this.numberCase = (int) ((((float) this.getDurationGame() / (this.getMinAnswerTime() + 30))
                / this.getNumberPlayer()) * this.getNumberDice() * 3);

        if (this.numberCase < 10)
            this.numberCase = 10;

        else if (this.numberCase > 32) {
            this.numberCase = 32;
        }
        return this.numberCase;
    }


    public int getMinAnswerTime() {
        int min = 5000;
        for (int i = 0; i < this.getNumberPlayer(); i++) {
            if (this.getPlayerList().get(i).getAnswerTime() < min)
                min = this.getPlayerList().get(i).getAnswerTime();
        }
        return min;
    }


    public void createCase(float xMargin, float yMargin, int windowWidth) {

        boolean toRight = true;

        float x = 0;
        float y = 0;

        // first case
        Case start = new Case(0, false, 1, xMargin/2, yMargin/2);
        this.boardGame.add(start);

        // init the rest of the board game
        for (int i = 1; i < this.numberCase; i++) {
            Case c = new Case(i, false, 1);
            c.calculatePosition(xMargin, yMargin,
                    this.boardGame.get(i - 1).getX(), this.boardGame.get(i-1).getY(),windowWidth);

            int percent = (int) (Math.random() * 100);

            if(percent <16){
                c.setType(0); // Bonus/Malus
                c.setBonus(true);
            }
            this.boardGame.add(c);
        }
    }

/*    public void selectTypeCase(Case c){
        switch ((int) (Math.random() * 5)) {
            //contraire
            case 0:
                c.setType(0);
                break;
            //Synonyme
            case 1:
                c.setType(1);
                break;
            //Intrus
            case 2:
                c.setType(2);
                break;
            //Lecture
            case 3:
                c.setType(3);
                break;
            //Grammaire
            case 4:
                c.setType(4);
                break;
        }
    } */


}
