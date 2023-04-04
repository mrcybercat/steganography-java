package steganography.keyless;

import steganography.Method;
import steganography.interfaces.KeyLessSteganography;
import steganography.interfaces.SteganographyMethod;
import untility.RGBArray;
import untility.operations.BitsOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class LSB8bMethod extends Method implements KeyLessSteganography, SteganographyMethod {

    @Override
    public void packMessage(String message, BufferedImage image, File outputFile, String extension) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                if ((counter / 8) < byteArray.length) {
                    blue[y][x] = BitsOperations.modifyAtPosition(blue[y][x], 7, BitsOperations.getAtPosition(byteArray[counter / 8], counter % 8));
                }
                counter++;
            }
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(outputFile, extension);
    }

    @Override
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[(image.getWidth() * image.getHeight()) / 8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                byteArray[(rgbArray.getBlue()[0].length * y + x) / 8] = (byte) BitsOperations.modifyAtPosition(byteArray[(rgbArray.getBlue()[0].length * y + x) / 8], counter % 8, BitsOperations.getAtPosition((byte) blue[y][x], 7));
                counter++;
            }
        }
        return new String(byteArray, "ASCII");
    }

    @Override
    public String getName() {
        return "LSBMethod";
    }
}

