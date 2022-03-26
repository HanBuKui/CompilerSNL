package com.SNL.lex.Entity;

import lombok.ToString;

/**
 * @ClassName: Token
 * @Description: TODO
 * @author: qby
 * @date: 2021/6/9  17:10
 */
@ToString
public class Token {

    //Token序列的组成成分为行，列，语义信息

    private int line;
    private TokenType tokenType;
    private String value;

    //Token的构造函数

    public Token() {
        super();
    }

    public Token(TokenType tokenType) {
        this.tokenType=tokenType;
        this.value=tokenType.getName();
        this.line=0;
    }

    public Token(int line,TokenType tokenType,String value) {
        this.line=line;
        this.tokenType=tokenType;
        this.value=value;
    }

    public int getLine() {
        return line;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
    }

    public boolean changeToKeyWord() {
        //当前为标识符，判断是否为关键字，是则改变为关键字
        if(tokenType==TokenType.ID) {
            if(value.equals(TokenType.PROGRAM.getName())) {
                tokenType=TokenType.PROGRAM;
                return true;
            }else if(value.equals(TokenType.PROCEDURE.getName())) {
                tokenType=TokenType.PROCEDURE;
                return true;
            }else if(value.equals(TokenType.TYPE.getName())) {
                tokenType=TokenType.TYPE;
                return true;
            }else if(value.equals(TokenType.VAR.getName())) {
                tokenType=TokenType.VAR;
                return true;
            }else if(value.equals(TokenType.IF.getName())) {
                tokenType=TokenType.IF;
                return true;
            }else if(value.equals(TokenType.THEN.getName())) {
                tokenType=TokenType.THEN;
                return true;
            }else if(value.equals(TokenType.ELSE.getName())) {
                tokenType=TokenType.ELSE;
                return true;
            }else if(value.equals(TokenType.FI.getName())) {
                tokenType=TokenType.FI;
                return true;
            }else if(value.equals(TokenType.WHILE.getName())) {
                tokenType=TokenType.WHILE;
                return true;
            }else if(value.equals(TokenType.DO.getName())) {
                tokenType=TokenType.DO;
                return true;
            }else if(value.equals(TokenType.ENDWH.getName())) {
                tokenType=TokenType.ENDWH;
                return true;
            }else if(value.equals(TokenType.BEGIN.getName())) {
                tokenType=TokenType.BEGIN;
                return true;
            }else if(value.equals(TokenType.END.getName())) {
                tokenType=TokenType.END;
                return true;
            }else if(value.equals(TokenType.READ.getName())) {
                tokenType=TokenType.READ;
                return true;
            }else if(value.equals(TokenType.WRITE.getName())) {
                tokenType=TokenType.WRITE;
                return true;
            }else if(value.equals(TokenType.ARRAY.getName())) {
                tokenType=TokenType.ARRAY;
                return true;
            }else if(value.equals(TokenType.OF.getName())) {
                tokenType=TokenType.OF;
                return true;
            }else if(value.equals(TokenType.RECORD.getName())) {
                tokenType=TokenType.RECORD;
                return true;
            }else if(value.equals(TokenType.RETURN.getName())) {
                tokenType=TokenType.RETURN;
                return true;
            }else if(value.equals(TokenType.INTEGER.getName())) {
                tokenType=TokenType.INTEGER;
                return true;
            }else if(value.equals(TokenType.CHAR.getName())) {
                tokenType=TokenType.CHAR;
                return true;
            }
        }
        return false;
    }

    //判断是是否为
    public boolean selectSingle(String p) {
        if(p.equals(TokenType.EQ.getName())) {
            tokenType=TokenType.EQ;
            return true;
        }else if(p.equals(TokenType.LT.getName())) {
            tokenType=TokenType.LT;
            return true;
        }else if(p.equals(TokenType.RT.getName())) {
            tokenType=TokenType.RT;
            return true;
        }else if(p.equals(TokenType.PLUS.getName())){
            tokenType=TokenType.PLUS;
            return true;
        }else if(p.equals(TokenType.MINUS.getName())) {
            tokenType=TokenType.MINUS;
            return true;
        }else if(p.equals(TokenType.TIMES.getName())) {
            tokenType=TokenType.TIMES;
            return true;
        }else if(p.equals(TokenType.DIVIDE.getName())) {
            tokenType=TokenType.DIVIDE;
            return true;
        }else if(p.equals(TokenType.LPAREN.getName())) {
            tokenType=TokenType.LPAREN;
            return true;
        }else if(p.equals(TokenType.RPAREN.getName())) {
            tokenType=TokenType.RPAREN;
            return true;
        }else if(p.equals(TokenType.LMIDPAREN.getName())) {
            tokenType=TokenType.LMIDPAREN;
            return true;
        }else if(p.equals(TokenType.RMIDPAREN.getName())) {
            tokenType=TokenType.RMIDPAREN;
            return true;
        }else if(p.equals(TokenType.SEMI.getName())) {
            tokenType=TokenType.SEMI;
            return true;
        }else if(p.equals(TokenType.COMMA.getName())) {
            tokenType=TokenType.COMMA;
            return true;
        }else if(p.equals(TokenType.DOT.getName())) {
            tokenType=TokenType.DOT;
            return true;
        }
        return false;
    }
}
