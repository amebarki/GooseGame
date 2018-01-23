package com.project.goosegame.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.goosegame.R;
import com.project.goosegame.utils.GradientView;
import com.project.goosegame.viewModel.SettingsViewModel;

public class ColorPickerActivity extends AppCompatActivity {

    private GradientView mTop;
    private GradientView mBottom;
    private TextView mTextView;
    private Button valider;
    private int retourCoul;
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


        valider = (Button) findViewById(R.id.validColor);
        mTop = (GradientView)findViewById(R.id.top);
        mBottom = (GradientView)findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                GradientDrawable bgShape = (GradientDrawable)valider.getBackground();
                bgShape.setColor(color);
                retourCoul = color;

            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ColorPickerActivity.this, "Couci", Toast.LENGTH_SHORT).show();
                /*String a =intent.getStringExtra("type");
                String b =intent.getStringExtra("text");

                if(intent.getStringExtra("type").equals("texteBouton")){
                    ColorManager.getInstance().texteBouton = retourCoul;
                    editor.putInt("texteBouton", retourCoul);
                    editor.commit();
                    finish();
                }if(intent.getStringExtra("type").equals("fondBouton")){
                    ColorManager.getInstance().fondBouton = retourCoul;
                    editor.putInt("fondBouton", retourCoul);
                    editor.commit();
                    finish();
                }if(intent.getStringExtra("type").equals("texteGeneral")){
                    ColorManager.getInstance().texteGeneral = retourCoul;
                    editor.putInt("texteGeneral", retourCoul);
                    editor.commit();
                    finish();
                }if(intent.getStringExtra("type").equals("fondGeneral")){
                    ColorManager.getInstance().fondGeneral = retourCoul;
                    editor.putInt("fondGeneral", retourCoul);
                    editor.commit();
                    finish();
                }if(intent.getStringExtra("type").equals("choixCase")){
                    ColorManager.getInstance().choixCase = retourCoul;
                    editor.putInt("choixCase", retourCoul);
                    editor.commit();
                    finish();
                }*/


            }
        });


    }





}
