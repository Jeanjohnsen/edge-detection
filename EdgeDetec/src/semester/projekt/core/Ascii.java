/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semester.projekt.core;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author geniu
 */
public class Ascii {
        boolean neg;
        double gradient;
        
    public Ascii(double gradient)
    {
        this(false);
        this.gradient = gradient;
    }
    
    public  Ascii(final boolean neg)
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
        
        if (gs >= gradient)
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
        
        if (gs >= gradient)
        {
            s = '@';
        }
        else 
        {
            s = ' ';
        }
        
        return s;
    }
}
