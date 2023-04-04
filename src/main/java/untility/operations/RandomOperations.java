package untility.operations;

import untility.RGBArray;
import untility.noises.NoiseGenerator;

import java.util.Random;

/**
 * The {@code RandomOperations} is an utility class that provides methods
 * for random operations.
 */
public final class RandomOperations {
    public static int getRandomIntInBounds(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
        //random.nextGaussian();
    }

    public static RGBArray generateRandomRGBArray(int height, int width, NoiseGenerator noise){
        RGBArray rgbArray = new RGBArray(height, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgbArray.getRed()[y][x] = noise.nextRGBValue();
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgbArray.getGreen()[y][x] = noise.nextRGBValue();
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgbArray.getBlue()[y][x] = noise.nextRGBValue();
            }
        }
        return rgbArray;
    }

    public static RGBArray generateMonotoneRGBAArray(int height, int width){
        RGBArray rgbArray = new RGBArray(height, width);
        int red = RandomOperations.getRandomIntInBounds(0, 255);
        int green = RandomOperations.getRandomIntInBounds(0, 255);
        int blue = RandomOperations.getRandomIntInBounds(0, 255);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rgbArray.getRed()[y][x] = red;
                rgbArray.getGreen()[y][x] = green;
                rgbArray.getBlue()[y][x] = blue;
            }
        }
        return rgbArray;
    }


}
