package steganography;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This interface imposes a basic and vital methods needed for
 * the key based steganography
 */
public interface KeyBasedSteganography {
    public int[] generateKey(BufferedImage image);
    public void packMessage(String message, int[] key, BufferedImage image, String newFilePath) throws IOException;
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException;

}

