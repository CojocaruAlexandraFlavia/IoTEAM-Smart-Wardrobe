package com.example.smartwardrobe.selfie_webcam;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Webcam {
    public static void main(String[] args) throws IOException {
        com.github.sarxos.webcam.Webcam webcam = com.github.sarxos.webcam.Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "JPG", new File("firstcapture.jpg"));
        webcam.close();
    }
}
