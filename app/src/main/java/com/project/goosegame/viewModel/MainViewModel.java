package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.project.goosegame.BR;
import com.project.goosegame.model.MainModel;
import com.project.goosegame.view.activity.ParametersActivity;
import com.project.goosegame.view.activity.SettingsActivity;

/**
 * Created by Adam on 15/01/2018.
 */

public class MainViewModel extends BaseObservable {

    private MainModel mainModel;
    private Context context;

    public MainViewModel(MainModel mainModel, Context context) {
        this.mainModel = mainModel;
        this.context = context;
    }

    @Bindable
    public String getTitle()
    {
        return this.mainModel.getTitle();
    }

    public void setTitle(String title){
        this.mainModel.setTitle(title);
        notifyPropertyChanged(BR.title);
    }


    public void openSettingsActivity()
    {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    public void openParametersActivity(){
        Intent intent = new Intent(context, ParametersActivity.class);
        context.startActivity(intent);
    }


    public void openCSVFile()
    {

    }


}
