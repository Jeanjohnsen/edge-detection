package semester.projekt.core;

/*
*
*   IMPORTS!
*
*/

import java.awt.Color;
import java.io.File;

public class Lumi {

    
    public static double lum(Color color) {
        return intensity(color);
    }

    public static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return 0.299 * r + 0.587 * g + 0.114 * b;

    }

    // return a gray version of this Color
    public static Color toGray(Color color) {
        int y = (int) (Math.round(lum(color)));   // round to nearest int
        Color gray = new Color(y, y, y);
        return gray;
    }

    // are the two colors compatible?
    public static boolean areCompatible(Color a, Color b) {
        return Math.abs(intensity(a) - intensity(b)) >= 128.0;
    }

}
