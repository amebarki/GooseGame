package com.project.goosegame.model;

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
    private List<Player> playerList;


    public GooseModel(int numberPlayer, int difficulty, int numberDice, int durationGame,String typeGame) {
        this.numberPlayer = numberPlayer;
        this.difficulty = difficulty;
        this.numberDice = numberDice;
        this.durationGame = durationGame;
        this.typeGame = typeGame;
        this.playerList = new ArrayList<>();
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
}
