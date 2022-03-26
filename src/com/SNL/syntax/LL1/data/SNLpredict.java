package com.SNL.syntax.LL1.data;

import com.SNL.lex.Entity.TokenType;

/**
 * @ClassName: SNLPredict
 * @Description: SNL的predict集
 * @author: 涵不亏
 * @date: 2021/6/5  11:59
 */
public class SNLpredict {
    public Predict[] predict = new Predict[105] ;
    public SNLpredict() {
        for( int i = 0 ; i <= 104 ; i ++ )
            predict[i] = new Predict() ;
        predict[1].setPredict( TokenType.PROGRAM ) ;

        predict[2].setPredict( TokenType.PROGRAM ) ;

        predict[3].setPredict( TokenType.ID ) ;

        predict[4].setPredict( TokenType.TYPE ) ; 			predict[4].setPredict( TokenType.VAR ) ;
        predict[4].setPredict( TokenType.PROCEDURE ) ;		predict[4].setPredict( TokenType.BEGIN ) ;

        predict[5].setPredict( TokenType.VAR ) ;				predict[5].setPredict( TokenType.PROCEDURE ) ;
        predict[5].setPredict( TokenType.BEGIN ) ;

        predict[6].setPredict( TokenType.TYPE ) ;

        predict[7].setPredict( TokenType.TYPE ) ;

        predict[8].setPredict( TokenType.ID ) ;

        predict[9].setPredict( TokenType.VAR ) ;				predict[9].setPredict( TokenType.PROCEDURE ) ;
        predict[9].setPredict( TokenType.BEGIN ) ;

        predict[10].setPredict( TokenType.ID ) ;

        predict[11].setPredict( TokenType.ID ) ;

        predict[12].setPredict( TokenType.INTEGER ) ;		predict[12].setPredict( TokenType.CHAR ) ;

        predict[13].setPredict( TokenType.ARRAY ) ;			predict[13].setPredict( TokenType.RECORD ) ;

        predict[14].setPredict( TokenType.ID ) ;

        predict[15].setPredict( TokenType.INTEGER ) ;

        predict[16].setPredict( TokenType.CHAR ) ;

        predict[17].setPredict( TokenType.ARRAY ) ;

        predict[18].setPredict( TokenType.RECORD ) ;

        predict[19].setPredict( TokenType.ARRAY ) ;

        predict[20].setPredict( TokenType.INTC ) ;

        predict[21].setPredict( TokenType.INTC ) ;

        predict[22].setPredict( TokenType.RECORD ) ;

        predict[23].setPredict( TokenType.INTEGER ) ; 		predict[23].setPredict( TokenType.CHAR ) ;

        predict[24].setPredict( TokenType.ARRAY ) ;

        predict[25].setPredict( TokenType.END ) ;

        predict[26].setPredict( TokenType.INTEGER ) ;		predict[26].setPredict( TokenType.CHAR ) ;
        predict[26].setPredict( TokenType.ARRAY ) ;

        predict[27].setPredict( TokenType.ID ) ;

        predict[28].setPredict( TokenType.SEMI ) ;

        predict[29].setPredict( TokenType.COMMA ) ;

        predict[30].setPredict( TokenType.PROCEDURE ) ; 		predict[30].setPredict( TokenType.BEGIN ) ;

        predict[31].setPredict( TokenType.VAR ) ;

        predict[32].setPredict( TokenType.VAR ) ;

        predict[33].setPredict( TokenType.INTEGER ) ;		predict[33].setPredict( TokenType.CHAR ) ;
        predict[33].setPredict( TokenType.ARRAY ) ;			predict[33].setPredict( TokenType.RECORD ) ;
        predict[33].setPredict( TokenType.ID ) ;

        predict[34].setPredict( TokenType.PROCEDURE ) ;		predict[34].setPredict( TokenType.BEGIN ) ;

        predict[35].setPredict( TokenType.INTEGER ) ;		predict[35].setPredict( TokenType.CHAR ) ;
        predict[35].setPredict( TokenType.ARRAY ) ;			predict[35].setPredict( TokenType.RECORD ) ;
        predict[35].setPredict( TokenType.ID ) ;

        predict[36].setPredict( TokenType.ID ) ;

        predict[37].setPredict( TokenType.SEMI ) ;

        predict[38].setPredict( TokenType.COMMA ) ;

        predict[39].setPredict( TokenType.BEGIN ) ;

        predict[40].setPredict( TokenType.PROCEDURE ) ;

        predict[41].setPredict( TokenType.PROCEDURE ) ;

        predict[42].setPredict( TokenType.BEGIN ) ;

        predict[43].setPredict( TokenType.PROCEDURE ) ;

        predict[44].setPredict( TokenType.ID ) ;

        predict[45].setPredict( TokenType.RPAREN ) ;

        predict[46].setPredict( TokenType.INTEGER ) ;		predict[46].setPredict( TokenType.CHAR ) ;
        predict[46].setPredict( TokenType.ARRAY ) ;			predict[46].setPredict( TokenType.RECORD ) ;
        predict[46].setPredict( TokenType.ID	) ;				predict[46].setPredict( TokenType.VAR ) ;

        predict[47].setPredict( TokenType.INTEGER ) ;		predict[47].setPredict( TokenType.CHAR ) ;
        predict[47].setPredict( TokenType.ARRAY ) ;			predict[47].setPredict( TokenType.RECORD ) ;
        predict[47].setPredict( TokenType.ID	) ;				predict[47].setPredict( TokenType.VAR ) ;

        predict[48].setPredict( TokenType.RPAREN ) ;

        predict[49].setPredict( TokenType.SEMI ) ;

        predict[50].setPredict( TokenType.INTEGER ) ;		predict[50].setPredict( TokenType.CHAR ) ;
        predict[50].setPredict( TokenType.ARRAY ) ;			predict[50].setPredict( TokenType.RECORD ) ;
        predict[50].setPredict( TokenType.ID ) ;

        predict[51].setPredict( TokenType.VAR ) ;

        predict[52].setPredict( TokenType.ID ) ;

        predict[53].setPredict( TokenType.SEMI ) ;			predict[53].setPredict( TokenType.RPAREN ) ;

        predict[54].setPredict( TokenType.COMMA ) ;

        predict[55].setPredict( TokenType.TYPE ) ;			predict[55].setPredict( TokenType.VAR ) ;
        predict[55].setPredict( TokenType.PROCEDURE ) ;		predict[55].setPredict( TokenType.BEGIN ) ;

        predict[56].setPredict( TokenType.BEGIN ) ;

        predict[57].setPredict( TokenType.BEGIN ) ;

        predict[58].setPredict( TokenType.ID ) ;				predict[58].setPredict( TokenType.IF ) ;
        predict[58].setPredict( TokenType.WHILE ) ;			predict[58].setPredict( TokenType.RETURN ) ;
        predict[58].setPredict( TokenType.READ ) ;			predict[58].setPredict( TokenType.WRITE ) ;
        predict[58].setPredict( TokenType.END ) ;			predict[58].setPredict( TokenType.SEMI ) ;

        predict[59].setPredict( TokenType.ELSE ) ;			predict[59].setPredict( TokenType.FI ) ;
        predict[59].setPredict( TokenType.END ) ;			predict[59].setPredict( TokenType.ENDWH ) ;

        predict[60].setPredict( TokenType.SEMI ) ;

        predict[61].setPredict( TokenType.IF ) ;

        predict[62].setPredict( TokenType.WHILE ) ;

        predict[63].setPredict( TokenType.READ ) ;

        predict[64].setPredict( TokenType.WRITE ) ;

        predict[65].setPredict( TokenType.RETURN ) ;

        predict[66].setPredict( TokenType.ID ) ;

        predict[67].setPredict( TokenType.ASSIGN ) ;			predict[67].setPredict( TokenType.DOT ) ;
        predict[67].setPredict( TokenType.LMIDPAREN ) ;

        predict[68].setPredict( TokenType.LPAREN ) ;

        predict[69].setPredict( TokenType.LMIDPAREN ) ;		predict[69].setPredict( TokenType.DOT ) ;
        predict[69].setPredict( TokenType.ASSIGN ) ;

        predict[70].setPredict( TokenType.IF ) ;

        predict[71].setPredict( TokenType.WHILE ) ;

        predict[72].setPredict( TokenType.READ ) ;

        predict[73].setPredict( TokenType.ID ) ;

        predict[74].setPredict( TokenType.WRITE ) ;

        predict[75].setPredict( TokenType.RETURN ) ;

        predict[76].setPredict( TokenType.LPAREN ) ;

        predict[77].setPredict( TokenType.RPAREN ) ;

        predict[78].setPredict( TokenType.LPAREN ) ;			predict[78].setPredict( TokenType.INTC ) ;
        predict[78].setPredict( TokenType.ID ) ;

        predict[79].setPredict( TokenType.RPAREN ) ;

        predict[80].setPredict( TokenType.COMMA ) ;

        predict[81].setPredict( TokenType.LPAREN ) ;			predict[81].setPredict( TokenType.INTC ) ;
        predict[81].setPredict( TokenType.ID ) ;

        predict[82].setPredict( TokenType.LT ) ;				predict[82].setPredict( TokenType.EQ ) ;

        predict[83].setPredict( TokenType.LPAREN ) ;			predict[83].setPredict( TokenType.INTC ) ;
        predict[83].setPredict( TokenType.ID ) ;

        predict[84].setPredict( TokenType.LT ) ;				predict[84].setPredict( TokenType.EQ ) ;
        predict[84].setPredict( TokenType.RMIDPAREN ) ;		predict[84].setPredict( TokenType.THEN ) ;
        predict[84].setPredict( TokenType.ELSE ) ;			predict[84].setPredict( TokenType.FI ) ;
        predict[84].setPredict( TokenType.DO ) ;				predict[84].setPredict( TokenType.ENDWH ) ;
        predict[84].setPredict( TokenType.RPAREN ) ;			predict[84].setPredict( TokenType.END ) ;
        predict[84].setPredict( TokenType.SEMI ) ;			predict[84].setPredict( TokenType.COMMA ) ;

        predict[85].setPredict( TokenType.PLUS ) ;			predict[85].setPredict( TokenType.MINUS ) ;

        predict[86].setPredict( TokenType.LPAREN ) ;			predict[86].setPredict( TokenType.INTC ) ;
        predict[86].setPredict( TokenType.ID ) ;

        predict[87].setPredict( TokenType.PLUS ) ;			predict[87].setPredict( TokenType.MINUS ) ;
        predict[87].setPredict( TokenType.LT ) ;				predict[87].setPredict( TokenType.EQ ) ;
        predict[87].setPredict( TokenType.RMIDPAREN ) ;		predict[87].setPredict( TokenType.THEN ) ;
        predict[87].setPredict( TokenType.ELSE ) ;			predict[87].setPredict( TokenType.FI ) ;
        predict[87].setPredict( TokenType.DO ) ;				predict[87].setPredict( TokenType.ENDWH ) ;
        predict[87].setPredict( TokenType.RPAREN ) ;			predict[87].setPredict( TokenType.END ) ;

        predict[88].setPredict( TokenType.SEMI ) ;			predict[87].setPredict( TokenType.COMMA ) ;


        predict[89].setPredict( TokenType.LPAREN ) ;

        predict[90].setPredict( TokenType.INTC ) ;

        predict[91].setPredict( TokenType.ID ) ;

        predict[92].setPredict( TokenType.ID ) ;

        predict[93].setPredict( TokenType.ASSIGN ) ;			predict[93].setPredict( TokenType.TIMES ) ;
        predict[93].setPredict( TokenType.MINUS ) ;			predict[93].setPredict( TokenType.LT ) ;
        predict[93].setPredict( TokenType.EQ ) ;				predict[93].setPredict( TokenType.THEN ) ;
        predict[93].setPredict( TokenType.ELSE ) ;			predict[93].setPredict( TokenType.FI ) ;
        predict[93].setPredict( TokenType.DO ) ;				predict[93].setPredict( TokenType.ENDWH ) ;
        predict[93].setPredict( TokenType.RPAREN ) ;			predict[93].setPredict( TokenType.END ) ;
        predict[93].setPredict( TokenType.SEMI ) ;			predict[93].setPredict( TokenType.COMMA ) ;
        predict[93].setPredict( TokenType.RMIDPAREN ) ;

        predict[94].setPredict( TokenType.LMIDPAREN ) ;

        predict[95].setPredict( TokenType.DOT ) ;

        predict[96].setPredict( TokenType.ID ) ;

        predict[97].setPredict( TokenType.ASSIGN ) ;			predict[97].setPredict( TokenType.TIMES ) ;
        predict[97].setPredict( TokenType.END ) ;			predict[97].setPredict( TokenType.PLUS ) ;
        predict[97].setPredict( TokenType.MINUS ) ;			predict[97].setPredict( TokenType.LT ) ;
        predict[97].setPredict( TokenType.EQ ) ;				predict[97].setPredict( TokenType.THEN ) ;
        predict[97].setPredict( TokenType.ELSE ) ;			predict[97].setPredict( TokenType.FI ) ;
        predict[97].setPredict( TokenType.DO ) ;				predict[97].setPredict( TokenType.ENDWH ) ;
        predict[97].setPredict( TokenType.RPAREN ) ;			predict[97].setPredict( TokenType.END ) ;
        predict[97].setPredict( TokenType.SEMI ) ;			predict[97].setPredict( TokenType.COMMA ) ;
        predict[97].setPredict( TokenType.RMIDPAREN ) ;

        predict[98].setPredict( TokenType.LMIDPAREN ) ;

        predict[99].setPredict( TokenType.LT ) ;

        predict[100].setPredict( TokenType.EQ ) ;

        predict[101].setPredict( TokenType.PLUS ) ;

        predict[102].setPredict( TokenType.MINUS ) ;

        predict[103].setPredict( TokenType.TIMES ) ;

        predict[104].setPredict( TokenType.END ) ;
    }
}
