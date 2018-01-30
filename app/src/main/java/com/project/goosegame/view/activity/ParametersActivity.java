package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.goosegame.R;
import com.project.goosegame.utils.observable.ParametersObservable;
import com.project.goosegame.viewModel.ParametersViewModel;

import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class ParametersActivity extends AppCompatActivity implements ParametersObservable, View.OnClickListener {

    private ParametersViewModel parametersViewModel;
    private List<String> gameTypeList;
    private String selectedGameType;

    private RadioGroup playerNumberRadioGroup;
    private RadioGroup difficultyRadioGroup;
    private RadioGroup gameTimeRadioGroup;
    private NumberPicker gameTypePicker;
    private Button continueButton;
    private ScrollView scrollViewLayout;

    private TextView textViewPlayerNumber;
    private TextView textViewDifficulty;
    private TextView textViewGameTime;
    private TextView textViewType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.initQuestionTypeList();
        parametersViewModel.setParametersObservable(this);


        textViewPlayerNumber = findViewById(R.id.param_lib_player_number);
        textViewGameTime = findViewById(R.id.param_lib_game_time);
        textViewDifficulty = findViewById(R.id.param_lib_difficulty);
        textViewType = findViewById(R.id.param_lib_game_type);

        scrollViewLayout = findViewById(R.id.param_scroll_view);
        playerNumberRadioGroup = findViewById(R.id.param_radio_player_number);
        difficultyRadioGroup = findViewById(R.id.param_radio_difficulty);
        gameTimeRadioGroup = findViewById(R.id.param_radio_game_time);
        gameTypePicker = findViewById(R.id.param_picker_game_type);
        continueButton = findViewById(R.id.param_button_continue);

        parametersViewModel.checkPrimaryColor();
        parametersViewModel.checkSecundaryColor();
        parametersViewModel.checkSelectColor();

        gameTypePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedGameType = gameTypeList.get(newVal - 1);
            }
        });

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

        parametersViewModel.initGooseGame(nbPlayer, difficulty, 1, gameTime, selectedGameType);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1000){
            if(resultCode==1){
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void processDisplayQuestionTypeList(List<String> types) {
        gameTypeList = types;

        if (gameTypeList != null) {
            gameTypePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            gameTypePicker.setMinValue(1);
            gameTypePicker.setMaxValue(gameTypeList.size());
            gameTypePicker.setValue(1);
            selectedGameType = gameTypeList.get(gameTypePicker.getMinValue() - 1);
            gameTypePicker.setDisplayedValues(gameTypeList.toArray(new String[0]));
        }
    }

    @Override
    public void processParametersFinish() {
        Intent i = new Intent(this, PlayersActivity.class);
        startActivityForResult(i,1000);
    }

    @Override
    public void processPlayersFinish() {

    }

    @Override
    public void processNumberPlayers(int numberPlayers) {

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
        final TextView messageTextView = inflator.findViewById(R.id.general_dialog_message);
        messageTextView.setText(message);
        alertDialogBuilder.show();
    }

    @Override
    public void processPrimaryColor(int color) {
        scrollViewLayout.setBackgroundColor(color);

    }

    @Override
    public void processSecondaryColor(int color) {
       /* textViewPlayerNumber.setTextColor(color);
        textViewDifficulty.setTextColor(color);
        textViewGameTime.setTextColor(color);
        textViewType.setTextColor(color);
        continueButton.setTextColor(color);*/
    }

    @Override
    public void processSelectColor(int color) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        parametersViewModel.checkPrimaryColor();
        parametersViewModel.checkSecundaryColor();
        parametersViewModel.checkSelectColor();
    }
}
