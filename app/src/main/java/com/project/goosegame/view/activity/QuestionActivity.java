package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.AsyncQuestions;
import com.project.goosegame.utils.CustomItemClickListener;
import com.project.goosegame.utils.HidingScrollListener;
import com.project.goosegame.utils.observable.AsyncQuestions;
import com.project.goosegame.view.adapter.QuestionRecyclerAdapter;
import com.project.goosegame.viewModel.QuestionsViewModel;

import java.util.List;

public class QuestionActivity extends AppCompatActivity implements AsyncQuestions {

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
        questionsViewModel.response = this;

        questionsViewModel.displayQuestions();
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
            QuestionRecyclerAdapter questionRecyclerAdapter = new QuestionRecyclerAdapter(questionList, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {

                }
            }, questionsViewModel);

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

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    questionsViewModel.deleteQuestions();
                }
            });

            alertDialogBuilder.setNegativeButton("Annuler",
                    new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialogBuilder.setMessage("Etes-vous sûr de vouloir supprimer toutes les questions importées ?");
            alertDialogBuilder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // return boolean true, questions were added to the database, false error occurs while parsing
    @Override
    public void processAddQuestionsFromCSV(Boolean result) {
        if(result) {
            Toast.makeText(this, getString(R.string.question_toast_import_success), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.question_toast_import_error), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, getString(R.string.question_toast_delete_all_success), Toast.LENGTH_SHORT).show();
            questionsViewModel.importBaseQuestions();
            questionsViewModel.displayQuestions();
        } else {
            Toast.makeText(this, getString(R.string.question_toast_delete_all_error), Toast.LENGTH_SHORT).show();
        }
    }

    // return true if row was deleted, false if not
    @Override
    public void processDeleteQuestion(Boolean result) {
        if(result) {
            Toast.makeText(this, getString(R.string.question_toast_delete_success), Toast.LENGTH_SHORT).show();
            questionsViewModel.importBaseQuestions();
            questionsViewModel.displayQuestions();
        } else {
            Toast.makeText(this, getString(R.string.question_toast_delete_all_error), Toast.LENGTH_SHORT).show();
        }

    }
}
