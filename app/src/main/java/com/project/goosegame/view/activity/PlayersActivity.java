package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.project.goosegame.R;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.observable.ParametersObservable;
import com.project.goosegame.viewModel.ParametersViewModel;

import java.util.ArrayList;
import java.util.List;

public class PlayersActivity extends AppCompatActivity implements ParametersObservable {
    private ParametersViewModel parametersViewModel;

    LinearLayout layoutPlayer2;
    LinearLayout layoutPlayer3;
    LinearLayout layoutPlayer4;

    ImageButton imageButtonPlayer1;
    ImageButton imageButtonPlayer2;
    ImageButton imageButtonPlayer3;
    ImageButton imageButtonPlayer4;

    EditText textPlayer1;
    EditText textPlayer2;
    EditText textPlayer3;
    EditText textPlayer4;

    NumberPicker pickerAnswerTime1;
    NumberPicker pickerAnswerTime2;
    NumberPicker pickerAnswerTime3;
    NumberPicker pickerAnswerTime4;

    Button buttonLaunchGame;

    int[] pionAvailable = new int[] {
            1,
            2,
            3,
            4,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0};

    int[] pionImage = new int[] {
            R.drawable.pion1,
            R.drawable.pion2,
            R.drawable.pion3,
            R.drawable.pion4,
            R.drawable.pion5,
            R.drawable.pion6,
            R.drawable.pion7,
            R.drawable.pion8,
            R.drawable.pion9,
            R.drawable.pion10,
            R.drawable.pion11,
            R.drawable.pion12,
            R.drawable.pion13,
            R.drawable.pion14,
            R.drawable.pion15,
            R.drawable.pion16,
            R.drawable.pion17,
            R.drawable.pion18};

    int nbPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.setParametersObservable(this);
        parametersViewModel.getNumberPlayers();

        initImageButton();
        initPickerAnswerTime();
        initButton();
    }


    public void setNumberPlayer(int nbPlayer) {

        layoutPlayer2 = findViewById(R.id.players_layout_player2);
        layoutPlayer3 = findViewById(R.id.players_layout_player3);
        layoutPlayer4 = findViewById(R.id.players_layout_player4);

        if (nbPlayer < 4) {
            layoutPlayer4.setVisibility(View.INVISIBLE);
        }

        if (nbPlayer < 3) {
            layoutPlayer3.setVisibility(View.INVISIBLE);
        }

        if (nbPlayer < 2) {
            layoutPlayer2.setVisibility(View.INVISIBLE);
        }
    }


    public void initImageButton() {

        imageButtonPlayer1 = findViewById(R.id.players_image_player1);
        imageButtonPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonPlayer1.setImageResource(pionImage[getImageId(1)]);
            }
        });

        imageButtonPlayer2 = findViewById(R.id.players_image_player2);
        imageButtonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonPlayer2.setImageResource(pionImage[getImageId(2)]);
            }
        });

        imageButtonPlayer3 = findViewById(R.id.players_image_player3);
        imageButtonPlayer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonPlayer3.setImageResource(pionImage[getImageId(3)]);
            }
        });

        imageButtonPlayer4 = findViewById(R.id.players_image_player4);
        imageButtonPlayer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonPlayer4.setImageResource(pionImage[getImageId(4)]);
            }
        });
    }

    public void initPickerAnswerTime() {

        String[] displayedValues = new String[] {"15 secondes", "30 secondes", "45 secondes"};

        pickerAnswerTime1 = findViewById(R.id.players_answer_time_player1);
        pickerAnswerTime1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickerAnswerTime1.setMinValue(0);
        pickerAnswerTime1.setMaxValue(2);
        pickerAnswerTime1.setValue(0);
        pickerAnswerTime1.setDisplayedValues(displayedValues);

        pickerAnswerTime2 = findViewById(R.id.players_answer_time_player2);
        pickerAnswerTime2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickerAnswerTime2.setMinValue(0);
        pickerAnswerTime2.setMaxValue(2);
        pickerAnswerTime2.setValue(0);
        pickerAnswerTime2.setDisplayedValues(displayedValues);

        pickerAnswerTime3 = findViewById(R.id.players_answer_time_player3);
        pickerAnswerTime3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickerAnswerTime3.setMinValue(0);
        pickerAnswerTime3.setMaxValue(2);
        pickerAnswerTime3.setValue(0);
        pickerAnswerTime3.setDisplayedValues(displayedValues);

        pickerAnswerTime4 = findViewById(R.id.players_answer_time_player4);
        pickerAnswerTime4.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickerAnswerTime4.setMinValue(0);
        pickerAnswerTime4.setMaxValue(2);
        pickerAnswerTime4.setValue(0);
        pickerAnswerTime4.setDisplayedValues(displayedValues);


    }

    public int getImageId(int numPion) {

        int position = getCurrentPosition(numPion);

        int nextPosition;
        if (position < 17)
            nextPosition = position + 1;
        else
            nextPosition = 0;


        while (pionAvailable[nextPosition] != 0) {

            if (nextPosition < 17)
                nextPosition++;
            else
                nextPosition = 0;
        }

        pionAvailable[position] = 0;
        pionAvailable[nextPosition] = numPion;

        return nextPosition;
    }

    public int getCurrentPosition(int numPion) {

        int position = 0;

        while (pionAvailable[position] != numPion)
            position++;

        return position;
    }

    public void initButton() {

        textPlayer1 = findViewById(R.id.players_name_player1);
        textPlayer2 = findViewById(R.id.players_name_player2);
        textPlayer3 = findViewById(R.id.players_name_player3);
        textPlayer4 = findViewById(R.id.players_name_player4);


        buttonLaunchGame = findViewById(R.id.players_button_play);

        buttonLaunchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayersToSettings();
            }
        });
    }

    private void addPlayersToSettings() {

        List<Player> playerList = new ArrayList<Player>();
        Player player;

        if (textPlayer1.getText().length() == 0)
            player = new Player(getString(R.string.player_1), pionImage[getCurrentPosition(1)], getAnswerTime(pickerAnswerTime1.getValue()));
        else
            player = new Player(textPlayer1.getText().toString(), pionImage[getCurrentPosition(1)], getAnswerTime(pickerAnswerTime1.getValue()));
        playerList.add(player);

        if (nbPlayer > 1) {
            if (textPlayer2.getText().length() == 0)
                player = new Player(getString(R.string.player_2), pionImage[getCurrentPosition(2)], getAnswerTime(pickerAnswerTime1.getValue()));
            else
                player = new Player(textPlayer2.getText().toString(), pionImage[getCurrentPosition(2)], getAnswerTime(pickerAnswerTime2.getValue()));
            playerList.add(player);
        }

        if (nbPlayer > 2) {
            if (textPlayer3.getText().length() == 0)
                player = new Player(getString(R.string.player_3), pionImage[getCurrentPosition(3)], getAnswerTime(pickerAnswerTime1.getValue()));
            else
                player = new Player(textPlayer3.getText().toString(), pionImage[getCurrentPosition(3)], getAnswerTime(pickerAnswerTime3.getValue()));
            playerList.add(player);
        }

        if (nbPlayer > 3) {
            if (textPlayer4.getText().length() == 0)
                player = new Player(getString(R.string.player_4), pionImage[getCurrentPosition(4)], getAnswerTime(pickerAnswerTime1.getValue()));
            else
                player = new Player(textPlayer4.getText().toString(), pionImage[getCurrentPosition(4)], getAnswerTime(pickerAnswerTime4.getValue()));
            playerList.add(player);
        }
        parametersViewModel.initPlayers(playerList);
    }

    private int getAnswerTime(int pickerValue) {

        switch (pickerValue) {
            case 0:
                return 15;
            case 1:
                return 30;
            case 2:
                return 45;
            default:
                return 30;
        }
    }

    @Override
    public void processDisplayQuestionTypeList(List<String> types) {

    }

    @Override
    public void processParametersFinish(Boolean result) {

    }

    @Override
    public void processPlayersFinish() {
        Intent i = new Intent(PlayersActivity.this, GameActivity.class);
        startActivity(i);
    }

    @Override
    public void processNumberPlayers(int numberPlayers) {
        nbPlayer = numberPlayers;
        setNumberPlayer(nbPlayer);
    }

    @Override
    public void processDisplayMessage(String message) {
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialog_layout, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.DialogTextTheme)
                .setView(inflator);

        alertDialogBuilder.setPositiveButton(getString(R.string.main_dialog_message_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final TextView messageTextView = (TextView) inflator.findViewById(R.id.general_dialog_message);
        messageTextView.setText(message);
        alertDialogBuilder.show();
    }
}
