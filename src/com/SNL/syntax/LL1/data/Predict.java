package com.SNL.syntax.LL1.data;

import com.SNL.lex.Entity.TokenType;

/**
 * @ClassName: predict
 * @Description: predict集的数据结构
 * @author: 涵不亏
 * @date: 2021/6/5  11:58
 */

public class Predict {
    int predictNum ;   //predict集的数量
    TokenType[] predict = new TokenType[20] ;

    public Predict() {
        predictNum = 0 ;
    }

    public void setPredict( TokenType pre ) {
        predict[predictNum] = pre ;
        predictNum ++ ;
    }

    public int getPredictNum() {
        return predictNum ;
    }

    public TokenType getPredict( int number ) {
        return predict[number] ;
    }

}
