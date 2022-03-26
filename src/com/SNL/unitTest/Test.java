package com.SNL.unitTest;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Stack;

/**
 * @ClassName: Test
 * @Description: TODO
 * @author: 涵不亏
 * @date: 2021/6/10  21:31
 */

@ToString
class Son{
    public String sonname;
}

@ToString
class Person{
    public Son son;
    public String name;
}


public class Test {
    public static void main(String[] args) throws Exception{
//        Node root = new Node(); //语法树根节点
//
//        root.init();
//
////        System.out.println(root);
//
////        root.initChild0();
////        root.initChild1();
////        root.initChild2();
//
//        Stack<Node> treeStack = new Stack<>();       //语法树栈
//
//
//        root.setNodekind("ProK");
//
//        //root.getChild0().init();
//
////        System.out.println(root.getChild0().getNodekind());
//
//
//        treeStack.push(root.getChild2()); //指向  程序体     的指针 child[2]的地址
//        treeStack.push(root.getChild1()); //指向  声明部分    的指针 child[1]的地址
//        treeStack.push(root.getChild0()); //指向   程序头    的儿子指针 child[0]的地
//
//       // System.out.println(root.getChild0());
//
//        Node pop = treeStack.pop();
//
////        if(pop == root.getChild0()){
////            System.out.println("same");
////        }else {
////            System.out.println("not same");
////        }
//
//
//        Node t1 = new Node();
//        t1.init();
//        t1.setNodekind("haha");
//       pop.setAll(t1);
//        //pop = t1;
////        if(pop == root.getChild0()){
////            System.out.println("same");
////        }else {
////            System.out.println("not same");
////        }
//
//
//        System.out.println(root.getChild0().getNodekind());
       // System.out.println("此时opcStck："+opcStack);

        Person p1 = new Person();
        System.out.println(p1);
        p1.name = "zjh";
        Person p2 = p1;
//        if(p1==p2){
//            System.out.println("==");
//        }
//        Stack<Person> stack = new Stack<>();
//        stack.push(p1);
//        p2 = stack.pop();
//        p2.name="qby";
        p2.son = new Son();
        p2.son.sonname="qby";
//        System.out.println(p1);
//        System.out.println(p2);

//        Node node = new Node();
//        node.init();
//        System.out.println(node.getSiblings());

        String s = "abc";
        String s1 =s;
        s1 = "tyoi";
        System.out.println(s);
        System.out.println(s1);



    }
}
