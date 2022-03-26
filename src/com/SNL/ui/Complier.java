package com.SNL.ui;

import com.SNL.lex.Entity.Token;
import com.SNL.lex.Funtion.ReadFile;
import com.SNL.syntax.DrawTree;
import com.SNL.syntax.LL1.LL1Utils;
import com.SNL.syntax.tree.node.Node;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * @ClassName: Complier
 * @Description: UI界面
 * @author: 涵不亏
 * @date: 2021/6/12  13:10
 */
public class Complier extends JFrame {
    // 此对话框采用边界布局，根据中、南部而分别创建中、南面板
    JPanel northJP = new JPanel();
    JPanel southJP = new JPanel();
    JScrollPane scrollJSP = new JScrollPane();
    JScrollPane scrollJSP2 = new JScrollPane();
    static JTextArea CodeArea = new JTextArea();
    static JTextArea LexArea = new JTextArea();
    JButton doTokenJB = new JButton("词法分析");
    JButton doGrammarJB = new JButton("语法分析");
    JComboBox select;
    public Complier(){
        setTitle("SNL Complier");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int) (0.8 * screenSize.width), windowHeight = (int) (0.8 * screenSize.height);
        setBounds((int) ((screenSize.width - windowWidth) * 0.5),
                (int) ((screenSize.height - windowHeight) * 0.5), windowWidth,
                windowHeight);
        setLayout(new BorderLayout());
        add(northJP, BorderLayout.CENTER);
        add(southJP, BorderLayout.SOUTH);
        northJP.setLayout(new GridLayout(1, 2));
        southJP.setLayout(new FlowLayout());

        northJP.add(scrollJSP);
        scrollJSP.setViewportView(CodeArea);
        CodeArea.setFont(new Font("宋体", Font.PLAIN, 16));
        northJP.add(scrollJSP2);
        scrollJSP2.setViewportView(LexArea);
        LexArea.setFont(new Font("宋体", Font.PLAIN, 16));

        southJP.add(doTokenJB);
        southJP.add(doGrammarJB);

        //词法分析
        doTokenJB.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String text = CodeArea.getText();
                String fileName = "src/resource/snlCode/src.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
                writer.write(text);
                writer.flush();

                ReadFile read = new ReadFile();
                List<Token> tokens = read.read(fileName);
                LexArea.setText("");
                for (Token token : tokens) {
                    String s = "("+(token.getLine()+1)+","+token.getTokenType()+","+token.getValue()
                            +")"+"\n";

                    LexArea.append(s);

                }

            }
        });
        doGrammarJB.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent arg0) {

                String text = CodeArea.getText();
                text = text.substring(0,text.length()-1);
                String fileName = "src/resource/snlCode/src2.txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
                writer.write(text);
                writer.flush();

                LL1Utils utils = new LL1Utils() ;

                if(utils.getRoot()==null){
                    JOptionPane.showMessageDialog(null, utils.showError(), "语法错误", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    DrawTree drawTree = new DrawTree();
                    drawTree.drawtree(new LL1Utils().getRoot());
                }
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Complier();
    }

}