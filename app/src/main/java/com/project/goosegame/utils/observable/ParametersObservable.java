package com.project.goosegame.utils.observable;

import java.util.List;

/**
 * Created by Adam on 18/01/2018.
 */

public interface ParametersObservable extends ColorObservable{

    void processDisplayQuestionTypeList(List<String> types);
    void processParametersFinish();
    void processPlayersFinish();
    void processNumberPlayers(int numberPlayers);
    void processDisplayMessage(String message);
}
