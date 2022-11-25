import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steganography.keybased.IQMethod;
import steganography.keybased.PRPMethod;
import steganography.keybased.PRIMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.KJBMethod;
import steganography.keyless.LSBMethod;
import untility.operations.FileOperations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteganographyTest {
    File picFile = new File("src\\main\\resources\\Input.bmp");
    File stegoFile = new File("src\\main\\resources\\Test_Output.bmp");
    String testMessage = "Hi, do you";
    String endMessageMarker = "EnD_mes_1!";
    BufferedImage image = null;

    @BeforeEach
    void setUp() {
        try {
            image = ImageIO.read(picFile); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void LSBMethodTest() throws IOException {
        LSBMethod lsbMethod = new LSBMethod();

        lsbMethod.packMessage(testMessage + endMessageMarker, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, lsbMethod.unpackMessage(imgContainer).split(endMessageMarker)[0], "Packed and unpacked message should be the same");
    }

    @Test
    void PRIMethodTest() throws IOException {
        PRPMethod prpMethod = new PRPMethod();

        int[] keyPRI = prpMethod.generateKey(image, testMessage  + endMessageMarker);
        prpMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, prpMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0],  "Packed and unpacked message should be the same");
    }

    @Test
    void PRPMethodTest() throws IOException {
        PRIMethod priMethod = new PRIMethod();

        int[] keyPRI = priMethod.generateKey(image, testMessage  + endMessageMarker);
        priMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, priMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0], "Packed and unpacked message should be the same");
    }

    @Test
    void BHMethodTest() throws IOException {
        BHMethod bhMethod = new BHMethod();

        bhMethod.packMessage(testMessage + endMessageMarker, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, bhMethod.unpackMessage(imgContainer).split(endMessageMarker)[0], "Packed and unpacked message should be the same");
    }

    @Test
    void IQMethodTest() throws IOException {
        IQMethod iqMethod = new IQMethod();

        int[] keyPRI = iqMethod.generateKey(image, testMessage  + endMessageMarker);
        iqMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, iqMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0], "Packed and unpacked message should be the same");
    }

    @Test
    void KJBMethodTest() throws IOException {
        KJBMethod kjbMethod = new KJBMethod();

        kjbMethod.packMessage(testMessage + endMessageMarker, image, stegoFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoFile);
        assertEquals(testMessage, kjbMethod.unpackMessage(imgContainer).split(endMessageMarker)[0], "Packed and unpacked message should be the same");
    }
}
