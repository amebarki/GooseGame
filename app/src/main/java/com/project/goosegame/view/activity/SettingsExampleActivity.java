package com.project.goosegame.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.AsyncQuestions;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.utils.parser.CSVFileParser;
import com.project.goosegame.viewModel.QuestionsViewModel;
import com.project.goosegame.viewModel.SettingsViewModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsExampleActivity extends AppCompatActivity implements SettingsObservable, AsyncQuestions {

    private SettingsViewModel settingsViewModel;
    private QuestionsViewModel questionsViewModel;
    private Button buttonPrimary;
    private Button buttonSecundary;
    private Button buttonSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsViewModel = new SettingsViewModel(getApplicationContext());
        settingsViewModel.setSettingsObservable(this);
        questionsViewModel = new QuestionsViewModel(this);
        questionsViewModel.setAsyncQuestions(this);
        buttonPrimary = (Button) findViewById(R.id.settings_button_primary);
        buttonSecundary = (Button) findViewById(R.id.settings_button_secundary);
        buttonSelect = (Button) findViewById(R.id.settings_button_select);

        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getPrimaryColor();
            }
        });

        buttonSecundary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getSecundaryColor();
            }
        });
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getSelectColor();
            }
        });


    }

    @Override
    public void processOpenPrimaryColor(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void processOpenSecundaryColor(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void processOpenSelectColor(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void processAddQuestionsFromCSV(Boolean result) {

    }

    @Override
    public void processCallBaseQuestions(Boolean result) {

    }

    @Override
    public void processDisplayQuestions(List<Question> questions) {

    }

    @Override
    public void processDeleteQuestions(Boolean result) {

    }

    @Override
    public void processDeleteQuestion(Boolean result) {

    }
}
