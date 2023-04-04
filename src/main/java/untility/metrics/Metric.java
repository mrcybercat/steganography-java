package untility.metrics;

import untility.RGBArray;

abstract public class Metric {
    public static double calculateMean(RGBArray rgbArray) {
        double mean = 0;
        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                mean += calculateGray(rgbArray, x, y);
            }
        }
        mean = mean / (rgbArray.getBlue().length * rgbArray.getBlue()[0].length);
        return mean;
    }

    public static double calculateVariance(RGBArray rgbArray, double mean){
        double std = 0;
        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                std += Math.pow(calculateGray(rgbArray, x, y) - mean, 2);
            }
        }
        std = std / (double) (rgbArray.getBlue().length * rgbArray.getBlue()[0].length - 1);
        return std;
    }

    public static double calculateCrossCovariance(RGBArray rgbArray1, RGBArray rgbArray2, double mean1, double mean2){
        double crossCovariance = 0;
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                crossCovariance +=  (calculateGray(rgbArray1, x, y) - mean1) * (calculateGray(rgbArray2, x, y) - mean2);;
            }
        }
        crossCovariance = crossCovariance / (double) (rgbArray1.getBlue().length * rgbArray1.getBlue()[0].length - 1);
        return crossCovariance;
    }

    public static double calculateMSE(RGBArray rgbArray1, RGBArray rgbArray2){
        double mse = 0;
        for (int y = 0; y < rgbArray1.getBlue().length; y++) {
            for (int x = 0; x < rgbArray1.getBlue()[0].length; x++) {
                mse +=  Math.pow((calculateGray(rgbArray1, x, y) - calculateGray(rgbArray2, x, y)), 2);
            }
        }
        mse = mse / (rgbArray1.getBlue().length * rgbArray1.getBlue()[0].length);
        return mse;
    }

    public static double calculatePixelAverage1(RGBArray rgbArray, int x, int y){
        return (rgbArray.getRed()[y][x] + rgbArray.getGreen()[y][x] + rgbArray.getBlue()[y][x]) / 3.0;
    }

    public static double calculateGray(RGBArray rgbArray, int x, int y){
        return (0.2989 * rgbArray.getRed()[y][x] +0.5870  * rgbArray.getGreen()[y][x] + 0.1140  * rgbArray.getBlue()[y][x]);
    }

}
