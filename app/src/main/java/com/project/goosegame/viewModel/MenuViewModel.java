package com.project.goosegame.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.Toast;

import com.project.goosegame.BR;
import com.project.goosegame.model.MenuModel;

/**
 * Created by Adam on 15/01/2018.
 */

public class MenuViewModel extends BaseObservable {

    private MenuModel menu;
    private Context context;

    public MenuViewModel(MenuModel menu, Context context) {
        this.menu = menu;
        this.context = context;
    }


    @Bindable
    public String getTitle()
    {
        return this.menu.getTitle();
    }

    public void setTitle(String title)
    {
        this.menu.setTitle(title);
        notifyPropertyChanged(BR.title);
    }

    public View.OnClickListener onPlayClicked()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Open Activity GooseGame" , Toast.LENGTH_SHORT).show();
            }
        };
    }

    public View.OnClickListener onOptionsClicked(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Open Activity Options", Toast.LENGTH_SHORT).show();
            }
        };
    }


}


