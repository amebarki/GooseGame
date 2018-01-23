package com.project.goosegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.goosegame.R;
import com.project.goosegame.model.pojo.Player;
import com.project.goosegame.utils.async.AsyncParameters;
import com.project.goosegame.viewModel.ParametersViewModel;

import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class ParametersActivity extends AppCompatActivity implements AsyncParameters
        ,View.OnClickListener {

private ParametersViewModel parametersViewModel;
private List<String> gameTypeList;
private String selectedGameType;

private RadioGroup playerNumberRadioGroup;
private RadioGroup difficultyRadioGroup;
private RadioGroup gameTimeRadioGroup;
private NumberPicker gameTypePicker;
private Button continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.initQuestionTypeList();
        parametersViewModel.response = this;

        playerNumberRadioGroup = findViewById(R.id.param_radio_player_number);
        difficultyRadioGroup = findViewById(R.id.param_radio_difficulty);
        gameTimeRadioGroup = findViewById(R.id.param_radio_game_time);
        gameTypePicker = findViewById(R.id.param_picker_game_type);

        gameTypePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                selectedGameType = gameTypeList.get(newVal - 1);
            }
        });

        continueButton = findViewById(R.id.param_button_continue);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RadioButton radioButton;

        radioButton = findViewById(playerNumberRadioGroup.getCheckedRadioButtonId());
        int nbPlayer = playerNumberRadioGroup.indexOfChild(radioButton) + 1;

        radioButton = findViewById(difficultyRadioGroup.getCheckedRadioButtonId());
        int difficulty = difficultyRadioGroup.indexOfChild(radioButton) + 1;

        radioButton = findViewById(gameTimeRadioGroup.getCheckedRadioButtonId());
        int gameTime = getGameTime(gameTimeRadioGroup.indexOfChild(radioButton));

        parametersViewModel.initGooseGame(nbPlayer,difficulty,1,gameTime,selectedGameType);
    }

    private int getGameTime(int gameTime) {
        switch (gameTime) {
            case 0:
                return 10;
            case 1:
                return 20;
            default:
                return 30;
        }
    }

    @Override
    public void processDisplayQuestionTypeList(List<String> types) {
        gameTypeList = types;

        if (gameTypeList != null) {
            gameTypePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            gameTypePicker.setMinValue(1);
            gameTypePicker.setMaxValue(gameTypeList.size());
            gameTypePicker.setDisplayedValues(gameTypeList.toArray(new String[0]));
        }
    }

    @Override
    public void processParametersFinish(Boolean result) {
        Intent i = new Intent(this, PlayersActivity.class);
        startActivity(i);
    }

    @Override
    public void processPlayersFinish(Boolean result) {

    }

    @Override
    public void processNumberPlayers(int numberPlayers) {

    }

    // TODO: 19/01/2018 Create Button to launch the playersActivity and call the function to initGooseGame of parametersViewModel 
    
}
