package com.project.goosegame.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.goosegame.R;
import com.project.goosegame.databinding.ActivityMenuBinding;
import com.project.goosegame.model.MenuModel;
import com.project.goosegame.viewModel.MenuViewModel;

public class MenuActivity extends AppCompatActivity {

    private MenuViewModel menu_vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menu_vm = new MenuViewModel(new MenuModel("GooseModel"),getApplicationContext());
        ActivityMenuBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_menu);
        binding.setMenuvm(menu_vm);
    }
}
