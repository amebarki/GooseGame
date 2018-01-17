package com.project.goosegame.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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

    @Insert
    public List<Long> insertListQuestions(List<Question> listQuestions);

    @Update
    public void updateQuestion(Question question);

    @Delete
    public void deleteQuestion(Question question);

    @Query("SELECT * FROM questions")
    List<Question> getAll();


}
