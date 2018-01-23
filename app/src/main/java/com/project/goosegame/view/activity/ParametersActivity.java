package com.project.goosegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

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
private NumberPicker gameTypePicker;
private List<String> gameTypeList;
private Button continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);
        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.initQuestionTypeList();
        parametersViewModel.response = this;

        continueButton = findViewById(R.id.param_button_continue);
        continueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, PlayersActivity.class);
        startActivity(i);
    }

    @Override
    public void processDisplayQuestionTypeList(List<String> types) {
        gameTypeList = types;

        if (gameTypeList != null) {
            gameTypePicker = findViewById(R.id.param_picker_game_type);
            gameTypePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            gameTypePicker.setMinValue(1);
            gameTypePicker.setMaxValue(gameTypeList.size());
            gameTypePicker.setDisplayedValues(gameTypeList.toArray(new String[0]));
        }
    }
}
