package com.project.goosegame.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.goosegame.R;
import com.project.goosegame.utils.colorpicker.ColorPicker;
import com.project.goosegame.viewModel.SettingsViewModel;

public class ColorPickerActivity extends AppCompatActivity {

    private ColorPicker colorView;
    private ColorPicker saturationView;
    private Button validateButton;
    private int colorSelected;
    SharedPreferences.Editor editor;

    private SettingsViewModel settingsViewModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.activity_color_picker, null);
        setContentView(view);
        final Intent intent = getIntent();
        editor = getSharedPreferences("couleurs", MODE_PRIVATE).edit();
        settingsViewModel = new SettingsViewModel(getApplicationContext());

        validateButton = (Button) findViewById(R.id.validColor);
        colorView = (ColorPicker) findViewById(R.id.top);
        saturationView = (ColorPicker) findViewById(R.id.bottom);
        colorView.setBrightnessGradientView(saturationView);
        saturationView.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(ColorPicker view, int color) {
                GradientDrawable bgShape = (GradientDrawable) validateButton.getBackground();
                bgShape.setColor(color);
                colorSelected = color;

            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (settingsViewModel.getColorSelected()) {
                    case 0:
                        settingsViewModel.setPrimaryColor(colorSelected);
                        editor.putInt("PrimaryColor",colorSelected);
                        editor.commit();
                        finish();
                        break;
                    case 1:
                        settingsViewModel.setSecundaryColor(colorSelected);
                        editor.putInt("SecundaryColor",colorSelected);
                        editor.commit();
                        finish();
                        break;
                    case 2:
                        settingsViewModel.setSelectColor(colorSelected);
                        editor.putInt("SelectColor",colorSelected);
                        editor.commit();
                        finish();
                        break;
                    default:
                        finish();
                        break;
                }
            }
        });


    }


}
