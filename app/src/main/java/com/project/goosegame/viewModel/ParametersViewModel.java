package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;

import com.project.goosegame.R;
import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.GooseModel;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.observable.ParametersObservable;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class ParametersViewModel extends BaseObservable {

    private Context context;
    private QuestionManager questionsManager = null;
    private GameManager gameManager = null;
    private ParametersObservable response = null;

    public ParametersViewModel(Context context) {
        this.context = context;
        questionsManager = QuestionManager.getInstance();
        questionsManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
        gameManager = GameManager.getInstance();

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
                response.processParametersFinish(true);
            response.processParametersFinish(false);
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
}
