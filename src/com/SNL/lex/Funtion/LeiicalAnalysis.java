package com.SNL.lex.Funtion;

import com.SNL.lex.Entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * @ClassName: LeiicalAnalysis
 * @Description: qby
 * @author: 涵不亏
 * @date: 2021/6/9  17:12
 */
public class LeiicalAnalysis {

    public LeiicalAnalysis() {
        super();
    }

    //传入两个参数，一个是当前行的字符串，一个是当前的行数，返回一个Token的序列
    public List<Token> getTokenList(String tempString,int line){

        List<Token> tokens = new ArrayList<Token>();			//新建List来保存得到的token序列
        MatchPattern pattern = new MatchPattern();				//新建匹配模式对象来匹配

        int i=0;
        Scanner scanner = new Scanner(tempString);				//建立Scanner对象实例来读入
        String s = scanner.nextLine();							//去除头部的空格，缩进等完整字符串
        //System.out.println(s);
        String p="";											//新建字符串保存
        int length = s.length();								//获得读取字符串的长度

        while(i<length) {										//当还有下一个字符的时候读取
            p="";
            State state=State.S0;								//新建State来表明程序开始状态
            while(state != State.S13) {							//当状态未结束

                if(state == State.S0) {							//初始状态
                    if(i+1>length) {
                        break;
                    }
                    String p1 = String.valueOf(s.charAt(i++));
                    if(p1.matches(pattern.getLetterPattern())){				//如果读取字母
                        state = State.S1;						//进入标识符状态
                        p = p+p1;
                    }else if(p1.matches(pattern.getNumberPattern())){		//如果读取数字
                        state = State.S2;						//进入数字状态
                        p = p+p1;
                    }else if(p1.matches(pattern.getSinglePattern())||
                            p1.matches(pattern.getBlankPattern())){			//如果读取单分界符
                        state = State.S3;						//进入但分界符状态
                        p = p+p1;
                    }else if(p1.matches(":")) {								//如果读取:
                        state = State.S4;						//进入赋值前导状态
                        p = p+p1;
                    }else if(p1.matches("\\{")) {
                        state = State.S6;						//进入注释状态
                        p = p+p1;
                    }else if(p1.matches(".")) {								//如果读取的为.
                        state = State.S7;						//进入数组下标前导状态
                        p = p+p1;
                    }else if(p1.matches("'")) {								//如果读取的为'
                        state = State.S9;						//进入字符标识状态
                        p = p+p1;
                    }else {
                        state = State.S12;						//进入错误状态
                    }
                }else if(state == State.S1) {					//标识符状态
                    if(i+1>length) {
                        state = State.S13;						//完成标识符的确认
                        Token t = new Token(line, TokenType.ID, p);				//将标识符的token序列加入列表
                        if(t.changeToKeyWord()) {
                            t.setValue("保留字");
                        }
                        tokens.add(t);
                    }else {
                        String p1 = String.valueOf(s.charAt(i++));
                        if(p1.matches(pattern.getLetterPattern())||				//如果为数字或者字母
                                p1.matches(pattern.getNumberPattern())) {
                            state = State.S1;						//继续进入标识符状态
                            p = p+p1;
                        }else {
                            i--;
                            state = State.S13;						//完成标识符的确认
                            Token t = new Token(line, TokenType.ID, p);			//将标识符的token序列加入列表
                            if(t.changeToKeyWord()) {
                                t.setValue("保留字");
                            }
                            tokens.add(t);
                        }
                    }
                }else if(state == State.S2){
                    if(i+1>length) {
                       state = State.S13;
                       Token t = new Token(line, TokenType.INTC, p);		//将数字的token加入序列
                       tokens.add(t);
                    }else{
                        String p1 = String.valueOf(s.charAt(i++));
                        if(p1.matches(pattern.getNumberPattern())) {
                            state = State.S2;
                            p = p+p1;
                        }else {
                            i--;
                            state = State.S13;
                            Token t = new Token(line, TokenType.INTC, p);		//将数字的token加入序列
                            tokens.add(t);
                        }
                    }
                }else if(state == State.S3) {
                    state = State.S13;
                    Token t = new Token(line, TokenType.SINGLE, "分界符");
                    if(t.selectSingle(p)) {
                        if(p.equals(TokenType.EOF.getName())&&i==length) {
                            tokens.add(new Token(line, TokenType.EOF, "."));
                        }else {
                            tokens.add(t);
                        }
                    }
                }else if(state == State.S4) {
                    if(i+1>length) {
                        break;
                    }
                    String p1 = String.valueOf(s.charAt(i++));
                    if(p1.matches("=")) {
                        state = State.S5;
                        p = p+p1;
                    }else {
                        state = State.S12;
                    }
                }else if(state == State.S5) {
                    state = State.S13;
                    Token t = new Token(line, TokenType.ASSIGN, "分界符");
                    tokens.add(t);
                }else if(state == State.S6) {
                    state = State.S13;
                    Token t = new Token(line, TokenType.ANNOTATIONHEAD, "分界符");
                    tokens.add(t);
                }else if(state == State.S7) {
                    if(i+1>length) {
                        break;
                    }
                    String p1 = String.valueOf(s.charAt(i++));
                    if(p1.matches(".")) {
                        state = State.S8;
                    }else {
                        state = State.S13;
                    }
                }else if(state == State.S8) {
                    state = State.S13;
                    Token t = new Token(line, TokenType.UNDERANGE, "数组下标符");
                    tokens.add(t);
                }else if(state == State.S9) {
                    if(i+1>length) {
                        break;
                    }
                    String p1 = String.valueOf(s.charAt(i++));
                    if(p1.matches(pattern.getLetterPattern())||
                            p1.matches(pattern.getNumberPattern())) {
                        state = State.S10;
                        p = p+p1;
                    }else {
                        state = State.S12;
                    }
                }else if(state == State.S10) {
                    if(i+1>length) {
                        break;
                    }
                    String p1 = String.valueOf(s.charAt(i++));
                    if(p1.matches("'")) {
                        state = State.S11;
                    }else {
                        state = State.S12;
                    }
                }else if(state == State.S11) {
                    state = State.S13;
                    Token t = new Token(line, TokenType.CHARP,p);
                    tokens.add(t);
                }else if(state == State.S12) {				//错误状态
                    state = State.S13;
                }else {
                    state = State.S13;
                }

            }
//			if(!p.matches(pattern.getBlankPattern())) {		//非空格则输出
//				Token t = new Token(line, TokenType.ID, p);
//				t.changeToKeyWord();
//				tokens.add(t);
//			}
        }
        return tokens;
    }

}