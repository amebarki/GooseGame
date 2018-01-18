package com.project.goosegame.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.project.goosegame.utils.async.AsyncParameters;
import com.project.goosegame.viewModel.ParametersViewModel;

import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class ParametersActivity extends AppCompatActivity implements AsyncParameters{

private ParametersViewModel parametersViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parametersViewModel = new ParametersViewModel(getApplicationContext());
        parametersViewModel.getQuestionTypes();
        parametersViewModel.response = this;

    }

    @Override
    public void processDisplayQuestionTypes(List<String> types) {
        for (int i = 0; i < types.size(); i++) {
            Log.d("TAGO",types.get(i));
        }
    }
}
