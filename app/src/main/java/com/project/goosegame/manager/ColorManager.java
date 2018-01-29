package com.project.goosegame.manager;

/**
 * Created by Adam on 26/01/2018.
 */

public class ColorManager {


    private static ColorManager instance = null;
    private int primary = -1;
    private int secundary = -1;
    private int select = -1;

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
