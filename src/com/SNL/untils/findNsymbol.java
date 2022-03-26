package com.SNL.untils;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * @ClassName: findNsybbol
 * @Description: 提取文法中的非终极符
 * @author: 涵不亏
 * @date: 2021/6/8  10:46
 */
public class findNsymbol {

    public static void main(String[] args) throws Exception{
        String file = "src/resource/SYNwenfa.txt";
        String store = "src/resource/NSymbol.txt";
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(store));

        String s;
        LinkedHashSet<String> set = new LinkedHashSet<String>() ;
        while ((s=reader.readLine())!=null){
            s.trim();
            String[] lists = s.split(" ");
            if(lists.length<=1){
                continue;
            }else {
                String ans = lists[0].trim();

                set.add(ans);
            }
        }

        for (String t:set){
            System.out.println(t);
            writer.write(t);
            writer.newLine();
            writer.flush();
        }

    }

}
