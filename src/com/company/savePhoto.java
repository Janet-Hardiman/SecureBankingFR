package com.company;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Created by Dinmahr on 03/02/2017.
 */
public class savePhoto {

    public savePhoto(String filenameSave) {


        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filenameSave));
        } catch (IOException e) {
            e.printStackTrace();
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
        System.out.println(path);


        File f2 = new File(path + "\\" + filenameSave);
        String myName = filenameSave;

        System.out.println(myName);
        //File f2 = new File(path);  //output file path
        try {
            ImageIO.write(img, "png", f2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        sendToPi(myName);
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
}