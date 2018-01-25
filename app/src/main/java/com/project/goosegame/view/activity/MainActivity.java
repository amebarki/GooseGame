package com.project.goosegame.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.goosegame.R;
import com.project.goosegame.bdd.database.AppQuestionDatabase;
import com.project.goosegame.manager.QuestionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 15/01/2018.
 */

public class MainActivity extends AppCompatActivity{

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
        setContentView(R.layout.activity_main);
        checkPermissions();

        constraintLayoutSplashScreen = findViewById(R.id.imageSplashScreen);

        buttonLaunchGame = findViewById(R.id.main_button_play);
        buttonLaunchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ParametersActivity.class);
                startActivity(i);
            }
        });

        buttonSettings = findViewById(R.id.main_button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Change activity
                Intent i = new Intent(MainActivity.this, SettingsExampleActivity.class);
                startActivity(i);
            }
        });

        buttonLoadBD = findViewById(R.id.main_button_question);
        buttonLoadBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(i);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                constraintLayoutSplashScreen.setVisibility(View.GONE);
            }
        }, 2000);

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                QuestionManager.getInstance().setAppQuestionDatabase(AppQuestionDatabase.getInstance(getApplicationContext()));
                return QuestionManager.getInstance().createQuestions();
            }
            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        }.execute();

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
