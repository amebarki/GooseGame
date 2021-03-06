package com.project.goosegame.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.goosegame.R;
import com.project.goosegame.manager.QuestionManager;
import com.project.goosegame.utils.observable.MainObservable;
import com.project.goosegame.viewModel.MainViewModel;

/**
 * Created by Adam on 15/01/2018.
 */

public class MainActivity extends AppCompatActivity implements MainObservable {

    private MainViewModel mainViewModel = null;
    private Button buttonLaunchGame;
    private ImageButton buttonSettings;
    private ImageButton buttonLoadBD;
    private ConstraintLayout constraintLayoutSplashScreen;
    private ConstraintLayout constraintLayoutBackground;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new MainViewModel(getApplicationContext());

        getPrefsColor();

        mainViewModel.setResponse(this);

        mainViewModel.importBaseQuestions();

        buttonLaunchGame = findViewById(R.id.main_button_play);
        buttonLaunchGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.verifyListQuestions();
            }
        });

        buttonSettings = findViewById(R.id.main_button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertDialogSecretCode(1);
            }
        });

        buttonLoadBD = findViewById(R.id.main_button_question);
        buttonLoadBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialogSecretCode(2);
            }
        });

        mainViewModel.checkPrimaryColor();
        mainViewModel.checkSecundaryColor();
        mainViewModel.checkSelectColor();
        mainViewModel.checkPermissions(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                constraintLayoutSplashScreen.setVisibility(View.GONE);
            }
        }, 2000);



    }

    private void getPrefsColor() {
        int defaultPrimaryColor;
        int defaultSecondaryColor;
        int defaultSelectColor;
        int primary;
        int secondary;
        int select;

        sharedPref = this.getSharedPreferences(getString(R.string.shared_pref_color), this.MODE_PRIVATE);
        defaultPrimaryColor = getResources().getColor(R.color.colorPrimary);
        primary = sharedPref.getInt(getString(R.string.saved_primary_color),defaultPrimaryColor);
        defaultSecondaryColor = getResources().getColor(R.color.colorSecondary);
        secondary = sharedPref.getInt(getString(R.string.saved_secondary_color),defaultSecondaryColor);
        defaultSelectColor = getResources().getColor(R.color.colorSelect);
        select = sharedPref.getInt(getString(R.string.saved_select_color),defaultSelectColor);

        mainViewModel.setPrimaryColor(primary);
        mainViewModel.setSecondaryColor(secondary);
        mainViewModel.setSelectColor(select);
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

    @Override
    public void processStartParametersActivity() {
        Intent i = new Intent(MainActivity.this, ParametersActivity.class);
        startActivity(i);
    }

    @Override
    public void processStartQuestionsActivity() {
        Intent i = new Intent(MainActivity.this, QuestionActivity.class);
        startActivity(i);
    }

    @Override
    public void processStartSettingsActivity() {
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(i);
    }

    @Override
    public void processDisplayMessage(String message) {
        createAlertDialog(message);
    }


    public void createAlertDialog(String message) {
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.dialog_layout, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.DialogTextTheme)
                .setView(inflator);

        alertDialogBuilder.setPositiveButton(getString(R.string.main_dialog_message_ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final TextView et2 = inflator.findViewById(R.id.general_dialog_message);
        et2.setText(message);
        alertDialogBuilder.show();
    }

    public void createAlertDialogSecretCode(final int activity) {
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator = linf.inflate(R.layout.main_dialog_secret_code, null);
        final EditText et2 = (EditText) inflator.findViewById(R.id.main_dialog_password);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.DialogTextTheme)
                .setView(inflator);

        alertDialogBuilder.setPositiveButton(getString(R.string.main_dialog_message_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String code = et2.getText().toString();
                mainViewModel.verifySecretCode(activity, Integer.parseInt(code));
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.main_dialog_message_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void processPrimaryColor(int color) {
        constraintLayoutBackground = findViewById(R.id.main_background);
        constraintLayoutSplashScreen = findViewById(R.id.imageSplashScreen);

        constraintLayoutBackground.setBackgroundColor(color);
        buttonLaunchGame.setTextColor(color);
        constraintLayoutSplashScreen.setBackgroundColor(color);
    }

    @Override
    public void processSecondaryColor(int color) {
    }

    @Override
    public void processSelectColor(int color) {
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.checkPrimaryColor();
        mainViewModel.checkSecundaryColor();
        mainViewModel.checkSelectColor();
    }
}
