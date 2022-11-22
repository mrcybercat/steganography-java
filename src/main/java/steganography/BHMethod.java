package steganography;

import untility.BitsOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class BHMethod implements KeyLessSteganography{
    @Override
    public void packMessage(String message, BufferedImage image, String newFilePath) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            int sum = 0;
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                sum += sum + blue[y][x];
            }
            sum = sum % 2;

            if(sum != BitsOperations.getAtPosition(byteArray[counter/8], counter%8)){
                blue[y][0] = BitsOperations.flipAtPosition(blue[y][0], 0);
            }
            counter++;
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(newFilePath);
    }

    @Override
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
        return null;
    }
}
