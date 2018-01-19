package com.project.goosegame.utils;

import android.util.Log;

import com.project.goosegame.model.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 17/01/2018.
 */

public class CSVFileParser {

    private FileInputStream fileInputStream;
    private static final String TAG  ="CSV_PARSER";
    private ArrayList<Question> questionsList;
    public CSVFileParser(File file){
        questionsList = new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> read(){
        List resultList = new ArrayList();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        try {
            // avoid the first line wich is the name of the columns
            String csvLine=reader.readLine();
            while ((csvLine = reader.readLine()) != null) {

                String[] row = csvLine.split(";");
                Question question = new Question(row[1],Integer.parseInt(row[2]),row[3],row[4],
                        row[5],row[6],row[7],Integer.parseInt(row[8]));
                questionsList.add(question);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                fileInputStream.close();
                reader.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
        return questionsList;
    }
}