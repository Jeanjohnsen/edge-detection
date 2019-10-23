/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semester.projekt.starter;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import semester.projekt.core.EdgeDetector;

/**
 *
 * @author geniu
 */
public class Starter {
        public static void main(String[] args) throws IOException {
        System.out.println("Started");
        System.out.println("Enter the file path and name with file type :");

        semester.projekt.core.EdgeDetector con = new semester.projekt.core.EdgeDetector();

        File outputfile = new File("sobel.png");
        ImageIO.write(con.getBufferedImage(), "png", outputfile);

        System.out.println("max : " + con.getClass());
        System.out.println("Finished");

    }
    
}
