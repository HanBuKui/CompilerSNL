package com.SNL.lex.Client;

/**
 * @ClassName: Client
 * @Description: TODO
 * @author: qby
 * @date: 2021/6/9  17:09
 */
import com.SNL.lex.Entity.*;
import com.SNL.lex.Funtion.ReadFile;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) throws IOException {

        String fileName = "src/resource/snlCode/src.txt";
        ReadFile read = new ReadFile();
        List<Token> tokens = read.read(fileName);
        for (Token token : tokens) {
            System.out.println("("+(token.getLine()+1)+","+token.getTokenType()+","+token.getValue()
                    +")");
        }

//		GrammerAnalysis g = new GrammerAnalysis();
//		g.CreatLL1Table();

//		ChooseProcess.predict(1);


    }

}

