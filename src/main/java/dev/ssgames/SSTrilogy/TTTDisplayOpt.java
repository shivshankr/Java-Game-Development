package dev.ssgames.SSTrilogy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TTTDisplayOpt {

    Container contentPane;
    boolean player;

    public TTTDisplayOpt(Container content) {
        contentPane = content;
    }

    public void start(){

        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setBackground(Color.yellow);

        JButton OnePlayerB=new JButton("One Player");
        JButton TwoPlayerB=new JButton("Two Player");
        JButton BackB=new JButton("Go back");

        OnePlayerB.setBounds(350, 210,160, 60 );
        OnePlayerB.setBackground(Color.green ) ;
        OnePlayerB.setForeground(Color.white);
        contentPane.add(OnePlayerB);

        TwoPlayerB.setBounds(350, 270,160, 60 );
        TwoPlayerB.setBackground(Color.green ) ;
        TwoPlayerB.setForeground(Color.white);
        contentPane.add(TwoPlayerB);

        BackB.setBounds(350, 330,160, 60 );
        BackB.setBackground(Color.green ) ;
        BackB.setForeground(Color.white);
        contentPane.add(BackB);


        OnePlayerB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                player =true;
                MyThread myThread = new MyThread();
                myThread.start();

            }
        });

        TwoPlayerB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                player=false;
                MyThread myThread = new MyThread();
                myThread.start();
            }
        });

        BackB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MainMenu menu=new MainMenu(contentPane,null) ;
                menu.start();
            }
        });

    }

    public class MyThread extends Thread {

        public void run(){
            Login.display.getFrame().setVisible(false);
            new TTTGamePlay(player);

        }
    }


}
