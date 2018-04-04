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
        baseQuestions.addAll(this.initQuestionsConjugaison());
        baseQuestions.addAll(this.initQuestionsGrammaire());
        baseQuestions.addAll(this.initQuestionsOrthographe());
        baseQuestions.addAll(this.initQuestionsSynonyme());
        List<Question> empty = db.questionDao().getAll();
        if (empty.size() == 0) {
            List<Long> result = db.questionDao().insertListQuestions(baseQuestions);
            if (result != null)
                return true;
        }
        // no insertion because database not empty
        return false;
    }

    public List<Question> initGameQuestions(String typeQuestion, int difficulty) {
        return db.questionDao().getGameQuestions(typeQuestion, difficulty);
    }


    public ArrayList<Question> initQuestionsConjugaison() {
        ArrayList<Question> listQuestionsConjugaison = new ArrayList<>();
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "les enfants chant_____", "ent", "e", "es", "ons", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "elle march_____", "e", "ent", "es", "ons", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "maman parl_____", "e", "ent", "es", "ons", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "Nous jou_____", "ons", "e", "es", "ont", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "Maé et Marie rang_____", "ent", "e", "es", "ons", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "nous parl_____", "ons", "ont", "es", "ent", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "vous gagn_____", "ez", "e", "er", "ent", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "la fille cri_____", "e", "es", "ent", "ez", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "tu déjeun_____", "es", "e", "ent", "ez", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "tu réveill_____", "es", "e", "ent", "ez", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "vous mang_____", "ez", "er", "e", "es", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "nous mang_____", "eons", "ons", "e", "ent", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "mes parents hurl____", "ent", "es", "e", "ez", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "le chien aboi____", "e", "es", "ent", "ons", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "une fille pleur_____", "e", "es", "ent", "ez", 0));
        listQuestionsConjugaison.add(new Question("conjugaison", 4, "la fleur pouss____", "e", "es", "ent", "ez", 0));

        return listQuestionsConjugaison;
    }

    public ArrayList<Question> initQuestionsGrammaire() {

        ArrayList<Question> listQuestionsGrammaire = new ArrayList<>();
        listQuestionsGrammaire.add(new Question("grammaire", 2, "grand", "adjectif", "nom", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "chien", "nom", "adjectif", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "nager", "verbe", "nom", "adjectif", "déterminant ", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "petit", "adjectif", "nom", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "courir", "verbe", "nom", "adjectif", "déterminant ", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "fourmis", "nom", "adjectif", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "partir", "verbe", "nom", "adjectif", "déterminant ", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "ce", "déterminant", "verbe", "nom", "adjectif", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "magnifique", "adjectif", "nom", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "chaussette", "nom", "adjectif", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "garage", "nom", "adjectif", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "garder", "verbe", "nom", "adjectif", "déterminant ", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "jardin", "nom", "adjectif", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "rapide", "adjectif", "nom", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "rouge", "adjectif", "nom", "déterminant", "verbe", 0));
        listQuestionsGrammaire.add(new Question("grammaire", 2, "parents", "nom", "adjectif", "déterminant", "verbe", 0));

        return listQuestionsGrammaire;
    }

    public ArrayList<Question> initQuestionsOrthographe() {
        ArrayList<Question> listQuestionsOrthographe = new ArrayList<>();
        listQuestionsOrthographe.add(new Question("synonyme", 3, "château", "châteaux", "château", "chateaus", "chateaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "cheval", "chevaux", "chevau", "cheval", "chevals", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "table", "tables", "table", "tablex", "tableaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "bocal", "bocaux", "bocals", "bocalx", "bocau", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "cravate", "cravates", "cravate", "cravatex", "cravataux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "souris", "souris", "sourix", "sourisx", "souri", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "journal", "journaux", "journau", "journals", "journeaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "enfant", "enfants", "enfans", "enfantx", "enfanteaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "chou", "choux", "chous", "chou", "chouxs", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "clou", "clous", "cloux", "clou", "clouxs", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "gâteau", "gâteaux", "gateaux", "gâteaux", "gâteau", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "kangourou", "kangourous", "kangourou", "kangouroux", "kangouraux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "ami", "amis", "amies", "amix", "ami", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "local", "locaux", "locals", "local", "loceaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "végétal", "végétaux", "végétal", "végétals", "végéteaux", 0));
        listQuestionsOrthographe.add(new Question("synonyme", 3, "hibou", "hiboux", "hibous", "hibou", "hibouts", 0));

        return listQuestionsOrthographe;
    }

    public ArrayList<Question> initQuestionsSynonyme() {
        ArrayList<Question> listQuestionsSynonyme = new ArrayList<>();

        listQuestionsSynonyme.add(new Question("synonyme", 1, "pétrifié", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "euphorique", "joie", "colère", "tristesse", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "angoisse", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "chagrin", "tristesse", "colère", "joie", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "agacement", "colère", "joie", "peur", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "crainte", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "bonheur", "joie", "colère", "tristesse", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "terreur", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "enthousisme", "joie", "colère", "tristesse", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "furieux", "colère", "joie", "peur", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "appréhension", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "mélancolique", "tristesse", "colère", "joie", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "morose", "tristesse", "colère", "joie", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "rayonner", "joie", "colère", "tristesse", "peur", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "trac", "peur", "joie", "colère", "tristesse", 0));
        listQuestionsSynonyme.add(new Question("synonyme", 1, "ravi", "joie", "colère", "tristesse", "peur", 0));

        return listQuestionsSynonyme;
    }

}

