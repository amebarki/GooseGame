package com.project.goosegame.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.project.goosegame.model.CSVFileParser;
import com.project.goosegame.model.Question;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class MainViewModel extends BaseObservable {
    private Context context;
    private CSVFileParser csvFileParser;
    private ArrayList<Question> questionsList;
    public MainViewModel(Context context) {
        questionsList = new ArrayList<>();
        this.context = context;

    }

    public void launchGame() {
        Toast.makeText(context, "Open Activity GooseGame", Toast.LENGTH_SHORT).show();
    }


    public Intent openFileExplorer()
    {
        File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GameOfGoose" + File.separator);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/comma-separated-values");
        return  intent;
    }

    public boolean parseFile(int resultCode,Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            File pathFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GameOfGoose" + File.separator);
            if (!pathFolder.exists()) {
                pathFolder.mkdir();
            }
            String filePath = null;
            filePath = data.getData().getPath();
            File file = new File(filePath);
            Uri selectedUri = Uri.fromFile(file);
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            if(fileExtension.compareTo("csv")==0)
            {
                File f = new File(pathFolder.getAbsolutePath(),file.getName());
                csvFileParser = new CSVFileParser(f);
                questionsList.addAll(csvFileParser.read());
                for (int i = 0; i < questionsList.size(); i++) {
                    Log.d("TAGO",questionsList.get(i).toString());
                }
                return true;
            }
        }
        return false;
    }



}


