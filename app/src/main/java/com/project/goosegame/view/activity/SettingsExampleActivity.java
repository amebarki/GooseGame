package com.project.goosegame.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.viewModel.QuestionsViewModel;
import com.project.goosegame.viewModel.SettingsViewModel;

import java.util.List;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsExampleActivity extends AppCompatActivity implements SettingsObservable, AdapterView.OnItemSelectedListener {

    private SettingsViewModel settingsViewModel;
    private QuestionsViewModel questionsViewModel;
    private Button buttonPrimary;
    private Button buttonSecundary;
    private Button buttonSelect;
    private Spinner spinner;
    private List fontSizeList;
    private TextView primaryTextView, secundaryTextView, selectTextView, fontSizeTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsViewModel = new SettingsViewModel(getApplicationContext());
        settingsViewModel.setSettingsObservable(this);
        this.initialize();

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


        settingsViewModel.getListFontSize();
        settingsViewModel.getListFontSize();

    }


    public void initialize()
    {
        buttonPrimary = (Button) findViewById(R.id.settings_button_primary);
        buttonSecundary = (Button) findViewById(R.id.settings_button_secundary);
        buttonSelect = (Button) findViewById(R.id.settings_button_select);
        spinner = (Spinner) findViewById(R.id.settings_spinner);
        primaryTextView = (TextView) findViewById(R.id.settings_text_view_primary);
        secundaryTextView = (TextView) findViewById(R.id.settings_text_view_secundary);
        selectTextView = (TextView) findViewById(R.id.settings_text_view_select);
        fontSizeTextView = (TextView) findViewById(R.id.settings_text_view_font_size);

        spinner.setOnItemSelectedListener(this);

        buttonPrimary.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        buttonSecundary.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
        buttonSelect.setBackgroundColor(getResources().getColor(R.color.colorSelect));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
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

    @Override
    public void processFontSizeList(List fontSizeList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fontSizeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

}
