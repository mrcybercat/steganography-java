package untility.metrics;

import untility.RGBArray;

public class PSNR extends Metric {
    public static double calculatePSNR(RGBArray originalImage, RGBArray stegoImage) {
        double mse = calculateMSE(originalImage, stegoImage);
        return 10 * Math.log10((255*255) / mse);
    }
}
