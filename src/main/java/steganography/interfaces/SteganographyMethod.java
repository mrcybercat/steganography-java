package steganography.interfaces;

import java.io.File;
import java.io.IOException;

public interface SteganographyMethod {
    public String getName();
    public int[] generateKey(Object method, Object... args);
    public void packMessage(Object method, String message, File inputFile, File outputFile) throws IOException;
    public String unpackMessage(Object method, Object... args) throws IOException;
}
