package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.util.Log;

import com.project.goosegame.R;
import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.ColorManager;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.GooseModel;
import com.project.goosegame.model.Question;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.observable.ParametersObservable;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class ParametersViewModel extends BaseObservable implements ViewModel{

    private Context context;
    private QuestionManager questionsManager = null;
    private GameManager gameManager = null;
    private ColorManager colorManager = null;
    private ParametersObservable response = null;

    public ParametersViewModel(Context context) {
        this.context = context;
        questionsManager = QuestionManager.getInstance();
        questionsManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
        gameManager = GameManager.getInstance();
        colorManager = ColorManager.getInstance();
    }

    public void setParametersObservable(ParametersObservable parametersObservable) {
        this.response = parametersObservable;
    }

    public void numberOfPlayers() {
        response.processNumberPlayers(gameManager.getGooseModel().getNumberPlayer());
    }

    public void initGooseGame(int numberPlayer, int difficulty, int numberDice, int durationGame, String typeGame) {
        if (typeGame != null) {
            GooseModel gooseModel = new GooseModel(numberPlayer, difficulty, numberDice, durationGame, typeGame);
            gameManager.setGooseModel(gooseModel);
            if (gameManager.getGooseModel() != null)
                this.verifyGameQuestions(typeGame, difficulty);
            else
                response.processDisplayMessage(context.getString(R.string.param_list_questions_error));
        } else {
            response.processDisplayMessage(context.getString(R.string.param_picker_error));
        }
    }

    public void initPlayers(List<Player> playersList) {
        if (playersList != null) {
            gameManager.getGooseModel().setPlayerList(playersList);
            if (gameManager.getGooseModel().getPlayerList() != null)
                response.processPlayersFinish();
        } else {
            response.processDisplayMessage(context.getString(R.string.players_message_error));
        }

    }

    public void getNumberPlayers() {
        response.processNumberPlayers(gameManager.getGooseModel().getNumberPlayer());
    }

    public void initQuestionTypeList() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return questionsManager.initQuestionTypeList();
            }

            @Override
            protected void onPostExecute(List<String> types) {
                super.onPostExecute(types);
                if (types != null && types.isEmpty() == false)
                    response.processDisplayQuestionTypeList(types);
                else
                    response.processDisplayMessage(context.getString(R.string.param_list_questions_error));
            }
        }.execute();
    }


    public void verifyGameQuestions(final String type, final int difficulty) {
        new AsyncTask<Void, Void, List<Question>>() {
            @Override
            protected List<Question> doInBackground(Void... voids) {
                return questionsManager.initGameQuestions(type, difficulty);
            }

            @Override
            protected void onPostExecute(List<Question> gameQuestionsList) {
                super.onPostExecute(gameQuestionsList);
                if (gameQuestionsList != null && gameQuestionsList.isEmpty() == false)
                    response.processParametersFinish();
                else
                    response.processDisplayMessage(context.getString(R.string.param_verify_list_questions_error));
            }
        }.execute();
    }

    public void checkPrimaryColor() {
        if(colorManager.getPrimary() != -1)
            response.processPrimaryColor(colorManager.getPrimary());

    }

    public void checkSecundaryColor() {
        if(colorManager.getSecundary() != -1)
            response.processSecundaryColor(colorManager.getSecundary());
    }

    public void checkSelectColor() {
        if(colorManager.getSelect() != -1)
            response.processSelectColor(colorManager.getSelect());
    }

}
