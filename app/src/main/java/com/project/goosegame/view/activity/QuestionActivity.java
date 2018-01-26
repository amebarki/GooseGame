package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.QuestionsObservable;
import com.project.goosegame.utils.listener.CustomItemClickListener;
import com.project.goosegame.utils.listener.HidingScrollListener;
import com.project.goosegame.view.adapter.QuestionRecyclerAdapter;
import com.project.goosegame.viewModel.QuestionsViewModel;

import java.util.List;

public class QuestionActivity extends AppCompatActivity implements QuestionsObservable {

    private static final int INTENT_FILE_CODE = 10;
    private QuestionsViewModel questionsViewModel;
    private RecyclerView questionRecyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        toolbar = findViewById(R.id.question_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionsViewModel = new QuestionsViewModel(getApplicationContext());
        questionsViewModel.setQuestionsObservable(this);
        questionsViewModel.displayQuestions();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_FILE_CODE) {
            questionsViewModel.loadFile(resultCode, data);
            questionsViewModel.displayQuestions();
        }
    }

    private void setRecyclerViewMachine(List<Question> questionList) {
        questionRecyclerView = findViewById(R.id.question_recycler);
        questionRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        if (questionList.size() > 0) {
            QuestionRecyclerAdapter questionRecyclerAdapter = new QuestionRecyclerAdapter(questionList, questionsViewModel, this, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

                }
            });

            questionRecyclerView.setAdapter(questionRecyclerAdapter);
        }
        questionRecyclerView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideToolbar();
            }
            @Override
            public void onShow() {
                showToolbar();
            }
        });
        questionRecyclerView.setLayoutManager(layoutManager);
    }

    private void showToolbar() {
        toolbar.animate()
                .alpha(1)
                .translationY(0)
                .start();
    }

    private void hideToolbar() {
        toolbar.animate()
                .alpha(0)
                .translationY(-toolbar.getHeight())
                .start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_import) {
            startActivityForResult(questionsViewModel.openFileExplorer(), INTENT_FILE_CODE);
            return true;

        } else if (id == R.id.action_delete) {
           createAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // return boolean true, questions were added to the database, false error occurs while parsing
    @Override
    public void processAddQuestionsFromCSV(Boolean result) {
        if(result) {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_import_success),Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_import_error),Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void processCallBaseQuestions(Boolean result) {


    }

    // return list of questions to display, see if we need adapter in the view model or in the activity
    @Override
    public void processDisplayQuestions(List<Question> questions) {
        setRecyclerViewMachine(questions);
    }


    // return boolean true if rows were deleted, false if not
    @Override
    public void processDeleteQuestions(Boolean result) {
        if(result) {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_delete_all_success),Snackbar.LENGTH_LONG).show();
            //questionsViewModel.importBaseQuestions();
            questionsViewModel.displayQuestions();
        } else {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_delete_error),Snackbar.LENGTH_LONG).show();
        }
    }

    // return true if row was deleted, false if not
    @Override
    public void processDeleteQuestion(Boolean result) {
        if(result) {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_delete_success),Snackbar.LENGTH_LONG).show();
            //questionsViewModel.importBaseQuestions();
            questionsViewModel.displayQuestions();
        } else {
            Snackbar.make(findViewById(R.id.question_coordinator_layout), getString(R.string.question_snackbar_delete_error),Snackbar.LENGTH_LONG).show();

        }

    }

    @Override
    public void processErrorParsing(String message) {
        // TODO: 24/01/2018 Alert dialog display error
        Snackbar.make(findViewById(R.id.question_coordinator_layout), message,Snackbar.LENGTH_LONG).show();
    }



    public void createAlertDialog()
    {
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialog_layout, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.DialogTextTheme)
                .setView(inflator);
        alertDialogBuilder.setPositiveButton(getString(R.string.question_dialog_delete_confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        questionsViewModel.deleteQuestions();
                    }
                });

        alertDialogBuilder.setNegativeButton(getString(R.string.question_dialog_delete_cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final TextView textView = (TextView) inflator.findViewById(R.id.general_dialog_message);
        textView.setText(getString(R.string.question_dialog_delete_message));
        alertDialogBuilder.show();
    }
}
