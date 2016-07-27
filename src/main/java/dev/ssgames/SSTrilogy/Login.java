package dev.ssgames.SSTrilogy;

import com.sun.jndi.toolkit.url.Uri;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;


public class Login {



    public static Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    public Login(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init(){
        display = new Display(title, width, height);
        Container contentPane = display.getFrame().getContentPane();

        contentPane.setLayout(null);
        display.getFrame().remove(display.getCanvas());

        contentPane.setBackground(Color.yellow);
        JButton exUserButton = new JButton("Existing User");
        JButton newUserButton = new JButton("New User");
        JButton GuestButton = new JButton("Guest");


        exUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                UserLogin login=new UserLogin(contentPane,1);
                login.start();
            }
        });

        newUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                UserLogin login=new UserLogin(contentPane,2);
                login.start();

            }
        });

        GuestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MainMenu menu=new MainMenu(contentPane,null) ;
                menu.start();
            }
        });

        exUserButton.setBounds(350, 150,140, 50 );
        exUserButton.setBackground(Color.GREEN ) ;
        newUserButton.setBounds(350, 250,140, 50 );
        newUserButton.setBackground(Color.GREEN ) ;
        GuestButton.setBounds(350, 350,140, 50 );
        GuestButton.setBackground(Color.GREEN ) ;

        contentPane.add(exUserButton);
        contentPane.add(newUserButton);
        contentPane.add(GuestButton);

    }

}
