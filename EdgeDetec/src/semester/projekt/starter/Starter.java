package semester.projekt.starter;

/*
*
*   IMPORTS!
*
*/

import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import semester.projekt.core.EdgeDetector;

/**
 *  
 * 
 */
/*public class Starter 
{
        
        public static void main(String[] args) throws IOException, Exception 
        {
            
        // __INIT__ Code
        System.out.println("Started");
        System.out.println("Enter the file path and name with file type :");
        
        //Object of the EdgeDetector Class
        semester.projekt.core.EdgeDetector con = new semester.projekt.core.EdgeDetector();
        
        //Creates a grayscale version of the picture
        File file = new File("sobel.png");
        ImageIO.write(con.getBufferedImage(), "png", file);
        
        System.out.println("max : " + con.getClass());
        System.out.println("Finished");
        
        //Encoding
        String encodestring = encodeFile(file);
        System.out.println("ENCODED STRING :");
        System.out.println(encodestring);
        
        }
        
        /*
        *   This method takes the "sobel" file created by the main method
        *   and encodes it to a Base64 string https://da.wikipedia.org/wiki/Base64
        */
/*
        private static String encodeFile(File file) throws Exception
        {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = Files.readAllBytes(file.toPath());
        fileInputStreamReader.read(bytes);
        
        return new String(Base64.getEncoder().encode(bytes), "UTF-8");
        }
}
*/