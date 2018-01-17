package com.project.goosegame.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.project.goosegame.model.Question;
import com.project.goosegame.bdd.dao.QuestionDao;
/**
 * Created by Adam on 17/01/2018.
 */
@Database(entities = {Question.class}, version = 1)
public abstract class AppQuestionDatabase extends RoomDatabase {
    private static AppQuestionDatabase instance;

    public abstract QuestionDao questionDao();

    public static AppQuestionDatabase getAppQuestionDatabase(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppQuestionDatabase.class,"questions")
                    .build();
        }
        return instance;
    }

    public static void destroyInstance()
    {
        instance = null;
    }
}
