package steganography.interfaces;

import steganography.keybased.PRPMethod;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface GenericSteganography {

    public int[] generateKey(Object method, Object... args);
    public void packMessage(Object method, Object... args) throws IOException;
    public String unpackMessage(Object method, Object... args) throws IOException;
}
