package steganography;

import untility.BitsOperations;
import untility.MatrixOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

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
                    blue[y][x] = BitsOperations.modifyOnPosition(blue[y][x], 0, BitsOperations.getOnPosition(byteArray[counter/8], counter%8));
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
                byteArray[(rgbArray.getBlue()[0].length * y + x)/8] = (byte) BitsOperations.modifyOnPosition(byteArray[(rgbArray.getBlue()[0].length * y + x)/8],counter%8, BitsOperations.getOnPosition((byte) blue[y][x], 0));
                counter++;
            }
        }
        byteArray = decryptTranspos(key, byteArray);
        return new String(byteArray, "ASCII");
    }

    public byte[] encryptTranspos(int[] key, byte[] byteArray){
        byte[][] keyArray = keyToMatrix(key);

        for(int i = 0; i < byteArray.length; ++i){
            byte[][] valueArray = byteToMatrix(byteArray[i]);
            byteArray[i] = matrixToByte(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), keyArray));
        }
        return byteArray;
    }

    public byte[] decryptTranspos(int[] key, byte[] byteArray){
        byte[][] keyArray = keyToMatrix(key);

        for(int i = 0; i < byteArray.length; i++){
            byte[][] valueArray = byteToMatrix(byteArray[i]);
            byteArray[i] = matrixToByte(MatrixOperations.multiply(MatrixOperations.transpose(valueArray), MatrixOperations.inverse(keyArray)));
            if(i==10){
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

    public byte[][] byteToMatrix(byte byteValue) {
        byte[][] byteMatrix = new byte[8][1];
        // BitSet bitset = new BitSet(byteValue);
        for(int i = 0; i < 8; i++){
            if(BitsOperations.getOnPosition(byteValue, i) == 0){
                byteMatrix[i][0] = 0;
            }
            else {
                byteMatrix[i][0] = 1;
            }
        }
        return byteMatrix;
    }

    public byte matrixToByte(byte[][] byteMatrix) {
        byte byteValue = 0;

        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 0, byteMatrix[0][0]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 1, byteMatrix[0][1]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 2, byteMatrix[0][2]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 3, byteMatrix[0][3]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 4, byteMatrix[0][4]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 5, byteMatrix[0][5]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 6, byteMatrix[0][6]);
        byteValue = (byte) BitsOperations.modifyOnPosition(byteValue, 7, byteMatrix[0][7]);

        return byteValue;
    }

}
// MatrixOperations.print(keyArray);
// MatrixOperations.print(MatrixOperations.inverse(keyArray));
// MatrixOperations.print(byteMatrix);
// System.out.println("byte start " + byteValue);
// System.out.println("byte finish " + byteValue);
// System.out.println(String.valueOf("lengh " + byteArray.length));
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