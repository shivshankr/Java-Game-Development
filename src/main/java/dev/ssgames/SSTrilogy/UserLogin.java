package dev.ssgames.SSTrilogy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class UserLogin {

    Container contentPane;
    JLabel resultText;
    UserGamedata currentUser;
    int ch;

    public UserLogin(Container content, int ch) {
        contentPane = content;
        this.ch = ch;
    }

    public void start() {

        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        contentPane.setBackground(new Color(240, 248, 255));

        JLabel displayText = new JLabel("Username:");
        JTextField inputUser = new JTextField(50);
        JButton LoginButton;
        resultText = new JLabel("");

        if (ch == 1) {
            LoginButton = new JButton("Login");
            Login.display.getFrame().setTitle("Existing User");
        } else {
            LoginButton = new JButton("Register");
            Login.display.getFrame().setTitle("New User");
        }

        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (ch == 1) {
                    try {
                        checkLogin(inputUser.getText().toString());

                    }catch(IOException i){
                        i.printStackTrace();
                    }
                    catch(ClassNotFoundException i){
                        i.printStackTrace();
                    }
                } else {
                    try {
                        register(inputUser.getText().toString());

                    }catch(IOException i){
                        i.printStackTrace();
                    }
                    catch(ClassNotFoundException i){
                        i.printStackTrace();
                    }
                }

            }
        });


        displayText.setBounds(300, 200, 100, 50);
        inputUser.setBounds(300, 240, 140, 25);
        LoginButton.setBounds(455, 240, 100, 25);
        resultText.setBounds(300, 300, 500, 50);
        LoginButton.setBackground(Color.YELLOW);

        contentPane.add(displayText);
        contentPane.add(inputUser);
        contentPane.add(LoginButton);
        contentPane.add(resultText);


    }

    void checkLogin(String username) throws FileNotFoundException,
            IOException, ClassNotFoundException{

        UserGamedata newUser = null;
        String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\SStrilogyGame";
        File customDir = new File(path);
        boolean bool=true;
        FileInputStream fileIn=null;
        ObjectInputStream in=null;
        try {
            fileIn = new FileInputStream(path + "\\gamedata.ser");
        }catch(FileNotFoundException i){
            resultText.setText("Username does not exist!");
            bool=false;
        }

        while (bool) {

            try {
                in = new ObjectInputStream(fileIn);
                newUser = (UserGamedata) in.readObject();
                if(newUser.username.equals(username)){
                    in.close();
                    fileIn.close();
                    currentUser=newUser;
                    mainMenu();
                    return;
                }

            } catch (EOFException ignored) {
                resultText.setText("Username does not exist!");
                in.close();
                fileIn.close();
                break;
            }
        }

    }

    void register(String username) throws FileNotFoundException,
            IOException, ClassNotFoundException {

        UserGamedata newUser = null;
        String path = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\SStrilogyGame";
        File customDir = new File(path);
        boolean bool=true;
        FileInputStream fileIn=null;
        ObjectInputStream in=null;
        try {
            fileIn = new FileInputStream(path + "\\gamedata.ser");
        }catch(FileNotFoundException i){
            bool=false;
        }

        if (customDir.exists() || customDir.mkdirs()) {

            while (bool) {

                try {
                    in = new ObjectInputStream(fileIn);
                    newUser = (UserGamedata) in.readObject();
                    if(newUser.username.equals(username)){
                        resultText.setText("Sorry, this username already exists!");
                        in.close();
                        fileIn.close();
                        return;
                    }

                } catch (EOFException ignored) {
                    in.close();
                    fileIn.close();
                    break;
                }
            }

        }


        try {
            newUser = new UserGamedata(username, 0, 0, 0, 0, 0, 0, 100, 100);
            FileOutputStream fileOut = new FileOutputStream(path + "\\gamedata.ser",true);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(newUser);
            out.close();
            fileOut.close();
            currentUser=newUser;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mainMenu();
    }

    void mainMenu(){

        MainMenu menu=new MainMenu(contentPane,currentUser) ;
        menu.start();
    }
}
