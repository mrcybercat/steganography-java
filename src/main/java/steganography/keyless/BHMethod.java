package steganography.keyless;

import steganography.Method;
import steganography.interfaces.KeyLessSteganography;
import steganography.interfaces.SteganographyMethod;
import untility.operations.BitsOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * The {@code BHMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an BH approach (Block Hiding Method).
 *
 * <p>
 * This class implements the {@link KeyLessSteganography KeyLessSteganography} interface.
 *
 * @see KeyLessSteganography
 */
public class BHMethod extends Method implements KeyLessSteganography, SteganographyMethod {

    @Override
    public void packMessage(String message, BufferedImage image, File outputFile, String extension) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            int sum = 0;
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                sum += blue[y][x];
                // System.out.println("sum: " + sum + " - x " + x);
            }
            sum = sum % 2;
            // System.out.println("sum: " + sum);
            if((counter/8) < byteArray.length){
                if(sum != BitsOperations.getAtPosition(byteArray[counter/8], counter%8)){
                    blue[y][0] = BitsOperations.flipAtPosition(blue[y][0], 0);
                }
            }
            counter++;
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(outputFile, extension);
    }

    @Override
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[image.getWidth()/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            int sum = 0;
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                sum += blue[y][x];
            }
            sum = sum % 2;

            byteArray[y/8]= (byte) BitsOperations.modifyAtPosition(byteArray[y/8],counter%8, sum);
            counter++;
        }
        return new String(byteArray, "ASCII");
    }

    @Override
    public Integer getMaxPayload(BufferedImage container) {
        return container.getWidth();
    }


    @Override
    public String getName() {
        return "BHMethod";
    }
}