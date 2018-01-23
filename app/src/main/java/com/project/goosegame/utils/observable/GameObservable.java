package com.project.goosegame.utils.observable;

import com.project.goosegame.model.Question;
import com.project.goosegame.model.pojo.Case;
import com.project.goosegame.model.pojo.Player;

import java.util.List;

/**
 * Created by Adam on 19/01/2018.
 */

public interface GameObservable {

    void processGameQuestions(List<Question> gameQuestionsList);

    void processGooseCases(List<Case> boardGame);

    void processDisplayTime(String time);

    void processShowQuestion(Question q, int positionResponse);

    void processShowResultQuestion(String result,boolean isCorrect);

    void processLaunchBonusMalus(int visibility,String resultTitle,String result);

    void processDisplayDicePlayer(int visibility, String namePlayer);

    void processDisplayResultDice(int visibility, String resultDice);

    void processDisplayEnd(int visibility, String endText,boolean isOver);

    void processShowDialog(String title,String text);

    void processAnimatePiece(int numberOfCaseToPass,int currentPlayer,int currentCase,float xTranslation,float yTranslation,int duration,boolean forward);

}
