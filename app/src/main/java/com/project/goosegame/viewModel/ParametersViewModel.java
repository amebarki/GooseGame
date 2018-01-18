package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.QuestionsDBManager;
import com.project.goosegame.utils.async.AsyncParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class ParametersViewModel extends BaseObservable {

    private Context context;
    public AsyncParameters response = null;
    private QuestionsDBManager questionsManager = null;

    public ParametersViewModel(Context context) {
        this.context = context;
        questionsManager = QuestionsDBManager.getInstance();
        questionsManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(context))
        ;
    }



    public void getQuestionTypes()
    {
        new AsyncTask<Void, Void,List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return questionsManager.getQuestionTypes();
            }

            @Override
            protected void onPostExecute(List<String> types) {
                super.onPostExecute(types);
                response.processDisplayQuestionTypes(types);
            }
        }.execute();
    }
}
