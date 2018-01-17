package com.project.goosegame.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.goosegame.R;
import com.project.goosegame.databinding.ActivityMainBinding;
import com.project.goosegame.viewModel.MainViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.project.goosegame.R;
import com.project.goosegame.databinding.ActivityMainBinding;
import com.project.goosegame.model.MainModel;
import com.project.goosegame.viewModel.MainViewModel;


/**
 * Created by Adam on 15/01/2018.
 */


public class MainActivity extends AppCompatActivity {
    private static final int INTENT_FILE_CODE = 10;
    private MainViewModel mainViewModel;

    Button buttonLaunchGame;
    ImageButton buttonSettings;
    ImageButton buttonLoadBD;
    ConstraintLayout constraintLayoutSplashScreen;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        mainViewModel = new MainViewModel(getApplicationContext());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(mainViewModel);

        constraintLayoutSplashScreen = findViewById(R.id.imageSplashScreen);

        buttonLaunchGame = findViewById(R.id.buttonLaunchGame);
        buttonLaunchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Change activity
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Change activity
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        buttonLoadBD = findViewById(R.id.buttonLoadBD);
        buttonLoadBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(mainViewModel.openFileExplorer(), INTENT_FILE_CODE);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                constraintLayoutSplashScreen.setVisibility(View.GONE);
            }
        }, 2000);


//        if(databaseIsEmpty()) {
//            Toast.makeText(this, "Aucune question dans la base !", Toast.LENGTH_LONG).show();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_FILE_CODE) {
            mainViewModel.loadFile(resultCode, data);

        }
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }
}
