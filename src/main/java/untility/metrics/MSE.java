package untility.metrics;

import untility.RGBArray;

public class MSE extends Metric{
    public static double calculatePSNR(RGBArray originalImage, RGBArray stegoImage) {
        double mse = calculateMSE(originalImage, stegoImage);
        return mse;
    }
}
