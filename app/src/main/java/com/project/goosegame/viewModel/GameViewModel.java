package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.project.goosegame.R;
import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.GameObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 19/01/2018.
 */

public class GameViewModel extends BaseObservable {


    private Context context;
    private GameManager gameManager = null;
    private QuestionManager questionManager = null;
    private List<Question> gameQuestionsList = null;
    private GameObservable response = null;

    private int nbCaseToMove;


    public GameViewModel(Context context) {
        this.context = context;
        this.gameManager = GameManager.getInstance();
        this.questionManager = QuestionManager.getInstance();
        questionManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(this.context));

    }

    public void setGameObeservable(GameObservable gameObservable) {
        this.response = gameObservable;
    }

    public void getGooseModel() {
        response.processGooseModel(gameManager.getGooseModel());
    }


    public void initGameQuestions() {
        new AsyncTask<Void, Void, List<Question>>() {
            @Override
            protected List<Question> doInBackground(Void... voids) {
                gameQuestionsList = new ArrayList<Question>();
                gameQuestionsList.addAll(questionManager.initGameQuestions(gameManager.getGooseModel().getTypeGame(),
                                gameManager.getGooseModel().getDifficulty()));

                return gameQuestionsList;
            }

            @Override
            protected void onPostExecute(List<Question> questionList) {
                super.onPostExecute(questionList);

                if (gameQuestionsList != null && !gameQuestionsList.isEmpty())
                    response.processGameQuestions(gameQuestionsList);
            }
        }.execute();

        // TODO: 19/01/2018 manage error message
    }

    // TODO: 19/01/2018 activity recup window dimension and pass to this function to calculate the dimension of cases
    public void createCases(int windowHeigth, int windowWidth) {
        int numberCase = gameManager.getGooseModel().calculateNumberCases();
        float xMargin = this.getXMargin(windowWidth);
        float yMargin = this.getYMargin(windowHeigth);
        gameManager.getGooseModel().createCase(xMargin, yMargin, windowWidth);
        if (gameManager.getGooseModel().getBoardGame().size() == numberCase) {
            response.processGooseCases(gameManager.getGooseModel().getBoardGame());
        } else {
            response.processGooseCases(null);
        }
    }

    public float getXMargin(int windowwidth) {
        float xMargin = (windowwidth - (6 * 300)) / 6;
        return xMargin;
    }

    public float getYMargin(int windowHeight) {
        float yMargin = (windowHeight - (6 * 200)) /
                (this.gameManager.getGooseModel().getNumberCase() / 6) - 10;
        if ((yMargin < 0))
            yMargin = yMargin * (-1);
        return yMargin;
    }

    public void startTimer() {

        new CountDownTimer(gameManager.getGooseModel().getDurationGame() * 1000 * 60, 1000) {

            public void onTick(long millisUntilFinished) {
                response.processDisplayTime(context.getString(R.string.game_time_left) + (millisUntilFinished / (1000 * 60)) % 60 + " : " + (millisUntilFinished / 1000) % 60);
                gameManager.getGooseModel().setDurationGame(gameManager.getGooseModel().getDurationGame() - 1);
            }

            public void onFinish() {
                response.processDisplayTime("Time up !");
                gameManager.getGooseModel().setDurationGame(0);
            }
        }.start();
    }


    public void newTurn() {
        if (gameManager.getGooseModel().getDurationGame() > 0) {
            // start the new turn for the next player become currentPlayer
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
                    response.processDisplayDicePlayer(gameManager.getGooseModel().getPlayerList().get(currentPlayer).getName());
                }
            }.start();
        } else {
            response.processDisplayEnd("Temp écoulé !");
        }
    }

    // call by the buttonLaunchDice
    public void ThrowDice() {
        nbCaseToMove = 0;
        do {
            nbCaseToMove = (int) ((Math.random() * 6 * gameManager.getGooseModel().getNumberDice()));
        } while (nbCaseToMove == 0);

        Log.d("throw",nbCaseToMove + "");
        response.processDisplayResultDice(context.getString(R.string.game_advance_case,nbCaseToMove));
    }

    public void verifyShowQuestion() {
        // Show the random question or if player reach endboard, party is finish
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
                if (gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase() + nbCaseToMove == gameManager.getGooseModel().getNumberCase() - 1) {
                    response.processDisplayEnd(gameManager.getGooseModel().getPlayerList().get(currentPlayer).getName() + context.getString(R.string.game_player_win));
                } else {
                    showQuestion(nbCaseToMove);
                    // TODO: 22/01/2018 see if it is the correct way to determine the end maybe add the nbCaseToMove in the condition
                    currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
                    if (gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase() + nbCaseToMove == gameManager.getGooseModel().getNumberCase() - 1) {
                        //layoutFin.setVisibility(View.VISIBLE);
                        //tvFin.setText(sets.getListPlayer().get(currentPlayer).getName() + " a gagné la partie !!");
                        response.processDisplayEnd(gameManager.getGooseModel().getPlayerList().get(currentPlayer).getName() + " a gagné la partie !!");
                    } else {
                        showQuestion(nbCaseToMove);
                    }
                }
            }
        }.start();
    }

    public void moveToNextCase(int caseToMove) {
        int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
        int currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

        int durationAnimation = 2000;
        float xTranslation = 0.0f;
        float yTranslation = 0.0f;
        int numberOfCasesToPass = 0;
        int numberCase = gameManager.getGooseModel().getNumberCase();
        if (gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase() + caseToMove >= numberCase) {
            numberOfCasesToPass = numberCase - gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase() - 1;
        }else
        {
            numberOfCasesToPass = caseToMove;
        }
        // move the player before to move in the graphic interface
        gameManager.getGooseModel().getPlayerList().get(currentPlayer).setCurrentCase(currentCase + numberOfCasesToPass);
        gameManager.getGooseModel().getPlayerList().get(currentCase).setNbCaseToMove(0);
        switch (gameManager.getGooseModel().getCurrentPlayer()) {
            case 0:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY();
                response.processAnimatePiece(numberOfCasesToPass, 0, currentCase, xTranslation, yTranslation, durationAnimation,true);
                break;
            case 1:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 40;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToPass, 1, currentCase, xTranslation, yTranslation, durationAnimation,true);
                break;
            case 2:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 100;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToPass, 2, currentCase, xTranslation, yTranslation, durationAnimation,true);
                break;
            case 3:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 160;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToPass, 3, currentCase, xTranslation, yTranslation, durationAnimation,true);
                break;
            default:
                // TODO: 23/01/2018 create response erreur

                break;

        }
    }

    public void moveToPreviousCase(int caseToMove) {
        int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
        int currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();
        int durationAnimation = 2000;
        float xTranslation = 0.0f;
        float yTranslation = 0.0f;
        int numberOfCasesToStepBack = 0;
        int numberCase = gameManager.getGooseModel().getNumberCase();

        if (gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase() + caseToMove < 0) {
            numberOfCasesToStepBack = 0;
        }else{
            numberOfCasesToStepBack = caseToMove;
        }
        gameManager.getGooseModel().getPlayerList().get(currentPlayer).setCurrentCase(currentCase + numberOfCasesToStepBack);
        gameManager.getGooseModel().getPlayerList().get(currentCase).setNbCaseToMove(0);
        switch (currentPlayer) {
            case 0:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToStepBack,currentPlayer,currentCase,xTranslation,yTranslation,durationAnimation,false);
                break;
            case 1:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToStepBack,currentPlayer,currentCase,xTranslation,yTranslation,durationAnimation,false);
                break;
            case 2:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToStepBack,currentPlayer,currentCase,xTranslation,yTranslation,durationAnimation,false);
                break;
            case 3:
                xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 40;
                response.processAnimatePiece(numberOfCasesToStepBack,currentPlayer,currentCase,xTranslation,yTranslation,durationAnimation,false);
                break;
            default:
                // TODO: 24/01/2018 create erreur message
                break;
        }
    }

    // get the number of case to move if player answer correctly to the question
    public void showQuestion(final int caseToMove) {
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                int currentCasePlayer = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();
                int typeCase = gameManager.getGooseModel().getBoardGame().get(currentCasePlayer+caseToMove).getType();

                Log.d("type",typeCase + "");
                if (typeCase > 0) {
                    int indexQuestion = (int) (Math.random() * (gameQuestionsList.size() - 1));
                    Question question = gameQuestionsList.get(indexQuestion);
                    int positionResponse = (int) (Math.random() * 3);
                    int nbAnswer = 4;

                    List<String> answerList = new ArrayList<String>();

                    switch (positionResponse) {
                        case 0:
                            answerList.add(question.getCorrectAnswer());
                            answerList.add(question.getFalseAnswerOne());
                            answerList.add(question.getFalseAnswerTwo());
                            answerList.add(question.getFalseAnswerThree());
                            break;

                        case 1:
                            answerList.add(question.getFalseAnswerTwo());
                            answerList.add(question.getCorrectAnswer());
                            answerList.add(question.getFalseAnswerOne());
                            answerList.add(question.getFalseAnswerThree());
                            break;

                        case 2:
                            answerList.add(question.getFalseAnswerThree());
                            answerList.add(question.getFalseAnswerOne());
                            answerList.add(question.getCorrectAnswer());
                            answerList.add(question.getFalseAnswerTwo());
                            break;

                        case 3:
                            answerList.add(question.getFalseAnswerThree());
                            answerList.add(question.getFalseAnswerOne());
                            answerList.add(question.getFalseAnswerTwo());
                            answerList.add(question.getCorrectAnswer());
                            break;

                        default:

                            break;
                    }

                    nbAnswer = question.getNbAnswer();
                    gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(caseToMove);

                    response.processShowQuestion(question, nbAnswer, answerList);
                } else if (typeCase == 0) {
                    int nbCaseRandom = 0;
                    do {
                        nbCaseRandom = (int) ((Math.random() * 6) - 3);
                    } while (nbCaseRandom == 0);

                    if (nbCaseRandom < 0) {
                        gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(nbCaseRandom);
                        response.processLaunchBonusMalus(context.getString(R.string.game_malus_title),
                                context.getString(R.string.game_malus_message));
                    } else {
                        gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(nbCaseRandom);
                        response.processLaunchBonusMalus(context.getString(R.string.game_bonus_title),
                                context.getString(R.string.game_bonus_message));

                    }
                } else {
                    response.processShowDialog(context.getString(R.string.game_erreur_questions_title),
                            context.getString(R.string.game_erreur_questions_message));
                }
            }
        }.start();
    }


    public void startBonusMalus() {

        int nbCaseToMove = gameManager.getGooseModel().getCurrentPlayerObject().getNbCaseToMove();
        if (nbCaseToMove > 0) {
            moveToNextCase(nbCaseToMove);
        } else {
            moveToPreviousCase(nbCaseToMove);
        }
        endOfTurn();
    }


    public void endOfTurn() {
        int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
        if (currentPlayer == gameManager.getGooseModel().getNumberPlayer() - 1) {
            gameManager.getGooseModel().setCurrentPlayer(0);
        } else {
            gameManager.getGooseModel().setCurrentPlayer(currentPlayer + 1);

        }
        newTurn();
    }

    public void verifyAnswer(String answer, Question q) {
        final boolean isCorrect = (answer.toLowerCase().equals(q.getCorrectAnswer().toLowerCase()));
        if (isCorrect) {
            gameManager.getGooseModel().getCurrentPlayerObject().setScore(gameManager.getGooseModel().getCurrentPlayerObject().getScore() + 1);
            response.processShowResultQuestion(context.getString(R.string.game_question_good_answer_message), isCorrect);

        } else {
            response.processShowResultQuestion(context.getString(R.string.game_question_bad_answer_message), isCorrect);

        }
    }

    public void validateMove(boolean isCorrect) {
        if (isCorrect) {
            new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    moveToNextCase(gameManager.getGooseModel().getCurrentPlayerObject().getNbCaseToMove());
                    endOfTurn();
                }
            }.start();
        } else {
            endOfTurn();
        }
    }
}
