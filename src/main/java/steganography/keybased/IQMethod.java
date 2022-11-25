package steganography.keybased;

import steganography.interfaces.KeyBasedSteganography;
import steganography.interfaces.KeyLessSteganography;
import untility.RGBArray;
import untility.operations.BitsOperations;
import untility.operations.RandomOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * The {@code IQMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an IQ approach (Image Quantization Method).
 *
 * <p>
 * This class uses pseudorandom key to perform steganographic operations.
 *
 * <p>
 * This class implements the {@link KeyBasedSteganography KeyBasedSteganography} interface.
 *
 * @see KeyBasedSteganography
 */
public class IQMethod implements KeyBasedSteganography {


    @Override
    public int[] generateKey(BufferedImage image, String message) {
        int[] key = new int[255 * 2];
        for (int i = 0; i < key.length; i++) {
            key[i] = RandomOperations.getRandomIntInBounds(0,255) % 2;
        }
        return key;
    }

    @Override
    public void packMessage(String message, int[] key, BufferedImage image, File outputFile) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            int div = blue[y][0] - blue[y][1];
            if((counter/8) < byteArray.length){
                if(key[div + 255] != BitsOperations.getAtPosition(byteArray[counter/8], counter%8)){
                    int x = 1;
                    while ((key[div + 255 + x] != BitsOperations.getAtPosition(byteArray[counter/8], counter%8)) && (x < 509)){
                        x++;
                    }
                    blue[y][0] = blue[y][0] + x;
                }
            }
            counter++;
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(outputFile);
    }

    @Override
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[image.getWidth()/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            int div = blue[y][0] - blue[y][1];
            byteArray[y/8] = (byte) BitsOperations.modifyAtPosition(byteArray[y/8],counter%8, key[div+255]);
            counter++;
        }
        return new String(byteArray, "ASCII");
    }
}

// System.out.println("key" + Arrays.toString(key));
// System.out.println("key[div + 255] = " + key[div + 255]);
// blue[y][0] = BitsOperations.flipAtPosition(blue[y][0], 0);
// System.out.println("div = " + div);
// System.out.println("While = " + x);
