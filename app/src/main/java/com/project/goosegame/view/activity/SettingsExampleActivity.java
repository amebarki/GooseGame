package com.project.goosegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.goosegame.R;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.viewModel.SettingsViewModel;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsExampleActivity extends AppCompatActivity implements SettingsObservable {

    private SettingsViewModel settingsViewModel;
    private Button buttonPrimary;
    private Button buttonSecundary;
    private Button buttonSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsViewModel = new SettingsViewModel(getApplicationContext());
        settingsViewModel.settingsObservable = this;
        buttonPrimary = (Button) findViewById(R.id.settings_button_primary);
        buttonSecundary = (Button) findViewById(R.id.settings_button_secundary);
        buttonSelect = (Button) findViewById(R.id.settings_button_select);


        buttonPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getPrimaryColor();
            }
        });

        buttonSecundary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getSecundaryColor();
            }
        });
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsViewModel.getSelectColor();
            }
        });


    }


    @Override
    public void processOpenPrimaryColor(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void processOpenSecundaryColor(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void processOpenSelectColor(Intent intent) {
        startActivity(intent);
    }
}
