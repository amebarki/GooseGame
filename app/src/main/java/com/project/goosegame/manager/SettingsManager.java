package com.project.goosegame.manager;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsManager {

    private static SettingsManager instance = null;
    private int FontSize;
    private int primary;
    private int secundary;
    private int select;


    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }


    public int getFontSize() {
        return FontSize;
    }

    public void setFontSize(int fontSize) {
        FontSize = fontSize;
    }


    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getSecundary() {
        return secundary;
    }

    public void setSecundary(int secundary) {
        this.secundary = secundary;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }
}
