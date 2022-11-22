package steganography.keybased;

import steganography.interfaces.KeyBasedSteganography;
import untility.BitsOperations;
import untility.MatrixOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * The {@code PRIMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an PRI approach (Pseudo Random Interval).
 *
 * <p>
 * This class uses pseudorandom key to perform steganographic operations.
 *
 * <p>
 * This class implements the {@link KeyBasedSteganography KeyBasedSteganography} interface.
 *
 * @see KeyBasedSteganography
 */
public class PRIMethod implements KeyBasedSteganography {
    @Override
    public int[] generateKey(BufferedImage image) {
        int[] key = new int[8 * 8 + 1];

        key = new int[]{8,
                0, 0, 1, 0, 0, 0, 0, 0,
                0, 1, 0, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 0, 0,
                0, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0,
                0, 0, 0, 1, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 1};
        return key;
    }

    @Override
    public void packMessage(String message, int[] key, BufferedImage image, String newFilePath) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = encryptTranspos(key, message.getBytes(charset));

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                if((counter/8) < byteArray.length){
                    blue[y][x] = BitsOperations.modifyAtPosition(blue[y][x], 0, BitsOperations.getAtPosition(byteArray[counter/8], counter%8));
                }
                counter++;
            }
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(newFilePath);
    }

    @Override
    public String unpackMessage(int[] key, BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[(image.getWidth() * image.getHeight())/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = 0; y < rgbArray.getBlue().length; y++) {
            for (int x = 0; x < rgbArray.getBlue()[0].length; x++) {
                byteArray[(rgbArray.getBlue()[0].length * y + x)/8] = (byte) BitsOperations.modifyAtPosition(byteArray[(rgbArray.getBlue()[0].length * y + x)/8],counter%8, BitsOperations.getAtPosition((byte) blue[y][x], 0));
                counter++;
            }
        }
        byteArray = decryptTranspos(key, byteArray);
        return new String(byteArray, "ASCII");
    }

    public byte[] encryptTranspos(int[] key, byte[] byteArray){
        byte[][] keyArray = keyToMatrix(key);

        for(int i = 0; i < byteArray.length; ++i){
            byte[][] valueArray = BitsOperations.byteToBitMatrix(byteArray[i]);
            byteArray[i] = BitsOperations.bitMatrixToByte(Objects.requireNonNull(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), keyArray)));
        }
        return byteArray;
    }

    public byte[] decryptTranspos(int[] key, byte[] byteArray){
        byte[][] keyArray = keyToMatrix(key);

        for(int i = 0; i < byteArray.length; i++){
            byte[][] valueArray = BitsOperations.byteToBitMatrix(byteArray[i]);
            byteArray[i] = BitsOperations.bitMatrixToByte(Objects.requireNonNull(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), MatrixOperations.inverse(keyArray))));
            if(i==100){
                return byteArray;
            }
        }
        return byteArray;
    }

    public byte[][] keyToMatrix(int[] key) {
        byte[][] keyMatrix = new byte[key[0]][key[0]];

        for (int i = 0; i < keyMatrix.length; i++){
            for (int j = 0; j < keyMatrix[0].length; j++){
                keyMatrix[i][j] = (byte) key[keyMatrix[0].length * i + j + 1];
            }
        }
        return keyMatrix;
    }
}
// MatrixOperations.print(keyArray);
// MatrixOperations.print(MatrixOperations.inverse(keyArray));
// MatrixOperations.print(byteMatrix);
// System.out.println("byte start " + byteValue);
// System.out.println("byte finish " + byteValue);
// System.out.println(String.valueOf("length " + byteArray.length));
// System.out.println(String.valueOf("i " + i));
// MatrixOperations.print(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), keyArray));
// System.out.println("Exiting...");
// System.out.println(matrixToByte(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), keyArray)));
//                System.out.println("counter = " + counter);
//                if(counter == 100){
//                    System.out.println("Exiting 1 on counter = " + counter);
//                    byteArray = decryptTranspos(key, byteArray);
//                    System.out.println("Exiting 2 on counter = " + counter);
//                    return new String(byteArray, "ASCII");
//               }
// System.out.printf("keyMatrix [%d][%d] = %d ", i , j , key[keyMatrix[0].length * i + j + 1]);
// System.out.println();