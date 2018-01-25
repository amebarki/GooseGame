package com.project.goosegame.bdd.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.bdd.dao.QuestionDao;
/**
 * Created by Adam on 17/01/2018.
 */
@Database(entities = {Question.class}, version = 2)
public abstract class AppQuestionDatabase extends RoomDatabase {
    private static AppQuestionDatabase instance;

    public abstract QuestionDao questionDao();

    public static AppQuestionDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),AppQuestionDatabase.class,context.getString(R.string.databasase_name))
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Nothing to add, just migrate to the new Database and keep the old data
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Nothing to add, just migrate to the new Database and keep the old data
        }
    };


    public static void destroyInstance()
    {
        instance = null;
    }


}
