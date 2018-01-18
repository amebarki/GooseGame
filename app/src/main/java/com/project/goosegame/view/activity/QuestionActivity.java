package com.project.goosegame.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.goosegame.R;
import com.project.goosegame.viewModel.QuestionsViewModel;

public class QuestionActivity extends AppCompatActivity {

    private static final int INTENT_FILE_CODE = 10;
    private QuestionsViewModel questionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionsViewModel = new QuestionsViewModel(getApplicationContext());
        // TODO: 18/01/2018 binding viewModel
    }


    /*
     TODO: 18/01/2018 Button to call the function :
     startActivityForResult(mainViewModel.openFileExplorer(), INTENT_FILE_CODE);
      */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_FILE_CODE) {
            questionsViewModel.loadFile(resultCode, data);
        }
    }



}
