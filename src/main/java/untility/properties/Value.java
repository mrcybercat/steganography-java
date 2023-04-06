package untility.properties;

import untility.RGBArray;

public class Value {
    public static double calculateValue(RGBArray rgbArray) {
        double value = 0;
        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                value += Math.max(rgbArray.getRed()[y][x], Math.max(rgbArray.getGreen()[y][x], rgbArray.getBlue()[y][x]));
            }
        }
        value = value / (double) (rgbArray.getBlue().length * rgbArray.getBlue()[0].length);
        return value;
    }
}
