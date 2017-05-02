package com.company;

/*
.------..------.
|J.--. ||H.--. |
| :(): || :/\: |
| ()() || (__) |
| '--'J|| '--'H|
`------'`------'
Name: Janet Hardiman
Date: 07/10/2016
Project: Login"
*/

import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Login extends javax.swing.JFrame{
    
    private JPanel login;
    private JLabel imageLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton cancelButton;
    ImageIcon image;

    public Login() {
        initComponents();
        System.out.println("haarcascade_frontalface_alt.xml");
    }

    private void initComponents() {
        image = new ImageIcon("JHardiman_Poster.jpg");
        login = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        imageLabel = new javax.swing.JLabel(image);
        cancelButton = new javax.swing.JButton();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        login.add(imageLabel);
        login.add(loginButton);
        login.add(registerButton);
        login.add(cancelButton);
        getContentPane().add(login);

        setSize(850, 700);


        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                loginButtonActionPerformed(e);
            }
        });

        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                registerButtonActionPerformed(e);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                cancelButtonActionPerformed(e);
            }
        });


    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(ActionEvent e) {
        new Webcam().setVisible(true);
        dispose();
    }

    private void registerButtonActionPerformed(ActionEvent e) {
        new FaceDetection().setVisible(true);
        dispose();
    }

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent e) {
        dispose();

    }//end event_cancelButtonActionPerformed
}
