package com.project.goosegame.utils.observable;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface ParametersObservable {

    void processDisplayQuestionTypeList(List<String> types);
    void processParametersFinish(Boolean result);
    void processPlayersFinish(Boolean result);
    void processNumberPlayers(int numberPlayers);
    void processDisplayMessage(String message);
}
