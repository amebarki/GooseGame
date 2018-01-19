package com.project.goosegame.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;

import com.project.goosegame.R;
import com.project.goosegame.utils.async.AsyncParameters;
import com.project.goosegame.viewModel.ParametersViewModel;

import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class ParametersActivity extends AppCompatActivity implements AsyncParameters{

private ParametersViewModel parametersViewModel;
private NumberPicker gameTypePicker;
private List<String> gameTypeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.initQuestionTypeList();
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
