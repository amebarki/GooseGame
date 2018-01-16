package com.project.goosegame.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.goosegame.R;

import java.io.File;
import java.util.List;

/**
 * Created by Adam on 16/01/2018.
 */

public class FileExplorerAdapter extends ArrayAdapter {

    private Context context;
    private int resourceRow; // represents the list row file as an int
    private List<File> listFiles; // list of files got from the model

    public FileExplorerAdapter(Context context, int ressource, List<File> flist) {
        super(context, ressource, flist);
        this.context = context;
        this.resourceRow = ressource;
        this.listFiles = flist;
    }

    public FileExplorerAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resourceRow = resource;
    }

    /*Does exactly what it looks like.  Pulls out a specific File Object at a specified index.
      Remember that our FileArrayAdapter contains a list of Files it gets from our model's getAllFiles(),
      so getitem(0) is the first file in that List, getItem(1), the second, etc.  ListView uses this
      method internally.*/
    @Override
    public File getItem(int i) {
        return listFiles.get(i);
    }

    /**
     * Allows me to pull out specific views from the row xml file for the ListView.   I can then
     * make any modifications I want to the ImageView and TextViews inside it.
     *
     * @param position    - The position of an item in the List received from my model.
     * @param convertView - list_row.xml as a View object.
     * @param parent      - The parent ViewGroup that holds the rows.  In this case, the ListView.
     ***/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater.from(context));
            v = inflater.inflate(resourceRow, null);
        }
        FileExplorerHolder viewHolder = (FileExplorerHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new FileExplorerHolder();
            viewHolder.picture = (ImageView) v.findViewById(R.id.row_file_explorer_image_view);
            viewHolder.nameView = (TextView) v.findViewById(R.id.row_file_explorer_text_view_name);
            viewHolder.detailsView = (TextView) v.findViewById(R.id.row_file_explorer_text_view_details);
            v.setTag(viewHolder);
        }
        File file = getItem(position);
        if (file.isDirectory()) {
            //viewHolder.picture.setImageResource(R);
        } else {
            //viewHolder.picture.setImageResource(R.drawable.file);
            if (file.length() > 0) {
                viewHolder.detailsView.setText(String.valueOf(file.length()));
            }
        }
        viewHolder.nameView.setText(file.getName());

        return v;
    }


    class FileExplorerHolder {
        TextView nameView;
        TextView detailsView;
        ImageView picture;
    }


}
