package com.SNL.untils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @ClassName: getLL1
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/10  20:06
 */
public class getLL1 {
    public static void main(String[] args) throws Exception{
        String file = "src/resource/SYNwenfa.txt";
        String file2 = "src/resource/predict2.txt";
        String file3 = "src/resource/LL1.txt";
        BufferedReader reader1 = new BufferedReader(new FileReader(file));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file3));
        String sn,st;
        while ((sn=reader1.readLine())!=null && (st=reader2.readLine())!=null){
            sn = sn.trim();
            st = st.trim();
            String s = sn.split(" ")[0];
            s += (" "+st);
            writer.write(s);
            writer.newLine();
            writer.flush();
        }
    }
}
