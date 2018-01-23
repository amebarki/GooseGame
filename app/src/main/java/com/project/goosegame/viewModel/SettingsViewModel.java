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
        intent.putExtra("type","texteButton");

        //startActivityForResult(intent, 1);
    }

    public void getSecundaryColor() {

    }

    public void getSelectColor() {

    }

    public void getFontSize() {

    }

}

