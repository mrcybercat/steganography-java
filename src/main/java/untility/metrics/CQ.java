package untility.metrics;

import untility.RGBArray;

public class CQ extends Metric {

    public static double calculateCQ(RGBArray rgbArray1, RGBArray rgbArray2){
        double numerator = 0;
        double denominator = 0;
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                numerator +=  calculateGray(rgbArray1, x, y) * calculateGray(rgbArray2, x, y);
            }
        }
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                denominator += calculateGray(rgbArray1, x, y);
            }
        }
        return numerator / denominator;
    }

}
