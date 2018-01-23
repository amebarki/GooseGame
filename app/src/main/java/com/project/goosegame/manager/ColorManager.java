package com.project.goosegame.manager;

import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by iem on 12/01/16.
 */
public class ColorManager {

    private static ColorManager colorInstance = new ColorManager();


    static public int texteBouton;
    static public int fondBouton;
    static public int texteGeneral;
    static public int fondGeneral;
    static public int choixCase;


    public static ColorManager getInstance() {
        return colorInstance;
    }

}
