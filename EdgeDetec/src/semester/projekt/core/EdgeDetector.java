package semester.projekt.core;

/*
*
*   IMPORTS!
*
*/

import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;

public class EdgeDetector extends Lumi {

    Scanner ne1 = new Scanner(System.in);
    String filename = ne1.nextLine();
    RenderedImage getImage;

    /**
     *
     * @param imagePath Path of the image to perform edgedetection on.
     */
    
    
    // Angiver hvor meget pixlene er tilstede. 0 = ikke tilstede, 255 = helt tilstede. 
    private int truncate(int a) {
        if (a < 127) { // a= transperency altså gennemsigtigheden for pixlen. 
            return 0; // Hvis pixlene er mindre end halv tilstede, så er pixlen der ikke. 
        } else if (a >= 255) {
            return 255; // Hvis pixlene er helt til stede vil pixlen være meget mørk. 
        } else {
            return a; // Hvis pixlen er større end halvdelen vil den være tilstede men være meget lav. Eg. hvis den er = 129 vil pixlen være meget svag. 
        }
    }

    // a = transperency (gennemsigtigheden) 
    private int binaryTruncate(int a) {
        if (a < 127) { // Gennemsigtigheden er der enten hel eller eller slet et. Dette er kun nødvendigt hvis vi vælger at bruge denne her metode i stedet for metoden ovenover.
            return 0;
        } else {
            return 255;
        }
    }

    /**
     * This method performs edge-detection of the image on the path provided to
     * the constructor, and returns a BufferedImage representation of the
     * result.
     *
     * @return A BufferedImage-object representation of the image provided.
     */
    public BufferedImage getBufferedImage() {

        int[][] filter1 = { // Sobel på x-aksen i edge 
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] filter2 = { // Sobel på y-aksen i edge 
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
        };

        // Picture0  hvor 0.0 er ud i kanten på papiret og hvor picture1 er med en kant inde i papiret. 
        Picture picture0 = new Picture(filename);
        int width = picture0.width() - 2;
        int height = picture0.height() - 2;
        Picture picture1 = new Picture(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // get 3-by-3 array of colors in neighborhood
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

}
