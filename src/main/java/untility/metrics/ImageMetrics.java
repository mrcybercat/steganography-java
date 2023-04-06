package untility.metrics;

import untility.RGBArray;

public class ImageMetrics {
    RGBArray originalImage;
    RGBArray stegoImage;

    public ImageMetrics(RGBArray originalImage, RGBArray stegoImage) {
        this.originalImage = originalImage;
        this.stegoImage = stegoImage;
    }

    public double getUIQI(){
        return UIQI.calculateUIQI(originalImage, stegoImage);
    }

    public double getSSIM(){
        return SSIM.calculateSSIM(originalImage, stegoImage);
    }

    public double getPSNR(){
        return PSNR.calculatePSNR(originalImage, stegoImage);
    }
    public double getMSE(){
        return MSE.calculateMSE(originalImage, stegoImage);
    }
    public double getNAAD(){
        return NAAD.calculateNAAD(originalImage, stegoImage);
    }
    public double getNCC(){
        return NCC.calculateNCC(originalImage, stegoImage);
    }
    public double getCQ(){
        return CQ.calculateCQ(originalImage, stegoImage);
    }
}


/*
  public static double meanValue(BufferedImage image) {
    Raster raster = image.getRaster();
    double sum = 0.0;

    for (int y = 0; y < image.getHeight(); ++y){
      for (int x = 0; x < image.getWidth(); ++x){
        sum += raster.getSample(x, y, 0);
      }
    }
    return sum / (image.getWidth() * image.getHeight());* */
