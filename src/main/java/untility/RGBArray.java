package untility;

import untility.operations.RandomOperations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * The {@code RGBArray} is an utility class that provides an data type for storing RGB values of images in form
 * of matrices of integers <i>(each color separately)</i>.
 *
 * <p>
 * Contains methods to perform {@link RGBArray#imageToRGBArray(BufferedImage) reading from },
 * and {@link RGBArray#saveImageFromRGBArray(File) writing to images}
 *
 */
public class RGBArray{
    private int[][] red;
    private int[][] green;
    private int[][] blue;

    public RGBArray(){}


    /**
     * Creates a new RGBArray
     * @param red  A matrix of red color values
     * @param green  A matrix of green color values
     * @param blue  A matrix of blue color values
     */
    public RGBArray(int[][] red, int[][] green, int[][] blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int[][] getRed() {
        return red;
    }

    public void setRed(int[][] red) {
        this.red = red;
    }

    public int[][] getGreen() {
        return green;
    }

    public void setGreen(int[][] green) {
        this.green = green;
    }

    public int[][] getBlue() {
        return blue;
    }

    public void setBlue(int[][] blue) {
        this.blue = blue;
    }

    public void imageToRGBArray(BufferedImage image){
        this.red = new int[image.getHeight()][image.getWidth()];
        this.green = new int[image.getHeight()][image.getWidth()];
        this.blue = new int[image.getHeight()][image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //                  2          1          0
                //    bitpos     32109876 54321098 76543210
                //    ------   --+--------+--------+--------+
                //    bits     ..|RRRRRRRR|GGGGGGGG|BBBBBBBB|
                this.red[y][x] = (image.getRGB(x, y) >> 16) & 0xff;
                this.green[y][x] = (image.getRGB(x, y) >> 8) & 0xff;
                this.blue[y][x] = (image.getRGB(x, y)) & 0xff;
                //    System.out.println("Loop i = " + i +  " j = " + j  );
            }
        }
        //return new untility.RGBArray(red, green, blue);
    }

    public void generateRandomRGBArray(int height, int width){
        this.red = new int[height][width];
        this.green = new int[height][width];
        this.blue = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.red[y][x] = RandomOperations.getRandomIntInBounds(0,255);
                this.green[y][x] = RandomOperations.getRandomIntInBounds(0,255);
                this.blue[y][x] = RandomOperations.getRandomIntInBounds(0,255);
            }
        }
        //return new untility.RGBArray(red, green, blue);
    }

    public void saveImageFromRGBArray(File outputFile) throws IOException {
        BufferedImage image = new BufferedImage(
                this.red[0].length, this.red.length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < this.red.length; y++) {
            for (int x = 0; x < this.red[0].length; x++) {
                int rgb = this.red[y][x];
                rgb = (rgb << 8) + this.green[y][x];
                rgb = (rgb << 8) + this.blue[y][x];
                image.setRGB(x, y, rgb);
            }
        }
        //File outputFile = new File(filePath);
        ImageIO.write(image, "bmp", outputFile);
    }
}