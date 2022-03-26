package com.SNL.syntax.symbol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: 非终极符
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/1  10:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nsymbol implements Symbol{
    //非终极符的值
    private String value;



}
