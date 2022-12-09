package steganography.interfaces;

import java.io.IOException;

public interface SteganographyMethod {
    public String getName();
    public int[] generateKey(Object method, Object... args);
    public void packMessage(Object method, Object... args) throws IOException;
    public String unpackMessage(Object method, Object... args) throws IOException;
}
