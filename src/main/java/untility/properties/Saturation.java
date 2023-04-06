package untility.properties;

import untility.RGBArray;

public class Saturation {
    public static double calculateSaturation(RGBArray rgbArray) {
        double saturation = 0;
        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                double max =  Math.max(rgbArray.getRed()[y][x], Math.max(rgbArray.getGreen()[y][x], rgbArray.getBlue()[y][x]));
                double min =  Math.min(rgbArray.getRed()[y][x], Math.min(rgbArray.getGreen()[y][x], rgbArray.getBlue()[y][x]));
                saturation += (max - min)/ max;
            }
        }
        saturation = saturation / (double) (rgbArray.getBlue().length * rgbArray.getBlue()[0].length);
        return saturation;
    }
}
