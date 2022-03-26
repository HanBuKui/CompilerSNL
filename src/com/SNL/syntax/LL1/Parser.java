package com.SNL.syntax.LL1;


import com.SNL.lex.Entity.Token;
import com.SNL.lex.Entity.TokenType;
import com.SNL.lex.Funtion.ReadFile;
import com.SNL.syntax.LL1.data.SNLpredict;
import com.SNL.syntax.tree.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Parser
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/5  12:04
 */
public class Parser {
    LL1Utils utils = new LL1Utils();
    static List<String> Product;
    static SNLpredict Predict = new SNLpredict() ;  //predict集
    static ReadFile read = new ReadFile();
    static ArrayList<Token> TokenList = new ArrayList<Token> () ;    //Token集合
    static int index ;   //当前token的index

    public Parser() {
        Product = utils.getLL1NSet();
        index = 0 ;
    }

    /**
     * 递归地匹配一个非终极符
     * @param NonTerminal
     * @param father
     * @return
     */
    public static Node match(TokenType NonTerminal , Node father ) {

        int  i , j , choose = -1 ;
        Node root = new Node() ;
        TokenType temp ;
        TokenType curLex = TokenList.get(index).getTokenType() ;   //获得当前的token序列

        root.setFlag( 1 ) ;
        root.setNonTerminal( NonTerminal ) ;
        root.setFather( father ) ;

        //查找predict集合
        for( i = 1 ; i <= 104 ; i ++ ) {
            int flag = 0 ;
            temp = TokenType.EOF;
            //查看该产生式的predict集合
            for( j = 0 ; j < Predict.predict[i].getPredictNum() ; j ++ ) {
                //当前的token在第i个产生式的predict集里
                if( curLex == Predict.predict[i].getPredict( j ) ) {
                    flag = 1 ;
                    break ;
                }
            }
            if(  flag == 1 && temp.equals("NonTerminal") ) {
                choose = i ;    //选择第i个式子
                break ;
            }
        }

        //没找到
        if( choose == -1 ) {
            return null ;
        }
        else {
            for( i = 0 ; i < Product.size() ; i ++ ) {
                //压入的是终极符
                if( Product.get( i ) == null ) {
                    Node leaf = new Node() ;
                    leaf.setFather(father) ;
                    leaf.setFlag( 0 ) ;    //终极符为叶子节点
                    leaf.setData( TokenList.get( index ).getValue() ) ;
                    index ++ ;
                }
                else {
                    Node child ;
                    child = match(TokenList.get(i).getTokenType() , root ) ;
                    root.setChild( child ) ;
                }
            }
        }

        return root ;
    }

}
