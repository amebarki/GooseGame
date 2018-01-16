package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.widget.Toast;

/**
 * Created by Adam on 15/01/2018.
 */

public class MainViewModel extends BaseObservable {

    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void launchGame() {
        Toast.makeText(context, "Open Activity GooseGame", Toast.LENGTH_SHORT).show();
    }

}


