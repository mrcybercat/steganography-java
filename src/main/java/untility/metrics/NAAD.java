package untility.metrics;

import untility.RGBArray;

public class NAAD extends Metric {
    public static double calculateNAAD(RGBArray rgbArray1, RGBArray rgbArray2){
        double numerator = 0;
        double denominator = 0;
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                numerator +=  Math.abs(calculateGray(rgbArray1, x, y) - calculateGray(rgbArray2, x, y));
            }
        }
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                denominator +=  Math.abs(calculateGray(rgbArray1, x, y));
            }
        }
        return numerator / denominator;
    }


}
