package untility.metrics;


import untility.RGBArray;

public class UIQI extends Metric {

    public static double calculateUIQI(RGBArray originalImage, RGBArray stegoImage) {
        double origMean = calculateMean(originalImage);
        double stegoMean = calculateMean(stegoImage);
        double originVar = calculateVariance(originalImage, origMean);
        double stegoVar = calculateVariance(stegoImage, stegoMean);
        double crossCovariance = calculateCrossCovariance(stegoImage, originalImage, stegoMean, origMean);
        return (4 * crossCovariance * origMean * stegoMean) / ((originVar + stegoVar) * (origMean * origMean + stegoMean * stegoMean));
    }
}

