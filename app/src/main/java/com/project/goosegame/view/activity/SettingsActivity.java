package com.project.goosegame.view.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

public class SettingsActivity extends AppCompatActivity implements SettingsObservable, AdapterView.OnItemSelectedListener {

    private SettingsViewModel settingsViewModel;
    private QuestionsViewModel questionsViewModel;
    private Button buttonPrimary;
    private Button buttonSecundary;
    private Button buttonSelect;
    private Button buttonValidate;
    private Spinner spinner;
    private List fontSizeList;
    private TextView primaryTextView, secundaryTextView, selectTextView, fontSizeTextView;

    private Drawable customRadioButtonDrawable;
    private Drawable customCheckedRadioButtonDrawable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settingsViewModel = new SettingsViewModel(getApplicationContext());
        settingsViewModel.setSettingsObservable(this);
        this.initialize();

        buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            if (requestCode == 1000) {
                settingsViewModel.checkPrimaryColor();
            } else if (requestCode == 2000) {
                settingsViewModel.checkSecundaryColor();
            } else if (requestCode == 3000) {
                settingsViewModel.checkSelectColor();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void initialize() {
        buttonValidate = findViewById(R.id.settings_button_validate);
        buttonPrimary = findViewById(R.id.settings_button_primary);
        buttonSecundary = findViewById(R.id.settings_button_secundary);
        buttonSelect = findViewById(R.id.settings_button_select);
        spinner = findViewById(R.id.settings_spinner);
        primaryTextView = findViewById(R.id.settings_text_view_primary);
        secundaryTextView = findViewById(R.id.settings_text_view_secundary);
        selectTextView = findViewById(R.id.settings_text_view_select);
        fontSizeTextView = findViewById(R.id.settings_text_view_font_size);

        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
    }

    @Override
    public void processOpenPrimaryColor(Intent intent) {
        startActivityForResult(intent,1000);
    }

    @Override
    public void processOpenSecundaryColor(Intent intent) {
        startActivityForResult(intent,2000);
    }

    @Override
    public void processOpenSelectColor(Intent intent) {
        startActivityForResult(intent,3000);
    }

    @Override
    public void processFontSizeList(List fontSizeList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fontSizeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void processPrimaryColor(int color) {
        buttonPrimary.setBackgroundColor(color);
    }

    @Override
    public void processSecondaryColor(int color) {
        buttonSecundary.setBackgroundColor(color);

    }

    @Override
    public void processSelectColor(int color) {
        buttonSelect.setBackgroundColor(color);

        customRadioButtonDrawable = getDrawable(R.drawable.custom_radio_button);
        customCheckedRadioButtonDrawable = getDrawable(R.drawable.custom_radio_selected_button);

        customRadioButtonDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        customCheckedRadioButtonDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

    }
}
