package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.QuestionsDBManager;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.CSVFileParser;
import com.project.goosegame.utils.async.AsyncResponse;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Adam on 18/01/2018.
 */

public class QuestionsViewModel extends BaseObservable {
    // TODO: 18/01/2018 function about the suppression of list of questions or just one question 
    private Context context;
    private CSVFileParser csvFileParser;
    private ArrayList<Question> questionsList;
    public AsyncResponse response = null;


    public QuestionsViewModel(Context context){
        questionsList = new ArrayList<>();
        this.context = context;
    }


    public Intent openFileExplorer(){
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
                Log.d("TAGO", f.getAbsolutePath());
                csvFileParser = new CSVFileParser(f);
                questionsList.addAll(csvFileParser.read());
                // import list question from CSV to the database
                QuestionsDBManager.getInstance().setAppQuestionDatabase(AppQuestionDatabase.getInstance(context));
                new AsyncTask<Void, Void,Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return QuestionsDBManager.getInstance().removeDuplicateQuestions(questionsList);
                    }
                    @Override
                    protected void onPostExecute(Boolean result) {
                        super.onPostExecute(result);
                        response.processFinish(result);
                    }
                }.execute();
            }
        }
    }




}
