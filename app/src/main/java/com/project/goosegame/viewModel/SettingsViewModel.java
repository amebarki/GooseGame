package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.widget.Toast;

import com.project.goosegame.manager.SettingsManager;
import com.project.goosegame.utils.observable.SettingsObservable;
import com.project.goosegame.view.activity.ColorPickerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsViewModel extends BaseObservable {

    private Context context;
    private SettingsManager settingsManager = null;
    private SettingsObservable response = null;
    private List fontSizeList = null;

    public SettingsViewModel(Context context) {
        this.context = context;
        settingsManager = SettingsManager.getInstance();
        fontSizeList = new ArrayList();
    }

    public void setSettingsObservable(SettingsObservable settingsObservable) {
        this.response = settingsObservable;
    }

    public void getListFontSize() {
        for (int i = 0; i < 20; i++) {
            fontSizeList.add(11 + i);
        }
        response.processFontSizeList(fontSizeList);
    }

    public void getPrimaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(0);
        response.processOpenPrimaryColor(intent);
    }

    public void getSecundaryColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(1);
        response.processOpenSecundaryColor(intent);
    }

    public void getSelectColor() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
        settingsManager.setCurrentSelectColor(2);
        response.processOpenSelectColor(intent);
    }

    public void getFontSize() {
        Intent intent = new Intent(context, ColorPickerActivity.class);
    }

    public int getColorSelected() {
        return settingsManager.getCurrentSelectColor();
    }

    public void setPrimaryColor(int primaryColor) {
        settingsManager.setPrimary(primaryColor);
    }

    public void setSecundaryColor(int secundaryColor) {
        settingsManager.setSecundary(secundaryColor);
    }

    public void setSelectColor(int selectColor) {
        settingsManager.setSelect(selectColor);
    }
}

