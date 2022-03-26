package com.SNL.lex.Entity;

/**
 * @ClassName: TokenType
 * @Description: TODO
 * @author: qby
 * @date: 2021/6/9  17:10
 */
public enum TokenType {


    //错误
    ERROR("error"),

    //标识符
    ID("id"),

    //保留字（标注符的子集）
    PROGRAM("program"),PROCEDURE("procedure"),TYPE("type"),VAR("var"), IF("if"),
    THEN("then"), ELSE("else"), FI("fi"), WHILE("while"), DO("do"),
    ENDWH("endwh"), BEGIN("begin"), END("end"), READ("read"), WRITE("write"),
    ARRAY("array"), OF("of"), RECORD("record"), RETURN("return"),
    INTEGER("integer"),CHAR("char"),

    //无符号整数
    INTC("intc"),CHARP("charp"),

    //单字符分界符
    SINGLE("single"),
    EQ("="),LT("<"),RT(">"),EOF("."),EMPTY(""),PLUS("+"),MINUS("-"),TIMES("*"),DIVIDE("/"),
    LPAREN("("),RPAREN(")"),LMIDPAREN("["), RMIDPAREN("]"),SEMI(";"),COMMA(","),DOT("."),

    //双字符分界符
    ASSIGN(":="),

    //注释头符和注释结束符
    ANNOTATIONHEAD("{"),ANNOTATIONEND("}"),

    //字符起始结束符
    CHARC("'"),

    //数组下界
    UNDERANGE("..");

    //成员变量
    private String name;

    //构造函数
    private TokenType(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
}
