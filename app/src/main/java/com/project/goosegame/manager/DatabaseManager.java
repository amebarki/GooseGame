package com.project.goosegame.manager;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 17/01/2018.
 */

public class DatabaseManager {

    private static final String TAG = DatabaseManager.class.getName();


    public static void populateAsync(@NonNull final AppQuestionDatabase db,int choice) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(choice);
    }

    private static Question addQuestion(final AppQuestionDatabase db, Question question) {
        db.questionDao().insertQuestion(question);
        List<Question> list = db.questionDao().getAll();
        return question;
    }
    private void test(){}
    private static void populateWithDataCSV(AppQuestionDatabase db,int choice) {
    }


    // TODO: 17/01/2018  create function with choice or switch and call the function which use the dao of the database
    // TODO: 17/01/2018 use the asynctask to call this one function
    // TODO: 17/01/2018 create function to display questions
    // TODO: 17/01/2018 create function to delete question
    // TODO: 17/01/2018 create four examples question




    private static class PopulateDbAsync extends AsyncTask<Integer, Void, Void> {

        private final AppQuestionDatabase db;

        PopulateDbAsync(AppQuestionDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Integer... choice) {
            populateWithDataCSV(db,choice[0]);
            return null;
        }
    }

}
