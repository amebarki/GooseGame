package com.project.goosegame.utils.observable;

import android.content.Context;

import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface QuestionsObservable {

    void processAddQuestionsFromCSV(Boolean result);
    void processCallBaseQuestions(Boolean result);
    void processDisplayQuestions(List<Question> questions);
    void processDeleteQuestions(Boolean result);
    void processDeleteQuestion(Boolean result);
    void processErrorParsing(String message);
}
