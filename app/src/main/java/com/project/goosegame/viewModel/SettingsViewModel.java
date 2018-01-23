package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.Color;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsViewModel extends BaseObservable {
    // couleurs 3 primary, secondary, select
    // font size

    private Context context;
    private int FontSize;
    private Color primary;
    private Color secundary;
    private Color select;

    public SettingsViewModel(Context context){
        this.context = context;
    }



}

