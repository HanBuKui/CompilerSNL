package com.SNL.syntax.tree.node;

import com.SNL.syntax.tree.node.attr.ArrayAttr;
import com.SNL.syntax.tree.node.attr.ExpAttr;
import com.SNL.syntax.tree.node.attr.ProcAttr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @ClassName: Attr
 * @Description: 记录语法树节点其他属性,
 * @author: 涵不亏
 * @date: 2021/6/10  21:15
 */
@Data
@AllArgsConstructor
public class Attr {
    private ArrayAttr arrayAttr;
    private ProcAttr procAttr;
    private ExpAttr expAttr;

    public Attr(){
        arrayAttr = new ArrayAttr();
        procAttr = new ProcAttr();
        expAttr = new ExpAttr();
    }



}
