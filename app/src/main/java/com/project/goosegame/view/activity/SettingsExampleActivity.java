package com.project.goosegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.goosegame.R;
import com.project.goosegame.viewModel.SettingsViewModel;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsExampleActivity extends AppCompatActivity {

    private SettingsViewModel settingsViewModel;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsViewModel = new SettingsViewModel(getApplicationContext());
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsExampleActivity.this, ColorPickerActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }



}
