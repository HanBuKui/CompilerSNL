package com.SNL.syntax.tree.node.attr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ProcAttr
 * @Description: 过程的属性
 * @author: 涵不亏
 * @date: 2021/6/10  21:19
 */
@Data
@AllArgsConstructor
public class ProcAttr {
    //记录过程的参数类型，
    //valparamtype 或者 varparamtype
    //表示过程的参数值参    还是变参。
    private String paramt;

    public ProcAttr(){
        paramt = "";
    }

}
