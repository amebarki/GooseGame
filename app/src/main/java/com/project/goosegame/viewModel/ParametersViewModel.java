package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.widget.Toast;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.manager.QuestionsDBManager;
import com.project.goosegame.model.GooseModel;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.async.AsyncParameters;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class ParametersViewModel extends BaseObservable {

    private Context context;
    public AsyncParameters response = null;
    private QuestionsDBManager questionsManager = null;
    private GameManager gameManager = null;

    public ParametersViewModel(Context context) {
        this.context = context;
        questionsManager = QuestionsDBManager.getInstance();
        questionsManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
        gameManager = GameManager.getInstance();

    }


    public void initGooseGame(int numberPlayer, int difficulty, int numberDice, int durationGame, String typeGame) {
        GooseModel gooseModel = new GooseModel(numberPlayer, difficulty, numberDice, durationGame, typeGame);
        gameManager.setGooseModel(gooseModel);

    }

    public void initPlayers(List<Player> playersList) {
        // TODO: 19/01/2018 create in PlayersActivity the function to launch the game and call this function to init player 
        if(playersList != null){
            gameManager.getGooseModel().setPlayerList(playersList);
        }else{
            Toast.makeText(context, "Error list empty", Toast.LENGTH_SHORT).show();
        }

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
                response.processDisplayQuestionTypeList(types);
            }
        }.execute();
    }
}
