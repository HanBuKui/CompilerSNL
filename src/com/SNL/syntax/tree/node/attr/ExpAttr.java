package com.SNL.syntax.tree.node.attr;

import com.SNL.lex.Entity.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ExpAttr
 * @Description: 表达式的属性
 * @author: 涵不亏
 * @date: 2021/6/10  21:23
 */
@Data
@AllArgsConstructor
public class ExpAttr {
    //记录语法树节点的运算符单词
    //语法树节点为“关系运算表达式”对应节点时 取值LT,EQ
    //语法树节点为“加法运算简单表达式” 取值 PLUS,MINUS；
    //当语法树节点为“乘法运算项”对应节点时，取值 TIMES,OVER；其
    //它情况下无效。
    private TokenType op;

    //记录 语法树节点的   数值,
    // 当语法树节点为“数字因子”对应的语法树节点时有效,
    private int val;

    /*
    记录变量的类别；值为枚举变量
IdV,ArrayMembV 或 FieldMembV；分别表示变量
是标志符变量，数组成员变量还是域成员变量；
     */
    private String varkind;

    /*
    记录语法树节点的检查类型，取值 Void,
    Integer,Boolean,为类型检查 ExpType 类型。
    */
    private String type;

    public ExpAttr(){
        op=TokenType.END;
        val=0;
        varkind="";
        type="";
    }

}
