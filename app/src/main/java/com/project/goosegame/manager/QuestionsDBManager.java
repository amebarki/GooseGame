package com.project.goosegame.manager;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.util.Log;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class QuestionsDBManager {

    private static QuestionsDBManager instance = null;
    private AppQuestionDatabase db;
    public static final String TAG = "DATABASE";

    public static QuestionsDBManager getInstance() {
        if (instance == null) {
            instance = new QuestionsDBManager();
        }
        return instance;
    }

    public void setAppQuestionDatabase(AppQuestionDatabase db) {
        this.db = db;
    }


    public Boolean addListQuestions(List<Question> questions) {
        List<Long> result = db.questionDao().insertListQuestions(questions);
        // if zero no line insert otherwise true
        if (result.size() != 0)
            return true;
        return false;
    }

    public Boolean deleteQuestion(Question question)
    {
        int result = db.questionDao().deleteQuestion(question);
        if(result !=0)
            return true;
        return false;
    }

    public Boolean deleteListQuestions(List<Question> questions) {
        int resutlt = db.questionDao().deleteQuestions(questions);
        if (resutlt !=0)
            return true;
        return false;
    }

    public List<Question> getListQuestions() {
        return db.questionDao().getAll();
    }

   public List<Question> getDistinctQuestions(){
        return db.questionDao().getDistinctAll();
   }

   // recup everything from the database and distinct row
    public boolean removeDuplicateQuestions(List<Question> questions)
    {
        // insert new List of questions correct -> extract all questions distinct
        if (this.addListQuestions(questions)){
            List<Question> listDistinct = this.getDistinctQuestions();
            // delete all rows from table  successful, insert new list distinct
            if(this.deleteListQuestions(getListQuestions())){
                if(this.addListQuestions(listDistinct)){
                    return true;
                }
            }
        }
        return false;

    }

    public List<String> getQuestionTypes()
    {
        return db.questionDao().getQuestionTypes();
    }

}
