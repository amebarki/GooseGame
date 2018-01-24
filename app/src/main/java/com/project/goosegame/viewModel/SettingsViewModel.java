package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.Color;

import com.project.goosegame.manager.SettingsManager;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.view.activity.ColorPickerActivity;
import com.project.goosegame.view.activity.SettingsExampleActivity;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsViewModel extends BaseObservable {
    // couleurs 3 primary, secondary, select
    // font size

    private Context context;
    private SettingsManager settingsManager = null;
    public SettingsObservable settingsObservable = null;

    public SettingsViewModel(Context context) {
        this.context = context;
        settingsManager = SettingsManager.getInstance();

    }
// TODO: 23/01/2018 prepare selection via SettingsManager 

    public void getPrimaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(0);
        //startActivityForResult(intent, 1);
        settingsObservable.processOpenPrimaryColor(intent);
    }

    public void getSecundaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(1);
        //startActivityForResult(intent, 1);
        settingsObservable.processOpenSecundaryColor(intent);
    }

    public void getSelectColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        //startActivityForResult(intent, 1);
        settingsManager.setCurrentSelectColor(2);
        settingsObservable.processOpenSelectColor(intent);
    }

    public void getFontSize() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
    }

    public int getColorSelected(){
        return settingsManager.getCurrentSelectColor();
    }

    public void setPrimaryColor(int primaryColor){
        settingsManager.setPrimary(primaryColor);
    }

    public void setSecundaryColor(int secundaryColor){
        settingsManager.setSecundary(secundaryColor);
    }

    public void setSelectColor(int selectColor){
        settingsManager.setSelect(selectColor);
    }
}

