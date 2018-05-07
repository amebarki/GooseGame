package com.project.goosegame.utils.parser;

import android.content.Context;
import android.util.Log;

import com.project.goosegame.R;
import com.project.goosegame.model.Question;
import com.project.goosegame.utils.observable.QuestionsObservable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 17/01/2018.
 */

public class CSVFileParser {

    private FileInputStream fileInputStream;
    private ArrayList<Question> questionsList;
    private Context context;
    private QuestionsObservable response;

    public CSVFileParser(Context context, QuestionsObservable response, File file) {
        this.context = context;
        this.response = response;
        questionsList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            response.processErrorParsing(context.getString(R.string.parsing_file_error));
            e.printStackTrace();
        }
    }

    public ArrayList<Question> read(QuestionsObservable response) {
        List resultList = new ArrayList();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(fileInputStream, "WINDOWS-1252"));
        } catch (UnsupportedEncodingException e) {
            response.processErrorParsing(context.getString(R.string.parsing_charset_error));
            e.printStackTrace();
        }
        try {
            // avoid the first line wich is the name of the columns
            String csvLine = URLEncoder.encode(reader.readLine());
            while ((csvLine = reader.readLine()) != null) {

                String[] row = csvLine.split(";");
                Question question = new Question(row[1], determineLevelQuestion(row[2].toUpperCase()), row[3], row[4],
                        row[5], row[6], row[7], Integer.parseInt(row[8]));
                questionsList.add(question);
            }
        } catch (IOException ex) {
            response.processErrorParsing(context.getString(R.string.parsing_reading_error));
            //throw new RuntimeException("Error in reading CSV file : "+ex);
        } finally {
            try {
                fileInputStream.close();
                reader.close();
            } catch (IOException e) {
                response.processErrorParsing(context.getString(R.string.parsing_reading_error));
                //throw new RuntimeException("Error while closing input stream : "+e);
            }
        }
        return questionsList;
    }

    public int determineLevelQuestion(String level) {
        switch (level) {
            case "CP":
                return 1;
            case "CE1":
                return 2;
            case "CE2":
                return 3;
            case "CM1":
                return 4;
            case "CM2":
                return 5;
            default:
                return 1;
        }
    }
}