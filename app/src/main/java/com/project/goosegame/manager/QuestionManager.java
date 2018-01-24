package com.project.goosegame.manager;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public class QuestionManager {

    private static QuestionManager instance = null;
    private AppQuestionDatabase db;

    public static QuestionManager getInstance() {
        if (instance == null) {
            instance = new QuestionManager();
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

    public Boolean deleteQuestion(Question question) {
        int result = db.questionDao().deleteQuestion(question);
        if (result != 0)
            return true;
        return false;
    }

    public Boolean deleteListQuestions(List<Question> questions) {
        int resutlt = db.questionDao().deleteQuestions(questions);
        if (resutlt != 0)
            return true;
        return false;
    }

    public List<Question> getListQuestions() {
        return db.questionDao().getAll();
    }

    public List<Question> getDistinctQuestions() {
        return db.questionDao().getDistinctAll();
    }

    // recup everything from the database and distinct row
    public boolean removeDuplicateQuestions(List<Question> questions) {
        // insert new List of questions correct -> extract all questions distinct
        if (this.addListQuestions(questions)) {
            List<Question> listDistinct = this.getDistinctQuestions();
            // delete all rows from table  successful, insert new list distinct
            if (this.deleteListQuestions(getListQuestions())) {
                if (this.addListQuestions(listDistinct)) {
                    return true;
                }
            }
        }
        return false;

    }

    public List<String> initQuestionTypeList() {
        return db.questionDao().getQuestionTypes();
    }

    public boolean createQuestions() {
        ArrayList<Question> baseQuestions = new ArrayList<>();
        Question q1 = new Question("grammaire", 1, "titi", "tic", "tac", "tuc", "toc", 0);
        Question q2 = new Question("conjugaison", 2, "roro", "roc", "rac", "ruc", "ric", 0);
        Question q3 = new Question("synonyme", 3, "baba", "bac", "buc", "", "", 0);
        Question q4 = new Question("vocabulaire", 4, "lulu", "luc", "lac", null, null, 0);
        baseQuestions.add(q1);
        baseQuestions.add(q2);
        baseQuestions.add(q3);
        baseQuestions.add(q4);
        List<Question> empty = db.questionDao().getAll();
        if (empty.size() == 0) {
            List<Long> result = db.questionDao().insertListQuestions(baseQuestions);
            if (result != null)
                return true;
        }
        // no insertion because database not empty
        return false;
    }


    public List<Question> initGameQuestions(String typeQuestion, int difficulty)
    {
        return db.questionDao().getGameQuestions(typeQuestion,difficulty);
    }



}
