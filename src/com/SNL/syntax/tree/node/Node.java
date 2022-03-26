package com.SNL.syntax.tree.node;

import com.SNL.lex.Entity.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 树结点
 *
 */



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    private Node child0;   //儿子节点
    private Node child1;
    private Node child2;

    private Node siblings;   //兄弟节点
    private int lineno;     //源程序行号
    /*
     （标志接点） ProK, PheadK, TypeK, VarK,ProcDecK, StmLK,
     （具体节点）DecK, StmtK, ExpK
    */
    private String nodekind;  //语法树节点类型

    /*
    声明节点 nodekind = DecK,   ArrayK,CharK,IntegerK,RecordK,IdK
    语句节点 nodekind = StmtK ， IfK,WhileK,AssignK,ReadK,WriteK,CallK,ReturnK
    表达式节点 nodekind=ExpK ，  OpK,ConstK,IdK
     */
    private String kind;    //具体结点的类型
    private int idnum; // 一个节点中的标志符的个数
    private ArrayList<String> name;  //节点中标识符的名字
    //table 指针数组，数组成员是节点中的各个标志符在符号表中的入口
    private String type_name;  //记录类型名，当节点为声明类型，且类型是由类型标志符 表示时有效
    private Attr attr; //记录语法树节点其他属性(数组，过程，表达式)

    int childNum ;
    Node[] child = new Node[10] ;
    Node father ;

    int flag ;     // 0 表示叶子节点
    TokenType NonTerminal ;
    TokenType Terminal ;
    String data ;

    int x , y , width , length ;

    public Node getChild(int num ) {
        return child[num] ;
    }
    public void setChild(Node child){
        child = child;
        childNum++;
    }

    //压栈之前的初始化操作
    public void init(){
        child0 = new Node();
        child1 = new Node();
        child2 = new Node();
        siblings = new Node();
        lineno = 0;
        nodekind = "";
        kind = "";
        idnum = 0;
        name = new ArrayList<>();
        type_name = "";
        attr = new Attr();
    }
//
//    public Node getSiblings(){
//        Node sub = this.siblings;
//        sub.init();
//        return sub;
//    }


    //赋值操作
    public void setAll(Node a){
        child0 = a.getChild0();
        child1 = a.getChild1();
        child2 = a.getChild2();
        siblings = a.getSiblings();
        lineno = a.getLineno();
        nodekind=a.getNodekind();
        kind = a.getKind();
        idnum=a.getIdnum();
        name = a.getName();
        type_name = a.getType_name();
        attr = a.getAttr();
    }

    public boolean isNull(Node node){
//        System.out.println(node);
        boolean flag = true;
        if (node.getNodekind()==null || node.getKind()==null || node == null){
            return true;
        }else {
            if (!node.getKind().equals("") || !node.getNodekind().equals("")) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 展示节点信息 （未完成）
     * @param root
     */
    public void showNode(Node root){
        //（具体节点）DecK, StmtK, ExpK
       /* if(node.getNodekind().equals("DecK") || node.getNodekind().equals("StmtK") || node.getNodekind().equals("ExpK")){
            System.out.print(node.getNodekind());

            System.out.print("\t"+node.getKind());

            //声明节点
            if(node.getKind().equals("DecK")){
                System.out.print("\t"+node.getKind());
                if(node.getKind().equals("ArrayK")){
                    //数组类型
                    ArrayAttr arrayAttr = node.getAttr().getArrayAttr();
                    System.out.print("\t"+arrayAttr.getChildType()+"\t"+arrayAttr.getLow()+"\t"+arrayAttr.getUp());
                }
                if(node.getIdnum()>0){
                    for(String name : node.getName()){
                        System.out.print("\t"+name);
                    }
                }


            }else if(node.getKind().equals("StmtK")){
                //语句节点
                System.out.println("\t"+node.getKind());   //IfK ...
                if(node.getIdnum()>0){
                    for(String name : node.getName()){
                        System.out.print("\t"+name);
                    }
                }

            }else if (node.getKind().equals("ExpK")){
                //表达式节点
                //表达式左部
                if(node.getIdnum()>0){
                    for(String name : node.getName()){
                        System.out.print("\t"+name);
                    }
                }
                ExpAttr expAttr = node.getAttr().getExpAttr();
                if(!expAttr.getVarkind().equals(""))
                    System.out.print("\t"+expAttr.getVarkind());
                if(!expAttr.getOp().getName().equals("end"))
                    System.out.print("\t"+expAttr.getOp());
                if(expAttr.getVal()!=0)
                    System.out.print("\t"+expAttr.getVal());
                if(!expAttr.getType().equals(""))
                    System.out.print("\t"+expAttr.getType());

            }


        }else {
            //(标志接点) ProK, PheadK, TypeK, VarK,ProcDecK, StmLK,
            String s = node.getNodekind();
            System.out.print(s);
            if(node.getIdnum()>0){
                for(String name : node.getName()){
                    System.out.print("\t"+name);
                }
            }

        }*/
        if (root.getNodekind().equals("ProK"))
        {
            System.out.println("["+root.getNodekind()+"]");
            return;
        }
        else if (root.getNodekind().equals("PheadK") || root.getNodekind().equals("ProcDecK"))
        {
            System.out.print("["+root.getNodekind()+"]"+"  ");
            //if(root.getIdnum()>0){
                for(String name : root.getName()){
                    System.out.print(name+"  ");
                }
            //}
            System.out.println();
            return;
        }
        else if(root.getNodekind().equals("DecK")){
            System.out.print("["+root.getNodekind()+"]"+"  ");
            System.out.print(root.getAttr().getProcAttr().getParamt()+": ");
            System.out.print(root.getKind()+" ");
            switch (root.getKind())
            {
                case "ArrayK":
                {
                    System.out.print("["+root.getAttr().getArrayAttr().getUp()+","+root.getAttr().getArrayAttr().getLow()+"]"+root.getAttr().getArrayAttr().getChildType()+"  ");
                    break;
                }
                case "IdK":
                {
                    System.out.print(root.getType_name());
                    break;
                }
                default:
                    break;
            }
//            if(root.getIdnum()>0){
                for(String name : root.getName()){
                    System.out.print(name+"  ");
                }
//            }
            System.out.println();
            return;
        }
        else if(root.getNodekind().equals("StmtK")){
            System.out.print("["+root.getNodekind()+"]"+"  ");
            System.out.println(root.getKind()+"  ");
//            if(root.getIdnum()>0){
                for(String name : root.getName()){
                    System.out.print(name+"  ");
                }
//            }
            System.out.println();
            return;
        }
        else if (root.getNodekind().equals("ExpK")) {
            System.out.print("["+root.getNodekind()+"]"+"  ");
            if(root.getKind().equals("OpK")){
                System.out.print(root.getKind()+"  ");
                System.out.print(root.getAttr().getExpAttr().getOp().getName());
            }
            if(root.getKind().equals("ConstK")){
                System.out.print(root.getKind()+"  ");
                System.out.print(root.getAttr().getExpAttr().getVal());
            }
            if(root.getKind().equals("IdK")){
                System.out.println(root.getType_name()+"  ");
                System.out.print(root.getKind());
            }
            System.out.println();
            return;
        }
        else {
            System.out.println("["+root.getNodekind()+"]");
            return;
        }
    }

    //展示语法树(zanshi)
    public void showTree(Node root,int deep){
        System.out.println(root);
        if(isNull(root)){
            return;
        }
        for (int i=0; i<deep; i++){
            System.out.print("\t");
        }

        showNode(root);

        showTree(root.getChild0(),deep+1);
        showTree(root.getChild1(),deep+1);
        showTree(root.getChild2(),deep+1);
        showTree(root.getSiblings(),deep);


    }


}
