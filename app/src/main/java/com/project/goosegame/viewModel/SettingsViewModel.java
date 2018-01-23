package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;

import com.project.goosegame.manager.SettingsManager;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsViewModel extends BaseObservable {
    // couleurs 3 primary, secondary, select
    // font size

    private Context context;
    private SettingsManager settingsManager = null;

    public SettingsViewModel(Context context) {
        this.context = context;
        settingsManager = SettingsManager.getInstance();
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}

