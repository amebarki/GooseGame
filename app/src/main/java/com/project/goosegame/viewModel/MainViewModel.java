package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.widget.Toast;


public class MainViewModel extends BaseObservable {
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;

    }

    public void launchGame() {
        Toast.makeText(context, "Open Activity GooseGame", Toast.LENGTH_SHORT).show();
    }


}


