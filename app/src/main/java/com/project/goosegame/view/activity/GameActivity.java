package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.manager.GameManager;
import com.project.goosegame.model.GooseModel;
import com.project.goosegame.model.Question;
import com.project.goosegame.model.pojo.Case;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.observable.GameObservable;
import com.project.goosegame.viewModel.GameViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 22/01/2018.
 */

public class GameActivity extends AppCompatActivity implements GameObservable {

    private GameViewModel gameViewModel;
    private List<Question> questionsList;
    private ArrayList<ImageView> pionsPlayer;
    private List<Case> caseList;
    private List<Player> playerList;
    private GooseModel gooseModel;
    private CountDownTimer answerTimeTimer;

    private int windowWidth;
    private int windowHeight;

    ImageView pion1;
    ImageView pion2;
    ImageView pion3;
    ImageView pion4;

    ConstraintLayout diceLayout;
    TextView dicePlayerTextView;
    TextView diceResultTextView;
    Button diceLaunchButton;

    ConstraintLayout resultatLayout;
    TextView resultatTextView;
    Button resultatContinuerButton;

    ConstraintLayout question2Layout;
    TextView question2TextView;
    Button question2Answer1Button;
    Button question2Answer2Button;

    ConstraintLayout question3Layout;
    TextView question3TextView;
    Button question3Answer1Button;
    Button question3Answer2Button;
    Button question3Answer3Button;

    ConstraintLayout question4Layout;
    TextView question4TextView;
    Button question4Answer1Button;
    Button question4Answer2Button;
    Button question4Answer3Button;
    Button question4Answer4Button;

    ConstraintLayout bonusMalusLayout;
    TextView bonusMalusTitleTextView;
    TextView bonusMalusResultTextView;
    Button bonusMalusContinueButton;

    ConstraintLayout endLayout;
    TextView endTextView;
    Button endNewGameButton;

    RelativeLayout caseLayout;

    TextView gameTimeTextView;

    private int nbPlayers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();

        gameViewModel  = new GameViewModel(getApplicationContext());
        gameViewModel.setGameObeservable(this);

        pionsPlayer = new ArrayList<>();

        gameViewModel.initGameQuestions();
        gameViewModel.startTimer();
    }

    public void initPion() {
        GameManager gameManager = gameViewModel.getGameManager();
        int currentCase = 0;

        float depPion1X = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
        float depPion1Y = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 20;

        pion1 = findViewById(R.id.game_pion1);
        pion2 = findViewById(R.id.game_pion2);
        pion3 = findViewById(R.id.game_pion3);
        pion4 = findViewById(R.id.game_pion4);

        pion1.setImageResource(playerList.get(0).getImage());
        pion1.setX(depPion1X);
        pion1.setY(depPion1Y);
        pion1.bringToFront();

        if (nbPlayers > 1) {
            pion2.setImageResource(playerList.get(1).getImage());
            pion2.setX(depPion1X + 80);
            pion2.setY(depPion1Y - 20);
            pion1.bringToFront();
        }

        if (nbPlayers > 2) {
            pion3.setImageResource(playerList.get(2).getImage());
            pion3.setX(depPion1X - 20);
            pion3.setY(depPion1Y + 80);
            pion1.bringToFront();
        }

        if (nbPlayers > 3) {
            pion4.setImageResource(playerList.get(3).getImage());
            pion4.setX(depPion1X + 80);
            pion4.setY(depPion1Y + 80);
            pion1.bringToFront();
        }

        gameViewModel.newTurn();

    }

    public void createCaseOnBoard() {

        Case currentCase;
        caseLayout = findViewById(R.id.game_layout_case);

        float x;
        float y;

        for (int i=0 ; i < caseList.size() ; i++) {

            currentCase = caseList.get(i);

            TextView textView = new TextView(this);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            textView.setPadding(20, 20, 20, 20);
            textView.setTextSize(60);
            textView.setText("" + (i + 1));

            //set position
            textView.setX(currentCase.getX());
            textView.setY(currentCase.getY());

            //textView background
            switch (currentCase.getType()) {
                case 0:
                    textView.setBackgroundResource(R.drawable.case_violette);
                    break;
                default:
                    textView.setBackgroundResource(R.drawable.case_bleu);
                    break;

            }

            //Depart / Arrivée
            if(i == 0) {
                textView.setBackgroundResource(R.drawable.case_depart);
                textView.setText("");
            } else if (i == caseList.size()-1) {
                textView.setBackgroundResource(R.drawable.case_arrivee);
                textView.setText("");
            }

            caseLayout.addView(textView);
        }

        initPion();
    }

    public void initPopupDice() {
        diceLayout = findViewById(R.id.game_layout_dice);
        dicePlayerTextView = findViewById(R.id.game_text_dice_player);
        diceLaunchButton = findViewById(R.id.game_button_dice_launch);
        diceResultTextView = findViewById(R.id.game_text_result);
    }

    public void initPopupResult() {
        resultatLayout = findViewById(R.id.game_layout_result);
        resultatTextView = findViewById(R.id.game_text_result);
        resultatContinuerButton = findViewById(R.id.game_button_result_continue);
    }

    public void initPopupQuestion2() {
        question2Layout = findViewById(R.id.game_layout_question2);
        question2TextView = findViewById(R.id.game_lib_question2);
        question2Answer1Button = findViewById(R.id.game_button_question2_answer1);
        question2Answer2Button = findViewById(R.id.game_button_question2_answer2);
    }

    public void initPopupQuestion3() {
        question3Layout = findViewById(R.id.game_layout_question3);
        question3TextView = findViewById(R.id.game_lib_question3);
        question3Answer1Button = findViewById(R.id.game_button_question3_answer1);
        question3Answer2Button = findViewById(R.id.game_button_question3_answer2);
        question3Answer3Button = findViewById(R.id.game_button_question3_answer3);
    }

    public void initPopupQuestion4() {
        question4Layout = findViewById(R.id.game_layout_question4);
        question4TextView = findViewById(R.id.game_lib_question4);
        question4Answer1Button = findViewById(R.id.game_button_question4_answer1);
        question4Answer2Button = findViewById(R.id.game_button_question4_answer2);
        question4Answer3Button = findViewById(R.id.game_button_question4_answer3);
        question4Answer4Button = findViewById(R.id.game_button_question4_answer4);
    }

    public void initPopupBonusMalus() {
        bonusMalusLayout = findViewById(R.id.game_layout_bonus_malus);
        bonusMalusTitleTextView = findViewById(R.id.game_text_title_bonus_malus);
        bonusMalusResultTextView = findViewById(R.id.game_text_result_bonus_malus);
        bonusMalusContinueButton = findViewById(R.id.game_button_bonus_malus);
    }

    public void initPopupEnd() {
        endLayout = findViewById(R.id.game_layout_end);
        endTextView = findViewById(R.id.game_text_end);
        endNewGameButton = findViewById(R.id.game_button_end_new);
    }

    private void showPopupQuestion2(final Question question, final List<String> answerList) {
        initPopupQuestion2();

        question2TextView.setText(question.getTitle());
        question2Layout.setVisibility(View.VISIBLE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = "";

                switch (v.getId()) {
                    case R.id.game_button_question2_answer1:
                        answer = answerList.get(0);
                        break;

                    case R.id.game_button_question2_answer2:
                        answer = answerList.get(1);
                        break;

                    default:

                }

                answerTimeTimer.cancel();
                question2Layout.setVisibility(View.GONE);
                gameViewModel.verifyAnswer(answer, question);
            }
        };

        question2Answer1Button.setText(answerList.get(0));
        question2Answer1Button.setOnClickListener(onClickListener);

        question2Answer2Button.setText(answerList.get(1));
        question2Answer2Button.setOnClickListener(onClickListener);
    }

    private void showPopupQuestion3(final Question question, final List<String> answerList) {
        initPopupQuestion3();

        question3TextView.setText(question.getTitle());
        question3Layout.setVisibility(View.VISIBLE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer;

                switch (v.getId()) {
                    case R.id.game_button_question3_answer1:
                        answer = answerList.get(0);
                        break;

                    case R.id.game_button_question3_answer2:
                        answer = answerList.get(1);
                        break;

                    case R.id.game_button_question3_answer3:
                        answer = answerList.get(2);
                        break;

                    default:
                        answer = "";
                }

                answerTimeTimer.cancel();
                question3Layout.setVisibility(View.GONE);
                gameViewModel.verifyAnswer(answer, question);
            }
        };

        question3Answer1Button.setText(answerList.get(0));
        question3Answer1Button.setOnClickListener(onClickListener);

        question3Answer2Button.setText(answerList.get(1));
        question3Answer2Button.setOnClickListener(onClickListener);

        question3Answer3Button.setText(answerList.get(2));
        question3Answer3Button.setOnClickListener(onClickListener);
    }

    public void showPopupQuestion4(final Question question, final List<String> answerList) {
        initPopupQuestion4();

        question4TextView.setText(question.getTitle());
        question4Layout.setVisibility(View.VISIBLE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer;

                switch (v.getId()) {
                    case R.id.game_button_question4_answer1:
                        answer = answerList.get(0);
                        break;

                    case R.id.game_button_question4_answer2:
                        answer = answerList.get(1);
                        break;

                    case R.id.game_button_question4_answer3:
                        answer = answerList.get(2);
                        break;


                    case R.id.game_button_question4_answer4:
                        answer = answerList.get(3);
                        break;

                    default:
                        answer = "";
                }

                answerTimeTimer.cancel();
                question4Layout.setVisibility(View.GONE);
                gameViewModel.verifyAnswer(answer, question);
            }
        };

        question4Answer1Button.setText(answerList.get(0));
        question4Answer1Button.setOnClickListener(onClickListener);

        question4Answer2Button.setText(answerList.get(1));
        question4Answer2Button.setOnClickListener(onClickListener);

        question4Answer3Button.setText(answerList.get(2));
        question4Answer3Button.setOnClickListener(onClickListener);

        question4Answer4Button.setText(answerList.get(3));
        question4Answer4Button.setOnClickListener(onClickListener);
    }

    @Override
    public void processGooseModel(GooseModel gooseModel) {
        this.gooseModel = gooseModel;

        this.playerList = gooseModel.getPlayerList();
        this.nbPlayers = gooseModel.getNumberPlayer();

        gameViewModel.createCases(windowHeight,windowWidth);
    }

    @Override
    public void processGameQuestions(List<Question> gameQuestionsList) {
        this.questionsList = gameQuestionsList;

        gameViewModel.getGooseModel();
    }

    @Override
    public void processGooseCases(List<Case> caseList) {
        if (caseList != null) {
            this.caseList = caseList;

            createCaseOnBoard();
        } else {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setPositiveButton("Revenir au menu",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(1);
                        finish();
                    }
                });

            alertDialogBuilder.setMessage(getString(R.string.game_dialog_loading_fail));
            alertDialogBuilder.show();
        }
    }

    @Override
    public void processDisplayTime(String time) {

        gameTimeTextView = findViewById(R.id.game_text_game_time);
        gameTimeTextView.setText(time);
    }

    @Override
    public void processShowQuestion(Question question, final int nbAnswer, List<String> answerList) {

        switch (nbAnswer) {
            case 2:
                showPopupQuestion2(question, answerList);
                break;

            case 3:
                showPopupQuestion3(question, answerList);
                break;

            default:
                showPopupQuestion4(question, answerList);
        }


        GameManager gameManager = gameViewModel.getGameManager();

        answerTimeTimer = new CountDownTimer(gameManager.getGooseModel().getCurrentPlayerObject().getAnswerTime() * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                switch (nbAnswer) {
                    case 2:
                        question2Layout.setVisibility(View.GONE);
                        break;

                    case 3:
                        question3Layout.setVisibility(View.GONE);
                        break;

                    case 4:
                        question4Layout.setVisibility(View.GONE);
                        break;

                    default:

                }

                processShowResultQuestion(getString(R.string.game_time_end_turn),false);
            }
        }.start();
    }

    @Override
    public void processShowResultQuestion(String message, final boolean isCorrect) {
        initPopupResult();

        resultatTextView.setText(message);
        resultatLayout.setVisibility(View.VISIBLE);

        resultatContinuerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultatLayout.setVisibility(View.GONE);
                gameViewModel.validateMove(isCorrect);
            }
        });
    }

    @Override
    public void processLaunchBonusMalus(String resultTitle, String result) {
        initPopupBonusMalus();

        bonusMalusTitleTextView.setText(resultTitle);
        bonusMalusResultTextView.setText(result);
        bonusMalusLayout.setVisibility(View.VISIBLE);

        bonusMalusContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bonusMalusLayout.setVisibility(View.GONE);
                gameViewModel.startBonusMalus();
            }
        });
    }

    @Override
    public void processDisplayDicePlayer(String namePlayer) {

        initPopupDice();

        dicePlayerTextView.setText(namePlayer);
        diceLayout.setVisibility(View.VISIBLE);

        diceLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceLayout.setVisibility(View.GONE);
                gameViewModel.ThrowDice();
            }
        });
    }

    @Override
    public void processDisplayResultDice(String resultDice) {
        initPopupResult();

        resultatTextView.setText(resultDice);
        resultatLayout.setVisibility(View.VISIBLE);

        resultatContinuerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultatLayout.setVisibility(View.GONE);
                gameViewModel.verifyShowQuestion();
            }
        });
    }

    @Override
    public void processDisplayEnd(String endText) {
        initPopupEnd();

        endTextView.setText(endText);
        endLayout.setVisibility(View.VISIBLE);

        endNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endLayout.setVisibility(View.GONE);
                setResult(1);
                finish();
            }
        });
    }

    @Override
    public void processShowDialog(String title, String text) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(text);
        alertDialogBuilder.show();
    }

    @Override
    public void processAnimatePiece(int numberOfCaseToPass, boolean forward) {
        GameManager gameManager = gameViewModel.getGameManager();

        float xTranslation = 0.0f;
        float yTranslation = 0.0f;

        int currentPlayer = gameManager.getGooseModel().getCurrentPlayer();
        int currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

        if (forward) {
            for (int i = 0; i < numberOfCaseToPass; i++) {

                switch (currentPlayer) {
                    case 0:
                        gameManager.getGooseModel().getCurrentPlayerObject().setCurrentCase(currentCase + 1);
                        currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 20;

                        pion1.bringToFront();
                        pion1.animate().translationX(xTranslation).setDuration(1000);
                        pion1.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 1:
                        gameManager.getGooseModel().getPlayerList().get(currentPlayer).setCurrentCase(currentCase + 1);
                        currentCase = gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 80;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 20;

                        pion2.bringToFront();
                        pion2.animate().translationX(xTranslation).setDuration(1000);
                        pion2.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 2:
                        gameManager.getGooseModel().getPlayerList().get(currentPlayer).setCurrentCase(currentCase + 1);
                        currentCase = gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() + 80;

                        pion3.bringToFront();
                        pion3.animate().translationX(xTranslation).setDuration(1000);
                        pion3.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 3:
                        gameManager.getGooseModel().getPlayerList().get(currentPlayer).setCurrentCase(currentCase + 1);
                        currentCase = gameManager.getGooseModel().getPlayerList().get(currentPlayer).getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 80;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() + 80;

                        pion4.bringToFront();
                        pion4.animate().translationX(xTranslation).setDuration(1000);
                        pion4.animate().translationY(yTranslation).setDuration(1000);
                        break;
                    default:
                        processMessageError(getString(R.string.game_animate_player_error));
                        break;

                }
            }
        } else {
            for (int i = numberOfCaseToPass; i < 0; i++) {
                switch (currentPlayer) {
                    case 0:
                        gameManager.getGooseModel().getCurrentPlayerObject().setCurrentCase(currentCase - 1);
                        currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 20;

                        pion1.bringToFront();
                        pion1.animate().translationX(xTranslation).setDuration(1000);
                        pion1.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 1:
                        gameManager.getGooseModel().getCurrentPlayerObject().setCurrentCase(currentCase - 1);
                        currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 60;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() - 20;

                        pion2.bringToFront();
                        pion2.animate().translationX(xTranslation).setDuration(1000);
                        pion2.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 2:
                        gameManager.getGooseModel().getCurrentPlayerObject().setCurrentCase(currentCase - 1);
                        currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() - 20;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() + 60;

                        pion3.bringToFront();
                        pion3.animate().translationX(xTranslation).setDuration(1000);
                        pion3.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    case 3:
                        gameManager.getGooseModel().getCurrentPlayerObject().setCurrentCase(currentCase - 1);
                        currentCase = gameManager.getGooseModel().getCurrentPlayerObject().getCurrentCase();

                        xTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getX() + 60;
                        yTranslation = gameManager.getGooseModel().getBoardGame().get(currentCase).getY() + 60;

                        pion4.bringToFront();
                        pion4.animate().translationX(xTranslation).setDuration(1000);
                        pion4.animate().translationY(yTranslation).setDuration(1000);
                        break;

                    default:
                        processMessageError(getString(R.string.game_animate_player_error));
                        break;
                }
            }
        }
    }

    @Override
    public void processMessageError(String message) {
        Snackbar.make(findViewById(R.id.game_coordinator_layout),message,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void processPrimaryColor(int color) {

    }

    @Override
    public void processSecondaryColor(int color) {

    }

    @Override
    public void processSelectColor(int color) {

    }
}
