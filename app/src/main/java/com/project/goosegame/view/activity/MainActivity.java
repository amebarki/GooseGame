package com.project.goosegame.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.project.goosegame.R;
import com.project.goosegame.databinding.ActivityMainBinding;
import com.project.goosegame.model.MainModel;
import com.project.goosegame.viewModel.MainViewModel;


/**
 * Created by Adam on 15/01/2018.
 */

public class MainActivity  extends AppCompatActivity {

    private MainViewModel mainVM;
    private Button mainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainVM = new MainViewModel(new MainModel("GooseModel"), getApplicationContext());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainvm(mainVM);
    }
}
