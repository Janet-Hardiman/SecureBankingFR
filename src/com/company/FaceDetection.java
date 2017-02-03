package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;

/**
 * Created by Dinmahr on 30/01/2017.
 */
public class FaceDetection extends javax.swing.JFrame {

    private JPanel facePanel;
    private JButton buttonCapture;
    private JButton buttonSave;
    private JButton buttonRetake;
    private JButton buttonCancel;

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    CascadeClassifier faceDetector = new CascadeClassifier("haarcascade_frontalface_alt.xml");
    MatOfRect faceDetections = new MatOfRect();

    class DaemonThread implements Runnable {
        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = facePanel.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            for (Rect rect : faceDetections.toArray()) {
                                // System.out.println("ttt");
                                Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                                        new Scalar(0, 255,0));
                            }
                            Highgui.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight()-150 , 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error");
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates new form FaceDetectionA
     */
    public FaceDetection() {
        initComponents();
        System.out.println("haarcascade_frontalface_alt.xml");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        facePanel = new javax.swing.JPanel();
        buttonCapture = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonRetake = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GroupLayout facePanelLayout = new GroupLayout(facePanel);
        facePanel.setLayout(facePanelLayout);
        facePanelLayout.setHorizontalGroup(
                facePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        facePanelLayout.setVerticalGroup(
                facePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 600, Short.MAX_VALUE) //600 value set the height of the webcam picture
        );


        buttonCapture.setText("Capture");
        buttonCapture.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                buttonCaptureActionPerformed(e);
            }
        });

        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                buttonSaveActionPerformed(e);
            }
        });

        buttonRetake.setText("Retake");
        buttonRetake.addActionListener(new java.awt.event. ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                buttonRetakeActionPerformed(e);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                buttonCancelActionPerformed(e);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)  //(int min, int pref, int max)
                                .addComponent(facePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(buttonCapture)
                                .addGap(86, 86, 86)
                                .addComponent(buttonSave)
                                .addGap(86, 86, 86)
                                .addComponent(buttonRetake)
                                .addGap(86, 86, 86)
                                .addComponent(buttonCancel)
                                .addContainerGap(258, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(facePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonCapture)
                                        .addComponent(buttonSave)
                                        .addComponent(buttonRetake)
                                        .addComponent(buttonCancel))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();

        webSource = new VideoCapture(0);    //video capture from default cam
        myThread = new DaemonThread();      //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                          //start thread

    }// </editor-fold>//GEN-END:initComponents

    private void buttonCaptureActionPerformed(java.awt.event.ActionEvent e) {
        myThread.runnable = false;          //stop thread

    }//end event_buttonCaptureActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent e) {
        myThread.runnable = false;          // stop thread
        webSource.release();                //stop capturing from cam
        //jButton2.setEnabled(false);       //activate start button
        //jButton1.setEnabled(true);        //deactivate stop button
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("webCamImg.png"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File theDir = new File("images");
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir);
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }
        String path = "";
        path = theDir.getAbsolutePath();
        //    JOptionPane.showMessageDialog(facePanel, "saved to:  " + path);
        System.out.println(path);


        File f2 = new File(path + "\\myName.jpg");
        String myName = "myName.jpg";

        //File f2 = new File(path);  //output file path
        try {
            ImageIO.write(img, "jpg", f2);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sendToPi(myName);
    }//end event_buttonSaveActionPerformed

    private void buttonRetakeActionPerformed(java.awt.event.ActionEvent e) {
        webSource = new VideoCapture(0);    //video capture from default cam
        myThread = new DaemonThread();      //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
    }//end event_buttonRetakeActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent e) {
        webSource.release();                //stop capturing from cam
        dispose();
    }

    private void sendToPi(String photo) {
        // This make scp copy of
        // one local file to remote dir
        org.apache.tools.ant.taskdefs.optional.ssh.Scp scp = new Scp();
        int portSSH = 22;
        String srvrSSH = "192.168.43.184";
        String userSSH = "pi";
        String pswdSSH = "raspberry";
        String localFile = "images/" + photo;
        String remoteDir = "/home/pi/Desktop";

        scp.setPort(portSSH);
        scp.setLocalFile(localFile);
        scp.setTodir(userSSH + ":" + pswdSSH + "@" + srvrSSH + ":" + remoteDir);
        scp.setProject(new Project());
        scp.setTrust(true);
        scp.execute();
    }


/*    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        *//* Set the Nimbus look and feel *//*
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        *//* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         *//*
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        *//* Create and display the form *//*
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetection().setVisible(true);
            }
        });
    }*/
}



