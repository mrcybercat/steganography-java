package steganography.interfaces;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This interface imposes a basic and vital methods needed for
 * the steganography without a key
 */
public interface KeyLessSteganography {
    public void packMessage(String message, BufferedImage image, String newFilePath) throws IOException;
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException;
}
