package com.SNL.untils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @ClassName: getPredict
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/10  19:47
 */
public class getPredict {
    public static void main(String[] args) throws Exception{
        String file = "src/resource/predict.txt";
        String file2 = "src/resource/predict2.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file2));

        String s;
        while ((s=reader.readLine())!=null){
            s = s.trim();
            s = s.substring(1,s.length()-1);
            //System.out.println(s);
            s.trim();
            //String[] split = s.split(",");
            s = s.replace(","," ");
            s = s.trim();
            System.out.println(s);
            writer.write(s);
            writer.newLine();
            writer.flush();
        }
    }
}
