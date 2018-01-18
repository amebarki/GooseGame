package com.project.goosegame.utils.async;

import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface AsyncResponse {

    void processAddQuestionsFromCSV(Boolean result);
    void processDisplayQuestions(List<Question> questions);
    void processDeleteQuestions(Boolean result);
    void processDeleteQuestion(Boolean result);
}
