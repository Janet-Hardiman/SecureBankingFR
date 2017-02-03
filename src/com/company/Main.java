package com.company;
/*Janet Hardiman G00305023
 * 5th Year Project - Facial Recognition Part
 * 07/10/2016 - Starting with Paul Dunne's code to Detect a Face from a still image and draw a rectangle box around it,
 *              this code is using open source code from OPENCV (DetectFaceDemo)
 * 14/10/2016 - Then used code to Capture one frame from a webcam (FrameCapture)
 * 21/10/2016 - Next tried to detect a face in the WebCam frame capture and draw a box around it (WebCamFaceDetect)
 *              the above three classes were called from the first main, one at a time - this is now commented out
 * 28/10/2016 - Next stage live stream webcam image, detect face and draw a circle / rectangle around it.
 *              this is ran from the second main method and uses (FacePanel - to open a JPanel and FaceDetector - as name suggests
 *              & to draw circle / rectangle around the face.
 * 04/11/2016 - research Facial Recognition! and update GitHub
 * 11/11/2016 - spent the week researching eye detection to verify the person is real and not just shown an image
 * 18/11/2016 - detect eyes and draw rectangles around them.
 * 25/11/2016 - working on showing a live image and capturing a photo of it.
 * 09/12/2016 - setting up raspberry pi for database
 * 16/12/2016 - project demo
 * 20/01/2017 - testing github
 * 27/01/2017 - Tidying up code
 */
import org.opencv.core.Core;

public class Main {

    public static void main(String arg[]) throws InterruptedException {
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetection().setVisible(true);
            }
        });
    }
}
