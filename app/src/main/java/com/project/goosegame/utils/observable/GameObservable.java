package com.project.goosegame.utils.observable;

import com.project.goosegame.model.GooseModel;
import com.project.goosegame.model.Question;
import com.project.goosegame.model.pojo.Case;
import com.project.goosegame.model.pojo.Player;

import java.util.List;

/**
 * Created by Adam on 19/01/2018.
 */

public interface GameObservable {

    void processGooseModel(GooseModel gooseModel);

    void processGameQuestions(List<Question> gameQuestionsList);

    void processGooseCases(List<Case> boardGame);

    void processDisplayTime(String time);

    void processShowQuestion(Question question, int nbAnswer, List<String> answerList);

    void processShowResultQuestion(String result,boolean isCorrect);

    void processLaunchBonusMalus(String resultTitle,String result);

    void processDisplayDicePlayer(String namePlayer);

    void processDisplayResultDice(String resultDice);

    void processDisplayEnd(String endText);

    void processShowDialog(String title,String text);

    void processAnimatePiece(int numberOfCaseToPass,int currentPlayer,int currentCase,float xTranslation,float yTranslation,int duration,boolean forward);
}
