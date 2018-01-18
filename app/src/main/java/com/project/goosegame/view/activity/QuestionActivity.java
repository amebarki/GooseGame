package com.project.goosegame.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.async.AsyncResponse;
import com.project.goosegame.viewModel.QuestionsViewModel;

import java.util.List;

public class QuestionActivity extends AppCompatActivity implements AsyncResponse {

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


    // return boolean true, questions were added to the database, false error occurs while parsing
    @Override
    public void processAddQuestionsFromCSV(Boolean result) {

    }

    // return list of questions to display, see if we need adapter in the view model or in the activity
    @Override
    public void processDisplayQuestions(List<Question> questions) {

    }


    // return boolean true if rows were deleted, false if not
    @Override
    public void processDeleteQuestions(Boolean result) {

    }

    // return true if row was deleted, false if not
    @Override
    public void processDeleteQuestion(Boolean result) {


    }
}
