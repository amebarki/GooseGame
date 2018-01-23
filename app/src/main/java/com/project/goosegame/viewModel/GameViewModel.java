package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.CountDownTimer;
import android.view.View;

import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.GameObservable;

import java.util.List;

/**
 * Created by Adam on 19/01/2018.
 */

public class GameViewModel extends BaseObservable {


    private Context context;
    private GameManager gameManager = null;
    private QuestionManager questionManager = null;
    private List<Question> gameQuestionsList = null;

    public GameObservable response = null;


    public GameViewModel(Context context) {
        this.context = context;
        this.gameManager = GameManager.getInstance();
        this.questionManager = QuestionManager.getInstance();
        questionManager.setAppQuestionDatabase(AppQuestionDatabase.getInstance(this.context));

    }


    public void getPlayersAndBoardGame()
    {
        response.processPlayersListAndBoardGame(gameManager.getGooseModel().getPlayerList(),
                gameManager.getGooseModel().getBoardGame());
    }

    public void initGameQuestions() {
        // TODO: 19/01/2018 manage error message
        gameQuestionsList.addAll(questionManager.initGameQuestions(gameManager.getGooseModel().getTypeGame(),
                gameManager.getGooseModel().getDifficulty()));
        if (gameQuestionsList != null && !gameQuestionsList.isEmpty())
            response.processGameQuestions(gameQuestionsList);
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
        new CountDownTimer(gameManager.getGooseModel().getDurationGame() * 2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();

        new CountDownTimer(gameManager.getGooseModel().getDurationGame() * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                //tvGameTime.setText("Temps restant   " + (millisUntilFinished / (1000*60)) % 60 + " : " + (millisUntilFinished / 1000) % 60);
                response.processDisplayTime("Temps restant   " + (millisUntilFinished / (1000 * 60)) % 60 + " : " + (millisUntilFinished / 1000) % 60);
                gameManager.getGooseModel().setDurationGame(gameManager.getGooseModel().getDurationGame() - 1);
            }

            public void onFinish() {
                //tvGameTime.setText("Time up !");
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
                    // response View.Visible
                    int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
                    response.processDisplayDicePlayer(View.VISIBLE, gameManager.getGooseModel().getPlayerList().get(currentPlayer).getName());
                    // button throw dice
                }
            }.start();
        } else {
            // turn finish for the currentPlayer if duration game is end
            new CountDownTimer(4000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    // LayoutFin View.Visible + string
                    response.processDisplayEnd(View.VISIBLE, "Temp écoulé !", true);
                    // create button to finish
                }
            }.start();
        }
    }

    // call by the buttonLaunchDice
    public void ThrowDice() {
        int nbCaseToMove = 0;
        do {
            nbCaseToMove = (int) ((Math.random() * 6 * gameManager.getGooseModel().getNumberDice()));
        } while (nbCaseToMove == 0);

        final int finalNbCaseToMove = nbCaseToMove;

        // display how case player will advance
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                response.processDisplayResultDice(View.GONE, "Vous avancez de " + Integer.toString(finalNbCaseToMove) + " case(s)");
            }
        }.start();

        // Show the random question or if player reach endboard, party is finish
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // TODO: 22/01/2018 see if it is the correct way to determine the end maybe add thenbCaseToMove in the condition
                int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
                if (gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase() + finalNbCaseToMove == gameManager.getGooseModel().getNumberCase() - 1) {
                    //layoutFin.setVisibility(View.VISIBLE);
                    //tvFin.setText(sets.getListPlayer().get(currentPlayer).getName() + " a gagné la partie !!");
                    response.processDisplayEnd(View.VISIBLE,
                            gameManager.getGooseModel().getPlayerList().get(currentPlayer).getName() + " a gagné la partie !!",
                            true);

/*                    bFinNew.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
*/
                } else {
                    //validMove();
                    //display question if not the end
                    showQuestion(finalNbCaseToMove);
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
                break;
        }
    }

    // get the number of case to move if player answer correctly to the question
    public void showQuestion(final int caseToMove) {
        new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                int currentCasePlayer = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();
                int typeCase = gameManager.getGooseModel().getBoardGame().get(currentCasePlayer+caseToMove).getType();
                if (typeCase >= 0 && typeCase <= 4) {
                    int indexQuestion = (int) (Math.random() * (gameQuestionsList.size() - 1));
                    Question q = gameQuestionsList.get(indexQuestion);
                    int positionResponse = (int) (Math.random() * 3);
                       /*layoutTrouver4.setVisibility(View.VISIBLE);
                tvTrouver4Type.setText("Trouver le contraire de ");
                tvTrouver4Question.setVisibility(View.VISIBLE);
                tvTrouver4Question.setText(q.getIntituleQuestion());*/
                    gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(caseToMove);
                    response.processShowQuestion(q, positionResponse);
                } else if (typeCase == 5) {
                    //layoutBonusMalus.setVisibility(View.VISIBLE);
                    int nbCaseRandom = 0;
                    do {
                        nbCaseRandom = (int) ((Math.random() * 6) - 3);
                    } while (nbCaseRandom == 0);

                    if (nbCaseRandom < 0) {
                        //tvBonusMalus.setText("Malus");
                        //tvBonusMalusResult.setText("Reculer de " + Integer.toString(nbCaseRandom * (-1)) + " case(s)");
                        gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(nbCaseRandom);
                        response.processLaunchBonusMalus(View.VISIBLE,
                                "Malus",
                                "Reculer de " + Integer.toString(nbCaseRandom * (-1)) + " case(s)");
                    } else {
                        //tvBonusMalus.setText("Bonus");
                        //tvBonusMalusResult.setText("Avancer de " + Integer.toString(nbCaseRandom) + " case(s)");
                        gameManager.getGooseModel().getCurrentPlayerObject().setNbCaseToMove(nbCaseRandom);
                        response.processLaunchBonusMalus(View.VISIBLE,
                                "Bonus",
                                "Avancer de " + Integer.toString(nbCaseRandom) + " case(s)");

                    }
                    //final int finalNbCaseRandom = nbCaseRandom;
                    /*
                    bBonusMalusContinuer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            layoutBonusMalus.setVisibility(View.GONE);
                            if (finalNbCaseRandom > 0) {
                                moveToNext(finalNbCaseRandom);
                            } else {
                                moveToPrevious(finalNbCaseRandom);
                            }
                            tourFini();
                        }
                    });*/


                    // AlertDialog
                } else {
                    response.processShowDialog("Pas de questions !! ", "Chargerr base de questions");
                }
            }
        }.start();
    }


    public void startBonusMalus(int nbCaseToMove) {
        //layoutBonusMalus.setVisibility(View.GONE);
        if (nbCaseToMove > 0) {
            moveToNextCase(nbCaseToMove);
        } else {
            moveToPreviousCase(nbCaseToMove);
        }

        //tourFini();
        endOfTurn();

    }


    public void endOfTurn() {
        int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
        if (currentPlayer == gameManager.getGooseModel().getNumberPlayer() - 1) {
            //currentPlayer = 0;
            gameManager.getGooseModel().setCurrentPlayer(0);
        } else {
            //currentPlayer = currentPlayer + 1;
            gameManager.getGooseModel().setCurrentPlayer(currentPlayer + 1);

        }
        //nouveauTour();
        newTurn();
    }

    public void verifyAnswer(String answer, Question q) {
        //layoutResultat.setVisibility(View.VISIBLE);
        final boolean isCorrect = (answer.toLowerCase().equals(q.getCorrectAnswer().toLowerCase()));
        if (isCorrect) {
            //tvResultat.setText("Bravo tu as bien répondu !!");
            //sets.getListPlayer().get(currentPlayer).setScore(sets.getListPlayer().get(currentPlayer).getScore()+1);
            gameManager.getGooseModel().getCurrentPlayerObject().setScore(gameManager.getGooseModel().getCurrentPlayerObject().getScore() + 1);
            response.processShowResultQuestion("Bravo tu as bien répondu !!", isCorrect);

        } else {
            //tvResultat.setText("Dommage tu as répondu faux, retourne sur la case où tu étais...");
            response.processShowResultQuestion("Dommage, mauvaise réponse, pas de déplacement.", isCorrect);

        }
       /* bResultatContinuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCorrect) {
                    tourFini();

                } else {
                    moveToPrevious((sets.getListPlayer().get(currentPlayer).getCaseMoved() * (-1)));
                    tourFini();
                }
                layoutResultat.setVisibility(View.GONE);
            }
        });*/
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
