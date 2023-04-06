package drivers;

import untility.operations.RandomOperations;

import java.io.File;
import java.io.IOException;

public class ColorGenDriver {

    public static void main(String[] args) throws IOException {
        File experimentDirectory = new File("src\\main\\resources");
        File outputRandomColor1 = new File("src\\main\\resources\\OutputRandomColor1.png");
        File outputRandomColor2 = new File("src\\main\\resources\\OutputRandomColor2.png");

        String[] extensions = new String[] { "bmp", "png" };

        RandomOperations.generateMonotoneRGBArray(1080,1920).saveImageFromRGBArray(outputRandomColor1, "png");
        RandomOperations.generateMonotoneRGBArray(1080,1920).saveImageFromRGBArray(outputRandomColor2, "png");




    }
}
