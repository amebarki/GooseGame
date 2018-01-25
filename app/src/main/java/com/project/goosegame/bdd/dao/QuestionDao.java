package com.project.goosegame.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 17/01/2018.
 */
@Dao
public interface QuestionDao {

    @Insert
    public Long insertQuestion(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public List<Long> insertListQuestions(List<Question> questions);

    @Update
    public void updateQuestion(Question question);

    @Delete
    public int deleteQuestion(Question question);

    @Delete
    public int deleteQuestions(List<Question> questions);

    @Query("SELECT * FROM questions")
    List<Question> getAll();

    @Query("SELECT min(id_question), * FROM questions GROUP BY intitule_question ORDER BY id_question")
    List<Question> getDistinctAll();

    @Query("SELECT DISTINCT type_question FROM questions")
    List<String> getQuestionTypes();


    @Query("SELECT * FROM questions WHERE type_question = :type AND niveau_question = :difficulty")
    List<Question> getGameQuestions(String type, int difficulty);
}
