package com.SNL.syntax.tree.node.attr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ArrayAttr
 * @Description: 数组类型的属性
 * @author: 涵不亏
 * @date: 2021/6/10  21:18
 */
@Data
@AllArgsConstructor
public class ArrayAttr {
    private int low;  //数组下界
    private int up;   //数组上界
    private String childType;  //数组的成员类型

    public ArrayAttr(){
        low = 0;
        up = 1;
        childType = "";
    }

}
