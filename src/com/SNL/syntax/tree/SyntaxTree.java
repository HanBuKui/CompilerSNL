package com.SNL.syntax.tree;

import com.SNL.syntax.tree.node.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.PrintStream;

/**
 * 语法树结构
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyntaxTree {
    private Node root;    //节点
    private PrintStream out;

    public void showSyntaxTree() {
        //pre(root, 0, 0);
    }

//    //递归打印语法树   兄弟应在同一level
//    public void pre(Node node, int level, int b) {
//        //if (node == null) return;
//        if(level!=0 && b!=0) {
//            out.println("");
//        }
//
//        for(int i=0;i<level+b;i++){
//            out.print(" ");
//        }
//
//        System.out.print(node.getValue());
//
//        if (node.getChildren()!=null){
//            //有儿子节点
//            pre(node.getChildren(),level+1,b+1);
//        }
//        if (node.getSiblings()!=null){
//            pre(node.getSiblings(),level,b);
//        }
//    }
//
//    //单元测试
//
//    public static void main(String[] args) throws FileNotFoundException {
//
//        Node t1 = new Node(null,null,"root");
//        Node t2_1 = new Node(null,null,"a");
//        Node t2_2 = new Node(null,null,"b");
//        Node t2_3 = new Node(null,null,"c");
//        Node t3_1_1 = new Node(null,null,"d");
//        Node t3_2_1 = new Node(null,null,"e");
//        Node t3_2_2 = new Node(null,null,"f");
//        Node t3_3_1 = new Node(null,null,"g");
//        Node t4_2_1_1 = new Node(null,null,"h");
//        Node t4_1_1_1 = new Node(null,null,"j");
//        //level0:
//        t1.setChildren(t2_1);
//        //level1:
//        t2_1.setSiblings(t2_2);
//        t2_2.setSiblings(t2_3);
//
//        t2_1.setChildren(t3_1_1);
//        t3_1_1.setChildren(t4_1_1_1);
//        t2_2.setChildren(t3_2_1);
//        t3_2_1.setChildren(t4_2_1_1);
//        t3_2_1.setSiblings(t3_2_2);
//        t2_3.setChildren(t3_3_1);
//
//
//        SyntaxTree s = new SyntaxTree(t1,System.out);
//        s. showSyntaxTree();
//    }
}
