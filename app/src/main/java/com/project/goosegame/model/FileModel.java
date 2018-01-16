package com.project.goosegame.model;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by Adam on 16/01/2018.
 */

public class FileModel {
    private File currentDirectory; // current location
    private File previousDirectory; // previous location
    private Stack<File> historyNavigation;  // navigation history
    private Context context;
    public static final String TAG = "Current Directory";


    public FileModel(Context context){
        this.context = context;
        init();
    }


    public void init(){
        historyNavigation = new Stack<>();

        // if storage is writable and readable, set current directory to external storage
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            currentDirectory = Environment.getExternalStorageDirectory();
            Log.i(TAG,String.valueOf(currentDirectory));
        }
        else
        {
            Toast.makeText(context, "External storage unavailable", Toast.LENGTH_SHORT).show();
        }

    }


   // Return wether or not we have a previous directory in our history
    // Stack not empty, we have one
    public boolean hasPreviousDirectory(){
        return !historyNavigation.isEmpty();
    }

    // return the previous directory and remove it from the stack
    public File getPreviousDirectory()
    {
        return historyNavigation.pop();
    }


    // set the previous directory for navigation
    public void setPreviousDirectory(File previousDirectory){
        this.previousDirectory = previousDirectory;
        historyNavigation.add(this.previousDirectory);
    }


    /* Getter and Setter of the current directory */
    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }


    // Returns sorted list of all directories and files in a given directory
    public List<File> getAllFiles(File flist){
        // contains all files/directories from the current directory
        File[] listFiles =flist.listFiles();
        // contains list of directories
        List<File> directories = new ArrayList<>();
        // contains list of files
        List<File> files = new ArrayList<>();


        // All directories appear before files
        for(File file : listFiles)
        {
            if(file.isDirectory()){
                directories.add(file);
            }else{
                files.add(file);
            }
        }

        // sort the two arraylist
        Collections.sort(directories);
        Collections.sort(files);

        // add the list of files after the list of directories
        directories.addAll(files);

        return directories;
    }

    // Determine the mime type of a file based on extension
    public String getMimeType(Uri uri)
    {
        String mimeType = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath());
        if(MimeTypeMap.getSingleton().hasExtension(extension)){
                mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }


    public Stack<File> getHistoryNavigation() {
        return historyNavigation;
    }

    public void setHistoryNavigation(Stack<File> historyNavigation) {
        this.historyNavigation = historyNavigation;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
