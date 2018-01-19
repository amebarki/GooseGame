package com.project.goosegame.manager;

import com.project.goosegame.model.GooseModel;

/**
 * Created by Adam on 19/01/2018.
 */

public class GameManager {

    private static GameManager instance = null;
    private GooseModel gooseModel = null;


    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }


    public void setGooseModel(GooseModel gooseModel)
    {
        this.gooseModel = gooseModel;
    }

    public GooseModel getGooseModel(){
        return gooseModel;
    }
}
