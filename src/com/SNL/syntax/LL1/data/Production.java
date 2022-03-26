package com.SNL.syntax.LL1.data;


import com.SNL.lex.Entity.TokenType;
import lombok.Data;

/**
 * @ClassName: Production
 * @Description: 产生式的数据结构
 * @author: 涵不亏
 * @date: 2021/6/5  12:00
 */
@Data
public class Production {
    TokenType Head ;
    int productNum ;
    class product {
        public int flag ;                 // 0表示终极符，1表示非终极符
        public TokenType nonterminals ;
        public TokenType      terminals    ;
    }

    product[] Product = new product[10] ;

    public Production(){
        productNum = 0 ;
    }
    public void setHead( TokenType head ) {
        Head = head ;
    }
    public void setProduction( TokenType nonterminal ){
        Product[productNum] = new product() ;
        Product[productNum].flag = 1 ;
        Product[productNum].nonterminals = nonterminal ;
        productNum ++ ;
    }
    public TokenType getHead(){
        return Head ;
    }
    public int getproductNum() {
        return productNum ;
    }
    public int getflag( int number ) {
        if( Product[number].flag == 1 ) return 1 ;
        else return 0 ;
    }
    public TokenType getProductNonterminal( int number ) {
        return Product[number].nonterminals ;
    }
    public TokenType getProductTerminal( int number ) {
        return Product[number].terminals ;
    }
}
