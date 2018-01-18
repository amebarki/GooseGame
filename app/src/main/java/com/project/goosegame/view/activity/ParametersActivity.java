package com.project.goosegame.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;

import com.project.goosegame.R;

import java.util.ArrayList;
import java.util.List;

public class ParametersActivity extends AppCompatActivity {
    NumberPicker pickerGameType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        List<String> gameTypeList = new ArrayList<String>();
        gameTypeList.add("Grammaire");
        gameTypeList.add("Conjugaison");
        gameTypeList.add("Histoire");
        gameTypeList.add("Géographie");

        pickerGameType = findViewById(R.id.param_picker_game_type);
        pickerGameType.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        pickerGameType.setMinValue(1);
        pickerGameType.setMaxValue(gameTypeList.size());
        pickerGameType.setDisplayedValues(gameTypeList.toArray(new String[0]));
    }
}
