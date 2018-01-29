package com.project.goosegame.utils.observable;

/**
 * Created by Adam on 25/01/2018.
 */

public interface MainObservable extends ColorObservable{

    public void processStartParametersActivity();
    public void processStartQuestionsActivity();
    public void processStartSettingsActivity();
    public void processDisplayMessage(String message);

}
