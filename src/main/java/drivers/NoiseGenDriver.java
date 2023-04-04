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
        File outputFileG = new File("src\\main\\resources\\OutputRGau.bmp");
        File outputFileF = new File("src\\main\\resources\\OutputRFrac.bmp");

        String[] extensions = new String[] { "bmp", "png" };
//    List<File> pictures = (List<File>) FileUtils.listFiles(experimentDirectory, extensions, true);
//    List<ExperimentResult> experiments = new ArrayList<>();
        FractalNoise noiseF = new FractalNoise();
        GaussianNoise noiseG = new GaussianNoise();
        for (int i = 0; i < 20; i++) {
            System.out.print("FractalNoise" + noiseF.nextRGBValue()  + "\n");
            System.out.print("Gaussian" + noiseG.nextRGBValue()  + "\n");
        }
        RandomOperations.generateRandomRGBArray(1080,1920, new GaussianNoise()).saveImageFromRGBArray(outputFileG, "png");
        RandomOperations.generateRandomRGBArray(1080,1920, new FractalNoise()).saveImageFromRGBArray(outputFileF,"png");
    }
}
