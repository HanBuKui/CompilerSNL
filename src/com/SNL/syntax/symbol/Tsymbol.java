package com.SNL.syntax.symbol;

import com.SNL.lex.Entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: 终极符
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/1  10:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tsymbol implements Symbol{
    //终极符所对应的token
    private Token token;
}
