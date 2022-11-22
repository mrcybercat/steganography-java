package steganography;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface KeyLessSteganography {
    public void packMessage(String message, BufferedImage image, String newFilePath) throws IOException;
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException;
}
