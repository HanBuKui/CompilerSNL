package com.SNL.lex.Entity;

/**
 * @ClassName: State
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/9  17:10
 */
public enum State {

    //S0为开始，S1为标识符状态，S2为数字状态，S3为单分界符状态,S4为赋值前导状态，S5为赋值状态
    //S6为注释状态，S7为数组下标前导状态，S8为数组下标状态
    //S9为字符标识状态，S10为完成状态，S11为字符状态，S12为错误状态，S13为结束状态
    S0("Start"),S1("INID"),S2("INNUM"),S3("SINGLE"),S4("PREASSIGN"),S5("INASSIGN"),
    S6("INCOMMENT"),S7("PRERANGE"),S8("INRANGE"),
    S9("INCHAR"),S10("DONE"),S11("CHAR"),S12("ERROR"),S13("END");

    private String name;

    private State(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

}
