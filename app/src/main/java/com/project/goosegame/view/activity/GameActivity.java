package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.goosegame.R;
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

    private int windowWidth;
    private int windowHeight;

    ImageView pion1;
    ImageView pion2;
    ImageView pion3;
    ImageView pion4;

    LinearLayout diceLayout;
    TextView dicePlayerTextView;
    TextView diceResultTextView;
    Button diceLaunchButton;

    LinearLayout resultatLayout;
    TextView resultatTextView;
    Button resultatContinuerButton;

    LinearLayout question2Layout;
    TextView question2TextView;
    Button question2Answer1Button;
    Button question2Answer2Button;

    LinearLayout question3Layout;
    TextView question3TextView;
    Button question3Answer1Button;
    Button question3Answer2Button;
    Button question3Answer3Button;

    LinearLayout question4Layout;
    TextView question4TextView;
    Button question4Answer1Button;
    Button question4Answer2Button;
    Button question4Answer3Button;
    Button question4Answer4Button;

    LinearLayout bonusMalusLayout;
    TextView bonusMalusTitleTextView;
    TextView bonusMalusResultTextView;
    Button bonusMalusContinueButton;

    LinearLayout endLayout;
    TextView endTextView;
    Button endNewGameButton;

    TextView gameTimeTextView;

    RelativeLayout caseLayout;

    private int nbPlayers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();

        gameTimeTextView = findViewById(R.id.game_text_game_time);

        gameViewModel  = new GameViewModel(getApplicationContext());
        gameViewModel.setGameObeservable(this);

        pionsPlayer = new ArrayList<>();
        gameViewModel.initGameQuestions();

        gameViewModel.startTimer();


    }

    public void initPion() {


        float depPion1X = 0;
        float depPion1Y = 0;


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
            pion2.setX(depPion1X + 100);
            pion2.setY(depPion1Y);
            pion1.bringToFront();
        }

        if (nbPlayers > 2) {
            pion3.setImageResource(playerList.get(2).getImage());
            pion3.setX(depPion1X);
            pion3.setY(depPion1Y + 70);
            pion1.bringToFront();
        }

        if (nbPlayers > 3) {
            pion4.setImageResource(playerList.get(3).getImage());
            pion4.setX(depPion1X + 100);
            pion4.setY(depPion1Y + 70);
            pion1.bringToFront();
        }

        gameViewModel.createCases(windowHeight,windowWidth);
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

        gameViewModel.newTurn();
    }

    public void initPopupDice() {
        diceLayout = findViewById(R.id.game_layout_dice);
        dicePlayerTextView = findViewById(R.id.game_text_dice_player);
        diceLaunchButton = findViewById(R.id.game_button_dice_launch);
        diceResultTextView = findViewById(R.id.game_text_dice_result);
    }

    public void initPopupDiceResult() {
        resultatLayout = findViewById(R.id.game_layout_dice_result);
        resultatTextView = findViewById(R.id.game_text_dice_result);
        resultatContinuerButton = findViewById(R.id.game_button_dice_result_continue);
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
        bonusMalusLayout = findViewById(R.id.game_layout_question4);
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
        initPion();
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
            alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

            alertDialogBuilder.setMessage(getString(R.string.game_dialog_loading_fail));
            alertDialogBuilder.show();
        }
    }

    @Override
    public void processDisplayTime(String time) {

        gameTimeTextView.setText(time);
    }

    @Override
    public void processShowQuestion(Question question, int nbAnswer, List<String> answerList) {

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

    }

    @Override
    public void processShowResultQuestion(String message, final boolean isCorrect) {
        initPopupDiceResult();

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
    public void processLaunchBonusMalus(int visibility, String resultTitle, String result) {
        initPopupBonusMalus();

        bonusMalusTitleTextView.setText(resultTitle);
        bonusMalusResultTextView.setText(result);
        bonusMalusLayout.setVisibility(visibility);

        bonusMalusContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultatLayout.setVisibility(View.GONE);
                gameViewModel.startBonusMalus();
            }
        });
    }

    @Override
    public void processDisplayDicePlayer(int visibility, String namePlayer) {

        initPopupDice();

        dicePlayerTextView.setText(namePlayer);
        diceLayout.setVisibility(visibility);

        diceLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.ThrowDice();
            }
        });
    }

    @Override
    public void processDisplayResultDice(int visibility, String resultDice) {
        initPopupDiceResult();

        resultatTextView.setText(resultDice);
        resultatLayout.setVisibility(visibility);

        resultatContinuerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameViewModel.verifyShowQuestion();
            }
        });
    }

    @Override
    public void processDisplayEnd(int visibility, String endText) {
        initPopupEnd();

        endTextView.setText(endText);
        endLayout.setVisibility(visibility);

        endNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void processAnimatePiece(int numberOfCaseToPass, int currentPlayer, int currentCase, float xTranslation, float yTranslation, int duration, boolean forward) {
        // TODO: 23/01/2018 create boucle for with numberOfCaseToPass has the limit if forward == true
        // TODO: 23/01/2018 in this boucle, move the currentPlayer pion from his currentCase one by one
        // TODO: 23/01/2018 see example of the old code :
        /**
         *  pion is the image of the graphic part
         *  pion4.bringToFront();
         *  pion4.animate().translationX(listCase.get(sets.getListPlayer().get(3).getCurrentCase()).getX() + 160).setDuration(2000);
         *  pion4.animate().translationY(listCase.get(sets.getListPlayer().get(3).getCurrentCase()).getY() - 40).setDuration(2000);
         */
        // TODO: 23/01/2018 the new code will be like this :
        /**
         * "thePion".animate().translationX(xTranslation).setDuration(duration);
         */
        // TODO: 23/01/2018 if forward == false the limit of the boucle will be 0

        ImageView pion;

        switch (currentPlayer) {
            case 1:
                pion = pion1;
                break;
            case 2:
                pion = pion2;
                break;
            case 3:
                pion = pion3;
                break;
            default:
        }       pion = pion4;

        if (forward) {
            for (int i = 0; i < numberOfCaseToPass; i++) {
                pion.animate().translationX(xTranslation).setDuration(duration);
                pion.animate().translationY(yTranslation).setDuration(duration);
            }
        } else {
            for (int i = numberOfCaseToPass; i < 0; i++) {
                pion.animate().translationX(xTranslation).setDuration(duration);
                pion.animate().translationY(yTranslation).setDuration(duration);
            }
        }
    }
}
