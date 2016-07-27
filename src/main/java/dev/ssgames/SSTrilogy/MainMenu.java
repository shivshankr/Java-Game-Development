package dev.ssgames.SSTrilogy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenu {

    Container contentPane;
    UserGamedata currentUser;
    public static boolean stoping;

    public MainMenu(Container content, UserGamedata ch) {
        contentPane = content;
        this.currentUser = ch;
    }

    public void start(){

        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setBackground(Color.green);

        JLabel displayText = new JLabel("SS DUOLOGY");
        displayText.setFont(new Font("Papyrus", Font.BOLD, 45));
        displayText.setBounds(260,50,500,100);
        displayText.setForeground(Color.red);
        contentPane.add(displayText);

        JLabel CreditText = new JLabel("");
        CreditText.setFont(new Font("Serif", Font.PLAIN, 20));
        CreditText.setBounds(530,220,260,30);
        CreditText.setForeground(Color.black);
        CreditText.setBackground(Color.BLUE);
        contentPane.add(CreditText);

        JLabel CreditText1 = new JLabel("");
        CreditText1.setFont(new Font("Serif", Font.PLAIN, 20));
        CreditText1.setBounds(530,250,360,30);
        CreditText1.setForeground(Color.black);
        CreditText1.setBackground(Color.BLUE);
        contentPane.add(CreditText1);

        JLabel CreditText2 = new JLabel("");
        CreditText2.setFont(new Font("Serif", Font.PLAIN, 20));
        CreditText2.setBounds(530,280,360,30);
        CreditText2.setForeground(Color.black);
        CreditText2.setBackground(Color.BLUE);
        contentPane.add(CreditText2);

        //JButton LaserB=new JButton("Laser Game");
        JButton SnakeB=new JButton("Snake Game");
        JButton TicTacToeB=new JButton("Tic Tac Toe");
        JButton LeaderB=new JButton("Leaderboards");
        JButton CreditsB=new JButton("Credits");
        JButton ExitB=new JButton("Exit");

        /*LaserB.setBounds(350, 150,160, 60 );
        LaserB.setBackground(Color.red ) ;
        LaserB.setForeground(Color.white);
        contentPane.add(LaserB);*/

        SnakeB.setBounds(350, 210,160, 60 );
        SnakeB.setBackground(Color.red ) ;
        SnakeB.setForeground(Color.white);
        contentPane.add(SnakeB);

        TicTacToeB.setBounds(350, 270,160, 60 );
        TicTacToeB.setBackground(Color.red ) ;
        TicTacToeB.setForeground(Color.white);
        contentPane.add(TicTacToeB);

        LeaderB.setBounds(350, 330,160, 60 );
        LeaderB.setBackground(Color.red ) ;
        LeaderB.setForeground(Color.white);
        contentPane.add(LeaderB);

        CreditsB.setBounds(350, 390,160, 60 );
        CreditsB.setBackground(Color.red ) ;
        CreditsB.setForeground(Color.white);
        contentPane.add(CreditsB);

        ExitB.setBounds(350, 450,160, 60 );
        ExitB.setBackground(Color.red ) ;
        ExitB.setForeground(Color.white);
        contentPane.add(ExitB);


        /*LaserB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });*/

        SnakeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MyThread myThread = new MyThread();
                myThread.start();

            }
        });

        TicTacToeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                TTTDisplayOpt TTT=new TTTDisplayOpt(contentPane);
                TTT.start();
            }
        });

        LeaderB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        CreditsB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                CreditText.setText("by:");
                CreditText1.setText("Shiv Shankar Sukumaran (14CO244)");
                CreditText2.setText("Samarth Mishra (14CO240)");
            }
        });

        ExitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });

    }

    public class MyThread extends Thread {

        public void run(){
            Login.display.getFrame().setVisible(false);
            SnakeGame snake = new SnakeGame();
            snake.startGame();
        }
    }


}
