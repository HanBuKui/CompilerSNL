package com.SNL.lex.Funtion;

import com.SNL.lex.Entity.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ReadFile
 * @Description: TODO
 * @author: qby
 * @date: 2021/6/9  17:13
 */
public class ReadFile {

    private String fileName;
    LeiicalAnalysis analysis = new LeiicalAnalysis();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Token> read(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<Token> tokens = new ArrayList<Token>();
        int line = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while((tempString = reader.readLine())!=null) {
                tokens.addAll(analysis.getTokenList(tempString, line));
                line++;
                //break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
