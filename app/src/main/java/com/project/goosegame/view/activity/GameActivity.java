package com.project.goosegame.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.project.goosegame.model.Question;
import com.project.goosegame.model.pojo.Case;
import com.project.goosegame.utils.async.GameInterface;
import com.project.goosegame.viewModel.GameViewModel;

import java.util.List;

/**
 * Created by Adam on 22/01/2018.
 */

public class GameActivity extends AppCompatActivity implements GameInterface {

private GameViewModel gameViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        int windowheight = getWindowManager().getDefaultDisplay().getHeight();
        gameViewModel  = new GameViewModel(getApplicationContext());
        gameViewModel.response = this;
        gameViewModel.initGameQuestions();
        gameViewModel.createCases(windowheight,windowwidth);
        gameViewModel.startTimer();
        gameViewModel.newTurn(); // start the first turn with player 0
    }

    @Override
    public void processGameQuestions(List<Question> gameQuestionsList) {
        // TODO: 22/01/2018 recup the list of question to store for the game
    }

    @Override
    public void processGooseCases(List<Case> boardGame) {
        // TODO: 22/01/2018 recup the boardgame null or full of cases, need to verify 
    }

    @Override
    public void processDisplayTime(String time) {
        // TODO: 22/01/2018 display the time of the game and also id the game is finish
    }

    @Override
    public void processShowQuestion(Question q, int positionResponse) {
        // TODO: 22/01/2018 recup the question to show on the activity with its answers
        // TODO: 22/01/2018 correct answer will be show at the positionResponse did randomly in the gameViewModel
        // TODO: 22/01/2018 il will probably need onclick listener to call the function verify answer here with the string answer has parameter
        gameViewModel.verifyAnswer("",q);
    }

    @Override
    public void processShowResultQuestion(String result, boolean isCorrect) {
        // TODO: 22/01/2018 display the result of the verification of the question
        // TODO: 22/01/2018 create OnClick listener button to validate the move send isCorrect boolean has parameters
    }

    @Override
    public void processLaunchBonusMalus(int visibility, String resultTitle, String result) {
        // TODO: 22/01/2018 if case is not a question but a bonus/malus, just need to show the result and display it with a button
        // TODO: 22/01/2018 the button will validate the bonus/malus it will call the function :
        //gameViewModel.startBonusMalus();
    }

    @Override
    public void processDisplayDicePlayer(int visibility, String namePlayer) {
        // TODO: 22/01/2018 recup visibility and nameplayer
        // TODO: 22/01/2018 create button which call :
         //gameViewModel.ThrowDice();
    }

    @Override
    public void processDisplayResultDice(int visibility, String resultDice) {
        // TODO: 22/01/2018 display result of the throw dice, only this action 

    }

    @Override
    public void processDisplayEnd(int visibility, String endText, boolean isOver) {
        // TODO: 22/01/2018 with boolean isOver true : end of game, false end of turn for a player 
        // TODO: 22/01/2018 create button to finish activity if true 
    }

    @Override
    public void processShowDialog(String title, String text) {
        // String parameters
            // Display no questions alerteDialog
    }


}
