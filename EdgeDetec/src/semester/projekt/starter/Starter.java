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
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  Class is final because we do not want to extend it.
 */
public final class Starter 
{
    
    boolean neg;
    
    public Starter()
    {
        this(false);
    }
    
    public  Starter(final boolean neg)
    {
        this.neg = neg;
    }
    
    /*
    *   This Method converts a arbitrary picture into ascii characters
    *
    *   The conditional operator and how does it work:
    *   https://stackoverflow.com/questions/798545/what-is-the-java-operator-called-and-what-does-it-do
    *
    *   @param img BufferedImage
    *   @return String
    */   
    
    public String conv(final BufferedImage img)
    {
        StringBuilder sb = new StringBuilder((img.getWidth() + 1) * img.getHeight());
        
        for(int j = 0; j < img.getHeight(); j++) 
        {
            
                //  If sb reaches the side of the picture
                //  then make a new line
            if (sb.length() != 0) sb.append("\n");
            
            for (int i = 0; i < img.getWidth(); i++) 
            {
                Color pc = new Color(img.getRGB(i, j));
                double gv = (double)pc.getRed()* 0.2989 + (double)pc.getBlue() * 0.5870 + (double)pc.getGreen() * 0.1140;
                final char as = neg ? rStrN(gv) : rStrP(gv);
                sb.append(as);
            }
            
        }
        
        return sb.toString();
    } 
    
    /*
    *   Creates a string and assigns it to a new one based on gs value
    *   
    *   @param gs
    *   @return char
    */
    
    private char rStrP(double gs)
    {
        final char s;
        
        if (gs >= 55.0)
        {
            s = ' ';
        }
        else 
        {
            s = '@';
        }
        
        return s;
    }
    
    private char rStrN(double gs)
    {
            final char s;
        
        if (gs >= 55.0)
        {
            s = '@';
        }
        else 
        {
            s = ' ';
        }
        
        return s;
    }
    
    
    public static void main(String[] args) throws IOException, Exception 
    {
        //  Object of the EdgeDetector Class
        semester.projekt.core.EdgeDetector con = new semester.projekt.core.EdgeDetector();

        // __INIT__ GUI
        SwingUtilities.invokeLater(() -> {
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "gif", "png"));
            while (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File f = fileChooser.getSelectedFile();
                    final BufferedImage image = ImageIO.read(f);
                    if (image == null) {
                        throw new IllegalArgumentException(f + " is not a valid image.");
                    }
                    ImageIO.write(con.getBufferedImage(), "png", f);
                    final String ascii = new Starter().conv(image);
                    final JTextArea textArea = new JTextArea(ascii, image.getHeight(), image.getWidth());
                    textArea.setFont(new Font("Monospaced", Font.BOLD, 5));
                    textArea.setEditable(false);
                    final JDialog dialog = new JOptionPane(new JScrollPane(textArea), JOptionPane.PLAIN_MESSAGE).createDialog(Starter.class.getName());
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            System.exit(0);
        
        });

        //System.out.println("max : " + con.getClass());
        //System.out.println("Finished");

        //Encoding
        //String encodestring = encodeFile(file);
        //System.out.println("ENCODED STRING :");
        //System.out.println(encodestring);

    }
    
        /*
    *   This method takes the "sobel" file created by the main method
    *   and encodes it to a Base64 string https://da.wikipedia.org/wiki/Base64
    */
    
    /*
    private static String encodeFile(File file) throws Exception {
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = Files.readAllBytes(file.toPath());
        fileInputStreamReader.read(bytes);

        return new String(Base64.getEncoder().encode(bytes), "UTF-8");
    }
    */
    
}
