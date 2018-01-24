package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;

import com.project.goosegame.manager.SettingsManager;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.view.activity.ColorPickerActivity;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsViewModel extends BaseObservable {
    // couleurs 3 primary, secondary, select
    // font size

    private Context context;
    private SettingsManager settingsManager = null;
    private SettingsObservable response = null;

    public SettingsViewModel(Context context) {
        this.context = context;
        settingsManager = SettingsManager.getInstance();

    }

    public void setSettingsObservable(SettingsObservable settingsObservable){
        this.response = settingsObservable;
    }

    public void getPrimaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(0);
        //startActivityForResult(intent, 1);
        response.processOpenPrimaryColor(intent);
    }

    public void getSecundaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(1);
        //startActivityForResult(intent, 1);
        response.processOpenSecundaryColor(intent);
    }

    public void getSelectColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        //startActivityForResult(intent, 1);
        settingsManager.setCurrentSelectColor(2);
        response.processOpenSelectColor(intent);
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

