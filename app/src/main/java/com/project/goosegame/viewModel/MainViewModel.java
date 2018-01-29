package com.project.goosegame.viewModel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.project.goosegame.R;
import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.ColorManager;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.MainObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/01/2018.
 */

public class MainViewModel extends BaseObservable implements ViewModel {

    private int secretCode = 3932;
    private Context context;
    private MainObservable response = null;
    private ColorManager colorManager = null;
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public MainViewModel(Context context) {
        this.context = context;
        QuestionManager.getInstance().setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
        colorManager = ColorManager.getInstance();
    }

    public void setResponse(MainObservable mainObservable) {
        this.response = mainObservable;
    }

    public boolean checkPermissions(Activity activity) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    public void verifyListQuestions() {
        new AsyncTask<Void, Void, List<Question>>() {
            @Override
            protected List<Question> doInBackground(Void... voids) {
                return QuestionManager.getInstance().getListQuestions();
            }

            @Override
            protected void onPostExecute(List<Question> questions) {
                super.onPostExecute(questions);
                if (!questions.isEmpty()) {
                    response.processStartParametersActivity();
                } else {
                    response.processDisplayMessage(context.getString(R.string.main_list_questions_error));
                }
            }
        }.execute();
    }


    public void verifySecretCode(int activity, int code) {
        if (this.secretCode == code) {
            if (activity == 2) {
                response.processStartQuestionsActivity();
            } else
                response.processStartSettingsActivity();
        } else {
            response.processDisplayMessage(context.getString(R.string.main_dialog_secret_code_error));
        }
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

