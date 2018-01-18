package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.QuestionsDBManager;
import com.project.goosegame.utils.CSVFileParser;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.async.AsyncResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MainViewModel extends BaseObservable {
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;

    }

    public void launchGame() {
        Toast.makeText(context, "Open Activity GooseGame", Toast.LENGTH_SHORT).show();
    }


}


