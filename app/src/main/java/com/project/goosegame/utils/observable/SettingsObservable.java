package com.project.goosegame.utils.observable;

import android.content.Intent;

import java.util.List;

/**
 * Created by Adam on 23/01/2018.
 */

public interface SettingsObservable extends ColorObservable {


    public void processOpenPrimaryColor(Intent intent);

    public void processOpenSecundaryColor(Intent intent);

    public void processOpenSelectColor(Intent intent);

    public void processFontSizeList(List fontSizeList);
}
