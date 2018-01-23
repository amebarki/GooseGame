package com.project.goosegame.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.project.goosegame.viewModel.SettingsViewModel;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsExampleActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsViewModel = new SettingsViewModel(getApplicationContext());

    }
}
