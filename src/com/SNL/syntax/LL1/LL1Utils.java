package com.SNL.syntax.LL1;

import com.SNL.lex.Entity.Token;
import com.SNL.lex.Entity.TokenType;
import com.SNL.lex.Funtion.ReadFile;
import com.SNL.syntax.symbol.Nsymbol;
import com.SNL.syntax.symbol.Symbol;
import com.SNL.syntax.symbol.Tsymbol;
import com.SNL.syntax.tree.SyntaxTree;
import com.SNL.syntax.tree.node.Node;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.NO_IMPLEMENT;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName: lexUtils
 * @Description: 所需的词法分析, 以及语法分析中需要用到的函数
 * @author: 涵不亏
 * @date: 2021/6/9  17:57
 */

@Data
@NoArgsConstructor
public class LL1Utils {

    private List<Token> tokenList;    //token序列
    private int tokenIndex = 0;  //获取的token位置

    Stack<Symbol> symbolStack = new Stack<>();   //符号栈

    Node root = new Node(); //语法树根节点
    Stack<Node> treeStack = new Stack<>();       //语法树栈
    Stack<Node> opcStack = new Stack<>();      //操作符栈
    Stack<Node> opdStack = new Stack<>();      //操作数栈


    //LL1表
    List<String> LL1NSet = null;
    List<ArrayList<String>> LL1predictSet = null;

    int expflag = 0;
    boolean getExpResult = false;
    boolean getExpResult2 = false;

    Node currentP = null;
    Node Phead = null;

    /**
     * LL1开始前的初始化操作
     * 生成LL1
     *
     */
    public void parseLL() throws Exception {

        creatLL1Table();   //生成LL1
        symbolStack.push(new Nsymbol("Program"));   //初始化符号栈

        //创建语法树根节点
        root.init();
        root.setNodekind("ProK");
        root.getChild0().init();
        root.getChild1().init();
        root.getChild2().init();

        treeStack.push(root.getChild2()); //指向  程序体     的指针 child[2]的地址
        treeStack.push(root.getChild1()); //指向  声明部分    的指针 child[1]的地址
        treeStack.push(root.getChild0()); //指向   程序头    的儿子指针 child[0]的地
    }


    /**
     * LL1开始执行
     *
     * @param  file SNL源文件的路径
     * @throws Exception
     */
    public void start(String file) throws Exception {
        parseLL();   //初始化
        getTokenList(file);    //获得SNL程序的Token序列

        Symbol symbol = null;
        //符号栈不空则一直循环
        while (!symbolStack.isEmpty()) {
            symbol = symbolStack.pop();

            Token nowToken = getNowToken();   //获取当前Token

            //栈不空，流空的Error情况 结束程序，输出错误程序
            if (nowToken == null) {
                //栈还没空，但是token空了
                System.out.println("程序语法有误，请更正后重新运行！");
                return;
            }

            //栈顶为终极符
            if (symbol instanceof Tsymbol) {
                //判断是否与现在的token匹配
                Node node = match(((Tsymbol) symbol).getToken().getTokenType());
                if (node != null) {  //匹配
                    System.out.println("终极符"+((Tsymbol) symbol).getToken().getTokenType()+"匹配成功！");
                    next();   //下一个Token
                    //???????//加入语法树
                } else {
                    //匹配失败，打印错误信息
                    showError();
                    System.out.println("114错了...");
                    return;
                }
            } else if (symbol instanceof Nsymbol) {
                //栈顶为非终极符
                System.out.println("Nsymbol is "+symbol);
                System.out.println("nowToken is:"+nowToken);

                int index = findLL1((Nsymbol) symbol, nowToken.getTokenType().toString());
                System.out.println("index is "+index);

                if (index == 0) {   //出错
                    showError();
                    System.out.println("121错了...");
                    return;
                } else {
                    predict(index);   //执行predict函数
                }
            }
        }

        //此时栈空了，判断token完了么
        if (getNowToken() == null) {
            //空了
            System.out.println("程序语法正确，输出语法树。。。。");
            root.showTree(root,0);
        } else {
            System.out.println("程序语法有误，请更正后重新运!！");
            return;
        }
    }


    /**
     * 生成LL1表
     * @throws IOException
     */
    public void creatLL1Table() throws IOException {

        String file = "src/resource/LL1.txt";
        BufferedReader r = new BufferedReader(new FileReader(file));

        List<String> VNT = new ArrayList<String>();
        List<ArrayList<String>> predictAss = new ArrayList<ArrayList<String>>();

        String S = null;
        int lines = 1;
        while ((S = r.readLine()) != null) {
            String[] S1 = S.split(" ");

            ArrayList<String> temp = new ArrayList<String>();

            for (int i =0 ;i<S1.length;i++){
                String trim = S1[i].trim();
                if(i==0){
                    VNT.add(trim);
                }else {
                    temp.add(trim);
                }
            }

            predictAss.add(temp);
        }
        this.LL1NSet = VNT;
        this.LL1predictSet = predictAss;
        //---------------------------------输出LL1表(单元测试)-------------------------------------------------------
//		Iterator it = this.LL1NSet.iterator();
//		while(it.hasNext()) {
//			System.out.println(it.next());
//		}
//        System.out.println("-------------------------");
//		for(ArrayList<String> s : this.LL1predictSet) {
//			for(String p : s) {
//				System.out.print(p+" ");
//			}
//			System.out.println();
//		}

    }


    /**
     * 获取Token序列  ok
     * @param fileName
     */
    public void getTokenList(String fileName) {
        ReadFile read = new ReadFile();
        this.tokenList = read.read(fileName);
    }

    /**
     * 获取下一个Token  ok
     */
    public void next() {
        tokenIndex++;
    }

    /**
     * 获取当前Token 不弹走  ok
     *
     * @return
     */
    public Token getNowToken() {
        Token token = null;
        if (tokenIndex < tokenList.size()) {
            token = tokenList.get(tokenIndex);
        }
        return token;
    }

    /**
     * 终极符与Token匹配
     *
     * @param type 该 终极符 对应的Token 的type
     * @return  生成的树节点
     */
    public Node match(TokenType type) {
        Node node = null;
        //获取当前Token
        Token token = getNowToken();
        if (token.getTokenType()==type) {
            //匹配成功  生成语法树节点
            //node.setValue(token.getValue());
            node = new Node();

        }
        return node;
    }

    /**
     * 查询LL1表，获取predict集序列号
     *
     * @param symbol
     * @return 0：Error（即出现错误）   文法所对应的行号
     */
    public int findLL1(Nsymbol symbol, String predict) {

        System.out.println("findLL1..."+symbol.getValue()+"...."+predict);

        int ans = 0;

        String nsymbol = symbol.getValue();

        List<Integer> index1 = new ArrayList<Integer>();
        for (int i = 0; i < LL1NSet.size(); i++) {
            if (LL1NSet.get(i).equals(nsymbol)) {
                index1.add(i);
            }
        }
        System.out.print("list1 index:");
        for (int j:index1){
            System.out.print(j+" ");
        }
        System.out.println();

        for (int i = 0; i < index1.size(); i++) {
            ArrayList<String> strings = LL1predictSet.get(index1.get(i));

            if (strings.contains(predict)) {
                ans = index1.get(i)+1;
                break;
            }
        }

        return ans;
    }

    /**
     * id为0的情况下，终止程序，打印错误信息
     *
     * @return
     */
    public String showError() {
        String errorMes = "程序第" + (getNowToken().getLine()+1) + "行语法有误，请更正后重新运行！";
        System.out.println(errorMes);
        return errorMes;
    }


    /**
     * 对于给定的操作符，此函数返回操作符的优先级
     * 返回值越大所给操作符优先级越高
     * 乘法运算符 > 加法运算符 > 关系运算符 > 栈底标识 END
     *
     * @param op
     * @return
     */
    public int priosity(String op){
        int ans = 0;
        if(op.equals("end")){
            ans = 0;
        }else if(op.equals("<") || op.equals("=")){
            ans = 1;
        }else if(op.equals("+") || op.equals("-")){
            ans = 2;
        }else if(op.equals("*") || op.equals("/")){
            ans = 3;
        }else {
            ans = -1;
        }
        return ans;
    }
//

    /**
     * 根据产生式编号选择一个要执行的函数
     * @param num  产生式编号
     * @throws IOException
     */
    public void predict(int num) throws IOException {
        switch (num) {
            case 1:
                process1();
                break;
            case 2:
                process2();
                break;
            case 3:
                process3();
                break;
            case 4:
                process4();
                break;
            case 5:
                process5();
                break;
            case 6:
                process6();
                break;
            case 7:
                process7();
                break;
            case 8:
                process8();
                break;
            case 9:
                process9();
                break;
            case 10:
                process10();
                break;
            case 11:
                process11();
                break;
            case 12:
                process12();
                break;
            case 13:
                process13();
                break;
            case 14:
                process14();
                break;
            case 15:
                process15();
                break;
            case 16:
                process16();
                break;
            case 17:
                process17();
                break;
            case 18:
                process18();
                break;
            case 19:
                process19();
                break;
            case 20:
                process20();
                break;
            case 21:
                process21();
                break;
            case 22:
                process22();
                break;
            case 23:
                process23();
                break;
            case 24:
                process24();
                break;
            case 25:
                process25();
                break;
            case 26:
                process26();
                break;
            case 27:
                process27();
                break;
            case 28:
                process28();
                break;
            case 29:
                process29();
                break;
            case 30:
                process30();
                break;
            case 31:
                process31();
                break;
            case 32:
                process32();
                break;
            case 33:
                process33();
                break;
            case 34:
                process34();
                break;
            case 35:
                process35();
                break;
            case 36:
                process36();
                break;
            case 37:
                process37();
                break;
            case 38:
                process38();
                break;
            case 39:
                process39();
                break;
            case 40:
                process40();
                break;
            case 41:
                process41();
                break;
            case 42:
                process42();
                break;
            case 43:
                process43();
                break;
            case 44:
                process44();
                break;
            case 45:
                process45();
                break;
            case 46:
                process46();
                break;
            case 47:
                process47();
                break;
            case 48:
                process48();
                break;
            case 49:
                process49();
                break;
            case 50:
                process50();
                break;
            case 51:
                process51();
                break;
            case 52:
                process52();
                break;
            case 53:
                process53();
                break;
            case 54:
                process54();
                break;
            case 55:
                process55();
                break;
            case 56:
                process56();
                break;
            case 57:
                process57();
                break;
            case 58:
                process58();
                break;
            case 59:
                process59();
                break;
            case 60:
                process60();
                break;
            case 61:
                process61();
                break;
            case 62:
                process62();
                break;
            case 63:
                process63();
                break;
            case 64:
                process64();
                break;
            case 65:
                process65();
                break;
            case 66:
                process66();
                break;
            case 67:
                process67();
                break;
            case 68:
                process68();
                break;
            case 69:
                process69();
                break;
            case 70:
                process70();
                break;
            case 71:
                process71();
                break;
            case 72:
                process72();
                break;
            case 73:
                process73();
                break;
            case 74:
                process74();
                break;
            case 75:
                process75();
                break;
            case 76:
                process76();
                break;
            case 77:
                process77();
                break;
            case 78:
                process78();
                break;
            case 79:
                process79();
                break;
            case 80:
                process80();
                break;
            case 81:
                process81();
                break;
            case 82:
                process82();
                break;
            case 83:
                process83();
                break;
            case 84:
                process84();
                break;
            case 85:
                process85();
                break;
            case 86:
                process86();
                break;
            case 87:
                process87();
                break;
            case 88:
                process88();
                break;
            case 89:
                process89();
                break;
            case 90:
                process90();
                break;
            case 91:
                process91();
                break;
            case 92:
                process92();
                break;
            case 93:
                process93();
                break;
            case 94:
                process94();
                break;
            case 95:
                process95();
                break;
            case 96:
                process96();
                break;
            case 97:
                process97();
                break;
            case 98:
                process98();
                break;
            case 99:
                process99();
                break;
            case 100:
                process100();
                break;
            case 101:
                process101();
                break;
            case 102:
                process102();
                break;
            case 103:
                process103();
                break;
            case 104:
                process104();
                break;
        }

    }


    //****************************104个process函数********************************************
    public void process1() {
        symbolStack.push(new Tsymbol(new Token(TokenType.EOF)));
        symbolStack.push(new Nsymbol("ProgramBody"));
        symbolStack.push(new Nsymbol("DeclarePart"));
        symbolStack.push(new Nsymbol("ProgramHead"));
    }
    public void process2() {

        symbolStack.push(new Nsymbol("ProgramName"));
        symbolStack.push(new Tsymbol(new Token(TokenType.PROGRAM)));

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("PheadK");

        Node t = treeStack.pop();   //t是个指针(ProK的0号儿子节点)
        t.setAll(currentP);

        Phead = t;
    }
    public void process3() {

        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        Phead.setIdnum(currentP.getIdnum()+1);
        Phead.getName().add(getNowToken().getValue());
    }
    public void process4() {
        symbolStack.push(new Nsymbol("ProcDecpart"));
        symbolStack.push(new Nsymbol("VarDecpart"));
        symbolStack.push(new Nsymbol("TypeDecpart"));
    }
    public void process5() {

    }
    public void process6() {
        symbolStack.push(new Nsymbol("TypeDec"));
    }
    public void process7() {
        symbolStack.push(new Nsymbol("TypeDecList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.TYPE)));
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("TypeK");  //创建类型声明标识节点currentP

        Node t = treeStack.pop();   //Prok的1号儿子节点
        t.setAll(currentP);

//        Node siblings = t.getSiblings();
//        siblings.init();
        treeStack.push(t.getSiblings()); //将当前节点的兄弟节点和第一个儿子节点压入语法树栈
        Node child0 = t.getChild0();
        child0.init();
        treeStack.push(child0);
    }

    Node temp = new Node();   //主要存放node.kind
    //String temp = null;   //类型

    //具体的类型声明
    public void process8() {
        symbolStack.push(new Nsymbol("TypeDecMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
        symbolStack.push(new Nsymbol("TypeDef"));
        symbolStack.push(new Tsymbol(new Token(TokenType.EQ)));
        symbolStack.push(new Nsymbol("TypeId"));

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("DecK");
        Node t = treeStack.pop();   //类型声明的声明1
        t.setAll(currentP);

        temp = t;   //指向声明1节点
        treeStack.push(currentP.getSiblings());  //放入类型声明的声明2
    }
    //完成类型部分的语法树节点生成。
    public void process9() {
        treeStack.pop();
    }
    public void process10() {
        symbolStack.push(new Nsymbol("TypeDecList"));
    }
    public void process11() {
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }

    public void process12() {
        symbolStack.push(new Nsymbol("BaseType"));
//        temp.setAll(currentP);
    }
    public void process13() {
        symbolStack.push(new Nsymbol("StructureType"));
    }
    public void process14() {
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }
    public void process15() {
        symbolStack.push(new Tsymbol(new Token(TokenType.INTEGER)));
//        temp.setNodekind("DecK");
//        temp = "IntegerK";
        temp.setKind("IntegerK");
    }
    public void process16() {
        symbolStack.push(new Tsymbol(new Token(TokenType.CHAR)));
//        temp.setNodekind("DecK");
        temp.setKind("CharK");
    }
    public void process17() {
        symbolStack.push(new Nsymbol("ArrayType"));
    }
    public void process18() {
        symbolStack.push(new Nsymbol("RecType"));
    }
    public void process19() {
        symbolStack.push(new Nsymbol("BaseType"));
        symbolStack.push(new Tsymbol(new Token(TokenType.OF)));
//        symbolStack.push(new Tsymbol(new Token(TokenType.C)));
        symbolStack.push(new Tsymbol(new Token(TokenType.RMIDPAREN)));
        symbolStack.push(new Nsymbol("top"));
        symbolStack.push(new Tsymbol(new Token(TokenType.UNDERANGE)));
        symbolStack.push(new Nsymbol("low"));
        symbolStack.push(new Tsymbol(new Token(TokenType.LMIDPAREN)));
        symbolStack.push(new Tsymbol(new Token(TokenType.ARRAY)));
        //语法树
        currentP.setKind("ArrayK");
//        temp.setNodekind("DecK");
        temp.setKind(currentP.getAttr().getArrayAttr().getChildType());


    }
    public void process20() {
        symbolStack.push(new Tsymbol(new Token(TokenType.INTC)));
        //语法树
        //temp.getAttr().getArrayAttr().setLow(Integer.parseInt(getNowToken().getValue()));
        currentP.getAttr().getArrayAttr().setLow(Integer.parseInt(getNowToken().getValue()));
    }
    public void process21() {
        symbolStack.push(new Tsymbol(new Token(TokenType.INTC)));
        currentP.getAttr().getArrayAttr().setUp(Integer.parseInt(getNowToken().getValue()));
    }
    Node saveP= new Node();
    public void process22() {
        symbolStack.push(new Tsymbol(new Token(TokenType.END)));
        symbolStack.push(new Nsymbol("FieldDecList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.RECORD)));
        //语法树
        saveP = currentP;
        currentP.setKind(saveP.getKind());
        Node child0 = currentP.getChild0();
        child0.init();
        treeStack.push(child0);
    }
    //....
    public void process23() {
        symbolStack.push(new Nsymbol("FieldDecMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
        symbolStack.push(new Nsymbol("IdList"));
        symbolStack.push(new Nsymbol("BaseType"));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("DecK");
        currentP.setKind("RecordK");
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
    }

    public void process24() {
        symbolStack.push(new Nsymbol("FieldDecMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
        symbolStack.push(new Nsymbol("IdList"));
        symbolStack.push(new Nsymbol("ArrayType"));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("DecK");
        currentP.setKind("ArrayK");
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
    }

    public void process25() {
        //
        treeStack.pop();
        currentP = saveP;
    }

    //******

    public void process26() {
        symbolStack.push(new Nsymbol("FieldDecList"));
    }
    public void process27() {
        symbolStack.push(new Nsymbol("IdMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }
    public void process28() {
        //爽
    }
    public void process29() {
        symbolStack.push(new Nsymbol("IdList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.COMMA)));
    }
    public void process30() {

    }
    public void process31() {
        symbolStack.push(new Nsymbol("VarDec"));
    }

    //变量声明
    public void process32() {
        symbolStack.push(new Nsymbol("VarDecList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.VAR)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("VarK");

        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(t.getSiblings());
        Node child0 = t.getChild0();
        child0.init();
        treeStack.push(child0);
    }
    public void process33() {
        symbolStack.push(new Nsymbol("VarDecMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
        symbolStack.push(new Nsymbol("VarIdList"));
        symbolStack.push(new Nsymbol("TypeDef"));
        //
        currentP = new Node();
        currentP.init();
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
    }
    public void process34() {
        treeStack.pop();
    }
    //变量声明结束

    public void process35() {
        symbolStack.push(new Nsymbol("VarDecList"));
    }
    public void process36() {
        symbolStack.push(new Nsymbol("VarIdMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }
    public void process37() {
    }
    public void process38() {
        symbolStack.push(new Nsymbol("VarIdList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.COMMA)));
    }
    public void process39() {

    }
    public void process40() {
        symbolStack.push(new Nsymbol("ProcDec"));
    }


    //过程声明
    public void process41() {
        symbolStack.push(new Nsymbol("ProcDecMore"));
        symbolStack.push(new Nsymbol("ProcBody"));
        symbolStack.push(new Nsymbol("ProcDecPart"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
//        symbolStack.push(new Nsymbol("BaseType"));
        symbolStack.push(new Tsymbol(new Token(TokenType.RPAREN)));
        symbolStack.push(new Nsymbol("ParamList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.LPAREN)));
        symbolStack.push(new Nsymbol("ProcName"));
        symbolStack.push(new Tsymbol(new Token(TokenType.PROCEDURE)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ProcDecK");
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
        Node child0 = currentP.getChild0();
        Node child1 = currentP.getChild1();
        Node child2 = currentP.getChild2();
        child0.init();
        child1.init();
        child2.init();
        treeStack.push(child2);
        treeStack.push(child1);
        treeStack.push(child0);
    }

    public void process42() {
    }
    public void process43() {
        symbolStack.push(new Nsymbol("ProcDec"));
    }
    public void process44() {
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }
    public void process45() {
        treeStack.pop();
    }
    public void process46() {
        symbolStack.push(new Nsymbol("ParamDecList"));
    }
    public void process47() {
        symbolStack.push(new Nsymbol("ParamMore"));
        symbolStack.push(new Nsymbol("Param"));
    }

    Node saveFuncP = new Node();
    public void process48() {
        //
        Node pop = treeStack.pop();
        temp.setNodekind(currentP.getNodekind());
        temp.setKind(currentP.getKind());
        saveFuncP = currentP;
    }

    public void process49() {
        symbolStack.push(new Nsymbol("ParamDecList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.SEMI)));
    }

    public void process50() {
        symbolStack.push(new Nsymbol("FormList"));
        symbolStack.push(new Nsymbol("TypeDef"));
        //
        currentP = new Node();
        currentP.init();
        currentP.getAttr().getProcAttr().setParamt("valparamtype");
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
    }

    public void process51() {
        symbolStack.push(new Nsymbol("FormList"));
        symbolStack.push(new Nsymbol("TypeDef"));
        symbolStack.push(new Tsymbol(new Token(TokenType.VAR)));
        //
        currentP = new Node();
        currentP.init();
        currentP.getAttr().getProcAttr().setParamt("varparamtype");
        Node t = treeStack.pop();
        t.setAll(currentP);
        treeStack.push(currentP.getSiblings());
    }

    public void process52() {
        symbolStack.push(new Nsymbol("FidMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }

    public void process53() {
    }

    public void process54() {
        symbolStack.push(new Nsymbol("FormList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.COMMA)));
    }

    public void process55() {
        symbolStack.push(new Nsymbol("DeclarePart"));
    }

    public void process56() {
        symbolStack.push(new Nsymbol("ProgramBody"));
    }

    public void process57() {
        symbolStack.push(new Tsymbol(new Token(TokenType.END)));
        symbolStack.push(new Nsymbol("StmList"));
        symbolStack.push(new Tsymbol(new Token(TokenType.BEGIN)));
        //
        treeStack.pop();
        currentP = new Node();
        currentP.init();
        Node t = treeStack.pop();
        t.setAll(currentP);
        Node child0 = currentP.getChild0();
        child0.init();
        treeStack.push(child0);
    }

    public void process58() {
        symbolStack.push(new Nsymbol("StmMore"));
        symbolStack.push(new Nsymbol("Stm"));
    }

    public void process59() {
        //
        treeStack.pop();
    }

    public void process60(){
        Symbol s1 = new Nsymbol("StmList");
        Symbol s2 = new Tsymbol(new Token(TokenType.SEMI));
        symbolStack.push(s1);
        symbolStack.push(s2);
    }

    public void process61(){
        Symbol s1 = new Nsymbol("ConditionalStm");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("IfK");

        Node t = treeStack.pop();
        t.setAll(currentP);

        treeStack.push(currentP.getSiblings());
    }
    public void process62(){
        Symbol s1 = new Nsymbol("LoopStm");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("WhileK");

        Node t = treeStack.pop();
        t.setAll(currentP);

        treeStack.push(currentP.getSiblings());

    }
    public void process63(){
        Symbol s1 = new Nsymbol("InputStm");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("ReadK");

        Node t = treeStack.pop();
        t.setAll(currentP);

        treeStack.push(currentP.getSiblings());

    }
    public void process64(){
        Symbol s1 = new Nsymbol("OutputStm");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("WriteK");

        Node t = treeStack.pop();
        t.setAll(currentP);

        treeStack.push(currentP.getSiblings());

    }
    public void process65(){
        Symbol s1 = new Nsymbol("ReturnStm");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("ReturnK");

        Node t = treeStack.pop();
        t.setAll(currentP);

        treeStack.push(currentP.getSiblings());
    }

    Node t2 = new Node();
    public void process66(){
        Symbol s1 = new Nsymbol("AssCall");
        symbolStack.push(s1);
        Symbol s2 = new Tsymbol(new Token(TokenType.ID));
        symbolStack.push(s2);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        //由于赋值语句和过程调用语句都以标识符开始，
        //故现在不能确定此语句的具体类型

//        Node t = new Node();
        t2.init();
        t2.setNodekind("ExpK");
        t2.setKind("IdK");
        //变量名记入节点
        t2.getName().add(getNowToken().getTokenType().getName());
        t2.setIdnum(t2.getIdnum()+1);
        //CurrentP的第一个儿子节点指针赋值为t
        currentP.getChild0().setAll(t2);

        //弹语法树栈，得到t1 ,赋值（*t1）指向当前语句节点currentP
        Node t1 = treeStack.pop();
        t1.setAll(currentP);

        treeStack.push(currentP.getSiblings());
    }

    public void process67(){
        Symbol s1 = new Nsymbol("AssignmentRest");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("AssignK");
    }

    public void process68(){
        Symbol s1 = new Nsymbol("CallStmRest");
        symbolStack.push(s1);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("StmtK");
        currentP.setKind("CallK");
    }

    //特殊的 栈底标志
    public void process69(){
        Symbol s1 = new Nsymbol("Exp");
        Symbol s2 = new Tsymbol(new Token(TokenType.ASSIGN));
        Symbol s3 = new Nsymbol("VariMore");
        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);
        //
        Node child1 = currentP.getChild1();
        child1.init();
        treeStack.push(child1);
        //currentP = t2;
        currentP = currentP.getChild0();
        //
        Node az = new Node();
        az.init();
        az.getAttr().getExpAttr().setOp(TokenType.END);
        opcStack.push(az);
    }

    public void process70(){
        Symbol s1 = new Tsymbol(new Token(TokenType.FI));
        Symbol s2 = new Nsymbol("StmList");
        Symbol s3 = new Tsymbol(new Token(TokenType.ELSE));
        Symbol s4 = new Nsymbol("StmList");
        Symbol s5 = new Tsymbol(new Token(TokenType.THEN));
        Symbol s6 = new Nsymbol("RelExp");
        Symbol s7 = new Tsymbol(new Token(TokenType.IF));
        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);
        symbolStack.push(s4);
        symbolStack.push(s5);
        symbolStack.push(s6);
        symbolStack.push(s7);

        currentP.getChild0().init();
        currentP.getChild1().init();
        currentP.getChild2().init();
        treeStack.push(currentP.getChild2());
        treeStack.push(currentP.getChild1());
        treeStack.push(currentP.getChild0());
    }

    public void process71() {
        Symbol s1 = new Tsymbol(new Token(TokenType.ENDWH));
        Symbol s2 = new Nsymbol("StmList");
        Symbol s3 = new Tsymbol(new Token(TokenType.DO));
        Symbol s4 = new Nsymbol("RelExp");
        Symbol s5 = new Tsymbol(new Token(TokenType.WHILE));
        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);
        symbolStack.push(s4);
        symbolStack.push(s5);

        currentP.getChild0().init();
        currentP.getChild1().init();
        treeStack.push(currentP.getChild1());
        treeStack.push(currentP.getChild0());

    }
    public void process72() {
        Symbol s1 = new Tsymbol(new Token(TokenType.RPAREN));
        Symbol s2 = new Nsymbol("Invar");
        Symbol s3 = new Tsymbol(new Token(TokenType.LPAREN));
        Symbol s4 = new Tsymbol(new Token(TokenType.READ));

        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);
        symbolStack.push(s4);
    }
    public void process73() {
        Symbol s1 = new Tsymbol(new Token(TokenType.ID));
        symbolStack.push(s1);
        currentP.getName().add(getNowToken().getValue());
        currentP.setIdnum(currentP.getIdnum()+1);
    }

    public void process74() {
        Symbol s1 = new Tsymbol(new Token(TokenType.RPAREN));
        Symbol s2 = new Nsymbol("Exp");
        Symbol s3 = new Tsymbol(new Token(TokenType.LPAREN));
        Symbol s4 = new Tsymbol(new Token(TokenType.WRITE));
        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);
        symbolStack.push(s4);
        //当前写节点的儿子节点地址入语法树栈
        Node child0 = currentP.getChild0();
        child0.init();
        treeStack.push(child0);

        //创建一个操作符节点t，内容是END
        Node t = new Node();
        t.init();
        t.setNodekind("ExpK");
        t.setKind("OpK");
        t.getAttr().getExpAttr().setOp(TokenType.END);
        opcStack.push(t);
        opstack();
    }
    public void process75() {
        Symbol s1 = new Tsymbol(new Token(TokenType.RETURN));
        symbolStack.push(s1);
    }

    public void process76() {
        Symbol s1 = new Tsymbol(new Token(TokenType.RPAREN));
        Symbol s2 = new Nsymbol("ActParamList");
        Symbol s3 = new Tsymbol(new Token(TokenType.LPAREN));
        symbolStack.push(s1);
        symbolStack.push(s2);
        symbolStack.push(s3);

        currentP.getChild0().init();
        treeStack.push(currentP.getChild0());
    }
    public void process77() {
        treeStack.pop();
    }
    public void process78() {
        Symbol s1 = new Nsymbol("ActParamMore");
        Symbol s2 = new Nsymbol("Exp");
        symbolStack.push(s1);
        symbolStack.push(s2);

        Node node = new Node();
        node.init();
        node.setNodekind("ExpK");
        node.setKind("OpK");
        node.getAttr().getExpAttr().setOp(TokenType.END);
        opcStack.push(node);
        opstack();
    }
    public void process79(){
        //o(*￣▽￣*)ブ
    }

    public void process80(){
        Symbol s1 = new Nsymbol("ActParamList");
        Symbol s2 = new Tsymbol(new Token(TokenType.COMMA));
        symbolStack.push(s1);
        symbolStack.push(s2);

        treeStack.push(currentP.getSiblings());
    }

    public void process81(){
        Symbol s1 = new Nsymbol("OtherRelE");
        Symbol s2 = new Nsymbol("Exp");
        symbolStack.push(s1);
        symbolStack.push(s2);

        Node node = new Node();
        node.init();
        node.setNodekind("ExpK");
        node.setKind("OpK");
        node.getAttr().getExpAttr().setOp(TokenType.END);

        opcStack.push(node);
        opstack();

        getExpResult = false;
    }
    public void process82(){
        Symbol s1 = new Nsymbol("Exp");
        Symbol s2 = new Nsymbol("CmpOp");
        symbolStack.push(s1);
        symbolStack.push(s2);

        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setKind("Opk");
        Token nowToken = getNowToken();
        String nowname = nowToken.getTokenType().getName();
        if(nowname.equals("<")){
            currentP.getAttr().getExpAttr().setOp(TokenType.LT);
        }else if(nowname.equals("=")){
            currentP.getAttr().getExpAttr().setOp(TokenType.EQ);
        }

        Node peek = opcStack.peek();
        opstack();
        int sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        int nowp = priosity(nowname);
        while (sTopp>=nowp) {//栈顶操作符sTop优先级高于或等于当前操作符currentP
            //弹操作符栈，得到操作符节点t
            Node t = opcStack.pop();
            opstack();
            //操作数栈弹出两个操作数节点指针,分别作为 t 的两个儿子节点
            Node Rnum = opdStack.pop();
            Node Lnum = opdStack.pop();
            Node child0 = t.getChild0();
            child0.init();
            Node child1 = t.getChild1();
            child1.init();
            child0.setAll(Lnum);
            child1.setAll(Rnum);
            opdStack.push(t);

            peek = opcStack.peek();
            opstack();
            sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        }
        opcStack.push(currentP);
        opstack();
        getExpResult = true;

    }

    public void process83(){
        Symbol s1 = new Nsymbol("OtherTerm");
        Symbol s2 = new Nsymbol("Term");
        symbolStack.push(s1);
        symbolStack.push(s2);
    }

    public void process84(){
        Token nowToken = getNowToken();
        //当前token为右括号)而且expflag为0
        if(nowToken.getTokenType().getName().equals(")")&&expflag!=0){
            opstack();
            String opup = opcStack.peek().getAttr().getExpAttr().getOp().getName();
            opstack();
            while (!opup.equals("(")){
                //弹操作符栈，得到操作符节点t
                Node t = opcStack.pop();
                opstack();
                //操作数栈弹出两个操作数节点指针,分别作为 t 的两个儿子节点
                Node Rnum = opdStack.pop();
                Node Lnum = opdStack.pop();
                Node child0 = t.getChild0();
                child0.init();
                Node child1 = t.getChild1();
                child1.init();
                child0.setAll(Lnum);
                child1.setAll(Rnum);
                opdStack.push(t);

                opup = opcStack.peek().getAttr().getExpAttr().getOp().getName();
                opstack();
            }
            opcStack.pop();
            opstack();
            expflag--;
        }
        //getExpResult或
        //getExpResult2为真
        if (getExpResult == true || getExpResult2 == true) {
            opstack();
            while (!opcStack.peek().getAttr().getExpAttr().getOp().getName().equals("end")) {
                System.out.println("执行了一次84.。。");
                //操作符栈顶不是栈底表示END 是
                //弹操作符栈，得到操作符节点t
                Node t = opcStack.pop();
                //操作数栈弹出两个操作数节点指针,分别作为 t 的两个儿子节点
                Node Rnum = opdStack.pop();
                Node Lnum = opdStack.pop();
                Node child0 = t.getChild0();
                child0.init();
                Node child1 = t.getChild1();
                child1.init();
                child0.setAll(Lnum);
                child1.setAll(Rnum);
                opdStack.push(t);
                opstack();
            }
            opcStack.pop();
            opstack();

            Node opdUp = opdStack.pop();

            currentP = opdUp;
            //弹语法树栈
            Node t = treeStack.pop();
            //相应指针(*t)赋值currentP
            t.setAll(currentP);
            getExpResult2 = false;
        }
    }
    public void process85(){
        Symbol s1 = new Nsymbol("Exp");
        Symbol s2 = new Nsymbol("AddOp");
        symbolStack.push(s1);
        symbolStack.push(s2);

        Token nowToken = getNowToken();
        String nowname = nowToken.getTokenType().getName();
        //建立一个表达式节点，具体类型是操作符类型
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setKind("Opk");
        if(nowname.equals("+")){
            currentP.getAttr().getExpAttr().setOp(TokenType.PLUS);
        }else if(nowname.equals("-")){
            currentP.getAttr().getExpAttr().setOp(TokenType.MINUS);
        }

        Node peek = opcStack.peek();
        int sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        int nowp = priosity(nowname);
        while (sTopp >= nowp) {//栈顶操作符sTop优先级高于或等于当前操作符currentP
            //弹操作符栈，得到操作符节点t
            Node t = opcStack.pop();
            //操作数栈弹出两个操作数节点指针,分别作为 t 的两个儿子节点
            Node Rnum = opdStack.pop();
            Node Lnum = opdStack.pop();
            Node child0 = t.getChild0();
            child0.init();
            Node child1 = t.getChild1();
            child1.init();
            child0.setAll(Lnum);
            child1.setAll(Rnum);
            opdStack.push(t);

            peek = opcStack.peek();
            sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        }
        opcStack.push(currentP);
        opstack();
    }
    public void process86(){
        Symbol s1 = new Nsymbol("OtherFactor");
        Symbol s2 = new Nsymbol("Factor");
        symbolStack.push(s1);
        symbolStack.push(s2);
    }
    public void process87(){
        //空函数开心o(*￣▽￣*)ブ
    }
    public void process88(){
        Symbol s1 = new Nsymbol("Term");
        Symbol s2 = new Nsymbol("MultOp");
        symbolStack.push(s1);
        symbolStack.push(s2);

        Token nowToken = getNowToken();
        String nowname = nowToken.getTokenType().getName();
        //建立一个表达式节点，具体类型是操作符类型
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setKind("Opk");
        if(nowname.equals("/")){
            currentP.getAttr().getExpAttr().setOp(TokenType.DIVIDE);
        }else if(nowname.equals("*")){
            currentP.getAttr().getExpAttr().setOp(TokenType.TIMES);
        }

        Node peek = opcStack.peek();
        opstack();
        int sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        int nowp = priosity(nowname);
        while (sTopp>=nowp){//栈顶操作符sTop优先级高于或等于当前操作符currentP
            //弹操作符栈，得到操作符节点t
            Node t = opcStack.pop();
            opstack();
            //操作数栈弹出两个操作数节点指针,分别作为 t 的两个儿子节点
            Node Rnum = opdStack.pop();
            Node Lnum = opdStack.pop();
            Node child0 = t.getChild0();
            child0.init();
            Node child1 = t.getChild1();
            child1.init();
            child0.setAll(Lnum);
            child1.setAll(Rnum);
            opdStack.push(t);

            peek = opcStack.peek();
            opstack();
            sTopp = priosity(peek.getAttr().getExpAttr().getOp().getName());
        }
        opcStack.push(currentP);
        opstack();
    }

    public void process89() {
        symbolStack.push(new Tsymbol(new Token(TokenType.RPAREN)));
        symbolStack.push(new Nsymbol("Exp"));
        symbolStack.push(new Tsymbol(new Token(TokenType.LPAREN)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setKind("OpK");
        currentP.getAttr().getExpAttr().setOp(TokenType.LPAREN);
        opcStack.push(currentP);
        opstack();
        expflag++;
    }

    public void process90() {
        symbolStack.push(new Tsymbol(new Token(TokenType.INTC)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setKind("ConstK");
        currentP.getAttr().getExpAttr().setVal(Integer.valueOf(getNowToken().getValue()));

        opdStack.push(currentP);

    }

    public void process91() {
        symbolStack.push(new Nsymbol("Variable"));
    }

    public void process92() {
        symbolStack.push(new Nsymbol("VariMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setType_name(getNowToken().getValue());
        opdStack.push(currentP);
    }
    public void process93() {
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.getAttr().getExpAttr().setVarkind("IdV");
    }

    public void process94(){
        Symbol symbol1 = new Tsymbol(new Token(TokenType.RMIDPAREN));
        Symbol symbol2 = new Nsymbol("Exp");
        Symbol symbol3 = new Tsymbol(new Token(TokenType.LMIDPAREN));
        symbolStack.push(symbol1);
        symbolStack.push(symbol2);
        symbolStack.push(symbol3);
        //
        currentP.getAttr().getExpAttr().setVarkind("ArrayMembV");

        Node child0 = currentP.getChild0();
        child0.init();
        opdStack.push(child0);
        Node tt = new Node();
        tt.init();
        tt.setNodekind("ExpK");
        tt.setKind("OpK");
        tt.getAttr().getExpAttr().setOp(TokenType.END);
        opcStack.push(tt);
        opstack();
        getExpResult2=true;

    }

    public void process95() {
        symbolStack.push(new Nsymbol("FieldVar"));
        symbolStack.push(new Tsymbol(new Token(TokenType.EOF)));
        //
        currentP.setNodekind("DecK");
        currentP.setKind("RecordK");
        Node child0 = currentP.getChild0();
        child0.init();
        treeStack.push(child0);
    }

    public void process96() {
        symbolStack.push(new Nsymbol("FieldVarMore"));
        symbolStack.push(new Tsymbol(new Token(TokenType.ID)));
        //
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.setType_name(getNowToken().getValue());
        opdStack.push(currentP);
    }

    public void process97() {
        currentP = new Node();
        currentP.init();
        currentP.setNodekind("ExpK");
        currentP.getAttr().getExpAttr().setVarkind("IdV");
    }

    public void process98(){
        Symbol symbol1 = new Tsymbol(new Token(TokenType.RMIDPAREN));
        Symbol symbol2 = new Nsymbol("Exp");
        Symbol symbol3 = new Tsymbol(new Token(TokenType.LMIDPAREN));
        symbolStack.push(symbol1);
        symbolStack.push(symbol2);
        symbolStack.push(symbol3);

        currentP.getAttr().getExpAttr().setVarkind("FieldMembV");

        Node child0 = currentP.getChild0();
        child0.init();
        opdStack.push(child0);
        Node tt = new Node();
        tt.init();
        tt.setNodekind("ExpK");
        tt.setKind("OpK");
        tt.getAttr().getExpAttr().setOp(TokenType.END);
        opcStack.push(tt);
        opstack();
        getExpResult2=true;

    }


    public void process99(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.LT));
        symbolStack.push(tsymbol);
    }
    public void process100(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.EQ));
        symbolStack.push(tsymbol);
    }
    public void process101(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.PLUS));
        symbolStack.push(tsymbol);
    }
    public void process102(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.MINUS));
        symbolStack.push(tsymbol);
    }
    public void process103(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.TIMES));
        symbolStack.push(tsymbol);
    }
    public void process104(){
        Tsymbol tsymbol = new Tsymbol(new Token(TokenType.DIVIDE));
        symbolStack.push(tsymbol);
    }


    
    //************************************************************************
    public void opstack(){
        System.out.print("此时opcStack：[");
        for(Node t:opcStack){
            System.out.print(t.getAttr().getExpAttr().getOp().getName()+",");
        }
        System.out.print("]");
        System.out.println();
    }
    

}
