package semester.projekt.core;

/*
*
*   IMPORTS!
*
*/

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class EdgeDetector extends Lumi {

    private String filename;
    private RenderedImage getImage;

    private int truncate(int a) {
        if (a < 127) { 
            return 0;  
        } else if (a >= 255) {
            return 255; 
        } else {
            return a; 
        }
    }


//    private int binaryTruncate(int a) {
//        if (a < 127) { 
//            return 0;
//        } else {
//            return 255;
//        }
//    }

    public BufferedImage getBufferedImage() {

        int[][] filter1 = { 
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] filter2 = { 
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
        };

        
        Picture picture0 = new Picture(getFilename());
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        Picture picture1 = new Picture(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

               
                int[][] gray = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray[i][j] = (int) Lumi.intensity(picture0.get(x + i, y + j));
                    }
                }

                // apply filter
                int gray1 = 0, gray2 = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        gray1 += gray[i][j] * filter1[i][j];
                        gray2 += gray[i][j] * filter2[i][j];
                    }
                }
                // int magnitude = 255 - truncate(Math.abs(gray1) + Math.abs(gray2));
                int magnitude = 255 - truncate((int) Math.sqrt(gray1 * gray1 + gray2 * gray2));
                Color grayscale = new Color(magnitude, magnitude, magnitude);
                picture1.set(x, y, grayscale);
            }
        }

        return picture1.getImage();
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the getImage
     */
    public RenderedImage getGetImage() {
        return getImage;
    }

    /**
     * @param getImage the getImage to set
     */
    public void setGetImage(RenderedImage getImage) {
        this.getImage = getImage;
    }

}
