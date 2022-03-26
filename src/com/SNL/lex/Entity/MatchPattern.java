package com.SNL.lex.Entity;

/**
 * @ClassName: MatchPattern
 * @Description: TODO
 * @author: qby
 * @date: 2021/6/9  17:10
 */
public class MatchPattern {

    private String numberPattern = "[0-9]";
    private String letterPattern = "[a-zA-Z]";
    private String singlePattern = "[/+-/*///(/);=<>]";				//使用/来取消转义，减无转义
    private String blankPattern = " ";

    public MatchPattern() {
        super();
    }

    public String getNumberPattern() {
        return numberPattern;
    }

    public String getLetterPattern() {
        return letterPattern;
    }

    public String getSinglePattern() {
        return singlePattern;
    }

    public String getBlankPattern() {
        return blankPattern;
    }

}
