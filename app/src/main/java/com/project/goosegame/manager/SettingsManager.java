package com.project.goosegame.manager;

import android.content.Context;
import android.graphics.Color;

/**
 * Created by Adam on 23/01/2018.
 */

public class SettingsManager {

    private static SettingsManager instance = null;

    private int FontSize;
    private Color primary;
    private Color secundary;
    private Color select;

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

    public Color getPrimary() {
        return primary;
    }

    public void setPrimary(Color primary) {
        this.primary = primary;
    }

    public Color getSecundary() {
        return secundary;
    }

    public void setSecundary(Color secundary) {
        this.secundary = secundary;
    }

    public Color getSelect() {
        return select;
    }

    public void setSelect(Color select) {
        this.select = select;
    }
}
