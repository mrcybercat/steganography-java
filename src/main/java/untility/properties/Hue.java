package untility.properties;

import untility.RGBArray;

public class Hue {

    public static double calculateHue(RGBArray rgbArray) {
        double hue = 0;
        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                hue += Math.acos((0.5 * (rgbArray.getRed()[y][x] - rgbArray.getGreen()[y][x] -  rgbArray.getBlue()[y][x]))
                        / Math.sqrt(Math.pow(rgbArray.getRed()[y][x] - rgbArray.getGreen()[y][x] , 2)
                        - (rgbArray.getRed()[y][x] - rgbArray.getBlue()[y][x]) * (rgbArray.getGreen()[y][x] - rgbArray.getBlue()[y][x])));
            }
        }
        hue = hue / (double) (rgbArray.getBlue().length * rgbArray.getBlue()[0].length);
        return hue;

    }
}
