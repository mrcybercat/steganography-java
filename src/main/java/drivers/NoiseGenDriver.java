package drivers;

import org.apache.commons.io.FileUtils;
import untility.ExperimentResult;
import untility.RGBArray;
import untility.noises.FractalNoise;
import untility.noises.GaussianNoise;
import untility.operations.FileOperations;
import untility.operations.RandomOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoiseGenDriver {

    public static void main(String[] args) throws IOException {
        File experimentDirectory = new File("src\\main\\resources");
        File outputFileGau = new File("src\\main\\resources\\OutputRGau.png");
        File outputFileGauGray = new File("src\\main\\resources\\OutputRGauGray.png");
        File outputFileFrac = new File("src\\main\\resources\\OutputRFrac.png");
        File outputFileFracGray = new File("src\\main\\resources\\OutputRFracGray.png");

        String[] extensions = new String[] { "bmp", "png" };
//    List<File> pictures = (List<File>) FileUtils.listFiles(experimentDirectory, extensions, true);
//    List<ExperimentResult> experiments = new ArrayList<>();
        RGBArray[] rgbArraysGau = RandomOperations.generateRandomRGBArray(1080,1920, new GaussianNoise());
        rgbArraysGau[0].saveImageFromRGBArray(outputFileGau, "png");
        rgbArraysGau[1].saveImageFromRGBArray(outputFileGauGray, "png");

        RGBArray[] rgbArraysFrac = RandomOperations.generateRandomRGBArray(1080,1920, new GaussianNoise());
        rgbArraysFrac[0].saveImageFromRGBArray(outputFileFrac, "png");
        rgbArraysFrac[1].saveImageFromRGBArray(outputFileFracGray, "png");
    }
}
