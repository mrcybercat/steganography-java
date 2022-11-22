package steganography.keyless;

import steganography.interfaces.KeyLessSteganography;
import untility.RGBArray;
import untility.operations.BitsOperations;

import java.awt.image.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * The {@code LSBMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an LSB approach (Least Significant Bit).
 *
 * <p>
 * This class implements the {@link KeyLessSteganography KeyLessSteganography} interface.
 *
 * @see KeyLessSteganography
 */
public class LSBMethod implements KeyLessSteganography {
    @Override
    public void packMessage(String message, BufferedImage image, String newFilePath) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                if((counter/8) < byteArray.length){
                    blue[y][x] = BitsOperations.modifyAtPosition(blue[y][x], 0, BitsOperations.getAtPosition(byteArray[counter/8], counter%8));
                }
                counter++;
            }
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(newFilePath);
    }

    @Override
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[(image.getWidth() * image.getHeight())/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                byteArray[(rgbArray.getBlue()[0].length * y + x)/8] = (byte) BitsOperations.modifyAtPosition(byteArray[(rgbArray.getBlue()[0].length * y + x)/8],counter%8, BitsOperations.getAtPosition((byte) blue[y][x], 0));
                counter++;
            }
        }
        return new String(byteArray, "ASCII");
    }
}


