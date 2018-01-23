package com.project.goosegame.manager;

import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by iem on 12/01/16.
 */
public class ColorManager {

    private static ColorManager instance = null;

    private int primary;
    private int secundary;
    private int select;


    public static ColorManager getInstance() {
        if (instance == null) {
            instance = new ColorManager();
        }
        return instance;
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
