package steganography.keybased;

import steganography.interfaces.KeyBasedSteganography;
import steganography.interfaces.KeyLessSteganography;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
 * @see KeyLessSteganography
 */
public class IQMethod implements KeyBasedSteganography {


    @Override
    public int[] generateKey(BufferedImage image) {
        return new int[0];
    }

    @Override
    public void packMessage(String message, int[] key, BufferedImage image, String newFilePath) throws IOException {

    }

    @Override
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException {
        return null;
    }
}
