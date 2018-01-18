package com.project.goosegame.utils.async;

import com.project.goosegame.model.Question;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface AsyncResponse {

    void processFinish(Boolean result);
    void processFinish(List<Question> questions);
    void processFinishString(List<String> questions);
}
