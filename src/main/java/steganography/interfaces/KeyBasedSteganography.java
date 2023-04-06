package steganography.interfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This interface imposes a basic and vital methods needed for
 * the key based steganography
 */
public interface KeyBasedSteganography {
    public int[] generateKey(BufferedImage image, String message);
    public void packMessage(String message, BufferedImage image, File outputFile, String extension) throws IOException;
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException;
    public Integer getMaxPayload(BufferedImage container);

}

