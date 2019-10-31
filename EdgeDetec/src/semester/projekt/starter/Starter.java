/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semester.projekt.starter;

import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import semester.projekt.core.EdgeDetector;

/**
 *
 * @author geniu
 */
public class Starter 
{
        public static void main(String[] args) throws IOException, Exception 
        {
        System.out.println("Started");
        System.out.println("Enter the file path and name with file type :");

        semester.projekt.core.EdgeDetector con = new semester.projekt.core.EdgeDetector();

        File file = new File("sobel.png");
        ImageIO.write(con.getBufferedImage(), "png", file);

        System.out.println("max : " + con.getClass());
        System.out.println("Finished");
        
        String encodestring = encodeFile(file);
        System.out.println(encodestring);

        }
        
        private static String encodeFile(File file) throws Exception
        {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = Files.readAllBytes(file.toPath());
        fileInputStreamReader.read(bytes);
        
        return new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }
}
