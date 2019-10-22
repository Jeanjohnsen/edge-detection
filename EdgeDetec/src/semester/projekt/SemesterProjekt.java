/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semester.projekt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author Yusuf
 */
public class SemesterProjekt {

    public static void main(String[] args) throws IOException {
        System.out.println("Started");
        System.out.println("Enter the file path and name with file type :");

        EdgeDetector con = new EdgeDetector();

        File outputfile = new File("sobel.png");
        ImageIO.write(con.getBufferedImage(), "png", outputfile);

        System.out.println("max : " + con.getClass());
        System.out.println("Finished");

    }

}
