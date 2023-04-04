package untility.metrics;

import untility.RGBArray;

public class SSIM extends Metric {

    public static double calculateSSIM(RGBArray originalImage, RGBArray stegoImage) {
        final double K1 = 0.01; // constants
        final double K2 = 0.03; // constants
        final int L = 255; // dynamic range of pixel values
        final double C1 = (K1 * L) * (K1 * L); // variables to stabilize division with weak denominator
        final double C2 = (K2 * L) * (K2 * L); // variables to stabilize division with weak denominator

        double origMean = calculateMean(originalImage);
        double stegoMean = calculateMean(stegoImage);
        double originVar = calculateVariance(originalImage, origMean);
        double stegoVar = calculateVariance(stegoImage, stegoMean);
        double crossCovariance = calculateCrossCovariance(originalImage, stegoImage, origMean, stegoMean);

        double numerator = (2 * origMean * stegoMean + C1) * (2 * crossCovariance + C2);
        double denominator = (origMean * origMean + stegoMean * stegoMean + C1) * (originVar + stegoVar + C2);

        return numerator / denominator;
    }
}