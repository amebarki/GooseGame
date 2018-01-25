package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.parser.CSVFileParser;
import com.project.goosegame.utils.observable.QuestionsObservable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Adam on 18/01/2018.
 */

public class QuestionsViewModel extends BaseObservable {
    private Context context;
    private CSVFileParser csvFileParser;
    private ArrayList<Question> questionsList;
    private QuestionsObservable response = null;
    private QuestionManager questionManager = null;

    public QuestionsViewModel(Context context) {
        questionsList = new ArrayList<>();
        this.context = context;
        questionManager = QuestionManager.getInstance();
        questionManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
    }


    public void setQuestionsObservable(QuestionsObservable questionsObservable){
        this.response = questionsObservable;
    }

    public Intent openFileExplorer() {
        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GameOfGoose" + File.separator);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/comma-separated-values");
        return intent;
    }


    public void loadFile(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            File pathFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GameOfGoose" + File.separator);
            if (!pathFolder.exists()) {
                pathFolder.mkdir();
            }
            String filePath = null;
            filePath = data.getData().getPath();
            File file = new File(filePath);
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            if (fileExtension.compareTo("csv") == 0) {
                File f = new File(pathFolder.getAbsolutePath(), file.getName());
                csvFileParser = new CSVFileParser(context,response,f);
                questionsList.addAll(csvFileParser.read(response));

                // import list question from CSV to the database
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return questionManager.removeDuplicateQuestions(questionsList);
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        super.onPostExecute(result);
                        response.processAddQuestionsFromCSV(result);
                    }
                }.execute();
            }
        }
    }

    public void importBaseQuestions() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {

                return questionManager.createQuestions();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                response.processCallBaseQuestions(aBoolean);
            }
        }.execute();
    }

    public void displayQuestions() {
        new AsyncTask<Void, Void,List<Question>>() {
            @Override
            protected List<Question> doInBackground(Void... voids) {
                questionsList.clear();
                questionsList.addAll(questionManager.getListQuestions());
                return questionsList;
            }

            @Override
            protected void onPostExecute(List<Question> result) {
                super.onPostExecute(result);
                response.processDisplayQuestions(result);
            }
        }.execute();
    }

    public void deleteQuestion(final Question question) {
        new AsyncTask<Void, Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return questionManager.deleteQuestion(question);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                response.processDeleteQuestion(result);
            }
        }.execute();
    }

    public void deleteQuestions() {
        new AsyncTask<Void, Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return questionManager.deleteListQuestions(questionsList);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                response.processDeleteQuestions(result);
            }
        }.execute();
    }
}
