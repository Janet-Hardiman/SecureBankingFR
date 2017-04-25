package com.company;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.optional.ssh.Scp;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;


/**
 * Created by Dinmahr on 03/02/2017.
 */
public class savePhoto extends Thread{

    private String imageCropSave;
    // for saving a detected face image
    private static final String FACE_DIR = "trainingImages";
    private static String FACE_FNM;
    private static final int FACE_WIDTH = 125;
    private static final int FACE_HEIGHT = 150;
    // used for constructing a filename for saving a face
    private int fileCount = 0;


    public savePhoto(String imageCropSave) {
        this.FACE_FNM = imageCropSave;
        System.out.println(FACE_FNM);
    }

    public void run(){
        System.out.println("SavePhoto thread running");

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(FACE_FNM));
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveClip(img);

        File theDir = new File("trainingImages");
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


    /*    File f2 = new File(path + "\\" + FACE_FNM); //output file path
        String myName = FACE_FNM;

        System.out.println(myName);

        try {
            ImageIO.write(img, "png", f2);
        } catch (IOException e) {
            System.out.println("Could not save image to " + f2);
            e.printStackTrace();
        }
        sendToPi(myName);*/
    }

    private void sendToPi(String photo) {
        System.out.println("Sending file to pi");
        // This make scp copy of
        // one local file to remote dir
        org.apache.tools.ant.taskdefs.optional.ssh.Scp scp = new Scp();
        int portSSH = 22;
        String srvrSSH = "securebanking.hopto.org";
        String userSSH = "pi";
        String pswdSSH = "G********gmit";
        String localFile = "images/" + photo;
        String remoteDir = "/home/pi/Desktop";

        scp.setPort(portSSH);
        scp.setLocalFile(localFile);
        scp.setTodir(userSSH + ":" + pswdSSH + "@" + srvrSSH + ":" + remoteDir);
        scp.setProject(new Project());
        scp.setTrust(true);
        scp.execute();
    }

    private void saveClip(BufferedImage clipIm)
  /* resizes to at least a standard size, converts to grayscale,
     clips to an exact size, then saves in a standard location */
    {
        long startTime = System.currentTimeMillis();

        System.out.println("Saving clip...");
        BufferedImage grayIm = resizeImage(clipIm);
        //   saveImage(grayIm, FACE_DIR + "/" + FACE_FNM + fileCount + ".png");
        //fileCount++;
        BufferedImage faceIm = clipToFace(grayIm);
        saveImage(faceIm, FACE_DIR + "/" + FACE_FNM);   // + fileCount + ".png"
        //fileCount++;

        System.out.println("  Save time: " + (System.currentTimeMillis() - startTime) + " ms");
    }  // end of saveClip()




    private BufferedImage resizeImage(BufferedImage im)
    // resize to at least a standard size, then convert to grayscale
    {
        // resize the image so *at least* FACE_WIDTH*FACE_HEIGHT size
        int imWidth = im.getWidth();
        int imHeight = im.getHeight();
        System.out.println("Original (w,h): (" + imWidth + ", " + imHeight + ")");

        double widthScale = FACE_WIDTH / ((double) imWidth);
        double heightScale = FACE_HEIGHT / ((double) imHeight);
        double scale = (widthScale > heightScale) ? widthScale : heightScale;

        int nWidth = (int)Math.round(imWidth* scale);
        int nHeight = (int)Math.round(imHeight* scale);

        // convert to grayscale while resizing
        BufferedImage grayIm = new BufferedImage(nWidth, nHeight,
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = grayIm.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(im, 0, 0, nWidth, nHeight,  0, 0, imWidth, imHeight, null);
        g2.dispose();

        System.out.println("Scaled gray (w,h): (" + nWidth + ", " + nHeight + ")");
        return grayIm;
    }  // end of resizeImage()


    private BufferedImage clipToFace(BufferedImage im)
    // clip image to FACE_WIDTH*FACE_HEIGHT size
    // I assume the input image is face size or bigger
    {
        int xOffset = (im.getWidth() - FACE_WIDTH)/2;
        int yOffset = (im.getHeight() - FACE_HEIGHT)/2;
        BufferedImage faceIm = null;
        try {
            faceIm = im.getSubimage(xOffset, yOffset, FACE_WIDTH, FACE_HEIGHT);
            System.out.println("Clipped image to face dimensions: (" +
                    FACE_WIDTH + ", " + FACE_HEIGHT + ")");
        }
        catch(RasterFormatException e) {
            System.out.println("Could not clip the image");
            // faceIm = im.getBufferedImage();
        }
        return faceIm;
    }  // end of clipToFace()


    private void saveImage(BufferedImage im, String fnm)
    // save image in fnm
    {
        try {
            ImageIO.write(im, "png", new File(fnm));
            System.out.println("Saved image to " + fnm);
        }
        catch (IOException e) {
            System.out.println("Could not save image to " + fnm);
        }
    }  // end of saveImage()
}