package com.project.goosegame.utils.async;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface AsyncParameters {

    void processDisplayQuestionTypeList(List<String> types);
    void processParametersFinish(Boolean result);
    void processPlayersFinish(Boolean result);
    void processNumberPlayers(int numberPlayers);
}
