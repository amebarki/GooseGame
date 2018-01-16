package com.project.goosegame.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageButton;

import com.project.goosegame.R;
import com.project.goosegame.databinding.ActivityMainBinding;
import com.project.goosegame.viewModel.MainViewModel;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int INTENT_FILE_CODE = 10;
    private MainViewModel mainViewModel;

    Button buttonLaunchGame;
    ImageButton buttonSettings;
    ImageButton buttonLoadBD;
    ConstraintLayout constraintLayoutSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new MainViewModel(getApplicationContext());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
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
                File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GameOfGoose" + File.separator);
                if (!filePath.exists()) {
                    filePath.mkdir();
                }

                // Just example, you should parse file name for extension
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, INTENT_FILE_CODE);
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
            if (resultCode == RESULT_OK) {
//                String filePath = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    filePath = FileUtils.getPath(this, data.getData());
//                }
//                File file = new File(filePath);
//                CSVReader.parseCSV(file, this);
            }
        }
    }
}
