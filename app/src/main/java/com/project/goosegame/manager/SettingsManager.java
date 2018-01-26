package com.project.goosegame.manager;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsManager {

    private static SettingsManager instance = null;
    private int FontSize;
    private int currentSelectColor;

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    public ColorManager getColorManager()
    {
        return ColorManager.getInstance();
    }


    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }


    public int getCurrentSelectColor() {
        return currentSelectColor;
    }


    public void setCurrentSelectColor(int currentSelectColor) {
        this.currentSelectColor = currentSelectColor;
    }
}
