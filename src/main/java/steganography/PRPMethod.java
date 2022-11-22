package steganography;

import untility.BitsOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;


/**
 * The {@code PRPMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an PRP approach (Pseudo Random Permutation).
 *
 * <p>
 * This class uses pseudorandom key to perform steganographic operations.
 *
 * <p>
 * This class implements the {@link steganography.KeyBasedSteganography KeyBasedSteganography} interface.
 *
 * @see KeyLessSteganography
 */
public class PRPMethod implements KeyBasedSteganography {
    @Override
    public int[] generateKey(BufferedImage image) {
        int[] key = new int[image.getWidth()];

        for (int i = 0; i < key.length; i++) {
            key[i] = i;
        }

        Collections.shuffle(List.of(key));
        return key;
    }

    @Override
    public void packMessage(String message, int[] key, BufferedImage image, String newFilePath) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            if((counter/8) < byteArray.length){
                blue[y][key[y]] = BitsOperations.modifyAtPosition(blue[y][key[y]], 0, BitsOperations.getAtPosition(byteArray[counter/8], counter%8));
            }
            counter++;
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(newFilePath);
    }

    @Override
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[image.getWidth()/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            byteArray[y/8]  = (byte) BitsOperations.modifyAtPosition(byteArray[y/8],counter%8, BitsOperations.getAtPosition((byte) blue[y][key[y]], 0));
            counter++;
        }
        return new String(byteArray, "ASCII");
    }
}
// System.out.println(Arrays.toString(key));
