import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import steganography.keybased.IQMethod;
import steganography.keybased.PRIMethod;
import steganography.keybased.PRPMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.LSBMethod;
import untility.FileOperations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SteganographyTest {
    String picPath = "src\\main\\resources\\Input.bmp";
    String stegoPath = "src\\main\\resources\\Test_Output.bmp";
    String testMessage = "Hi, do you";
    String endMessageMarker = "EnD_mes_1!";
    BufferedImage image = null;

    @BeforeEach
    void setUp() {
        try {
            image = ImageIO.read(new File(picPath)); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void LSBMethodTest() throws IOException {
        LSBMethod lsbMethod = new LSBMethod();

        lsbMethod.packMessage(testMessage + endMessageMarker, image, stegoPath);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoPath);
        assertEquals(lsbMethod.unpackMessage(imgContainer).split(endMessageMarker)[0], testMessage, "Packed and unpacked message should be the same");
    }

    @Test
    void PRIMethodTest() throws IOException {
        PRIMethod priMethod = new PRIMethod();

        int[] keyPRI = priMethod.generateKey(image);
        priMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoPath);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoPath);
        assertEquals(priMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0], testMessage, "Packed and unpacked message should be the same");
    }

    @Test
    void PRPMethodTest() throws IOException {
        PRPMethod prpMethod = new PRPMethod();

        int[] keyPRI = prpMethod.generateKey(image);
        prpMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoPath);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoPath);
        assertEquals(prpMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0], testMessage, "Packed and unpacked message should be the same");
    }

    @Test
    void BHMethodTest() throws IOException {
        BHMethod bhMethod = new BHMethod();

        bhMethod.packMessage(testMessage + endMessageMarker, image, stegoPath);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoPath);
        assertEquals(bhMethod.unpackMessage(imgContainer).split(endMessageMarker)[0], testMessage, "Packed and unpacked message should be the same");
    }

    @Test
    void IQMethodTest() throws IOException {
        IQMethod iqMethod = new IQMethod();

        int[] keyPRI = iqMethod.generateKey(image);
        iqMethod.packMessage(testMessage + endMessageMarker, keyPRI, image, stegoPath);
        BufferedImage imgContainer = FileOperations.readImageFromFile(stegoPath);
        assertEquals(iqMethod.unpackMessage(keyPRI, imgContainer).split(endMessageMarker)[0], testMessage, "Packed and unpacked message should be the same");
    }
}
