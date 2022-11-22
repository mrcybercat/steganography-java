package steganography;

import untility.RGBArray;
import untility.BitsOperations;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class LSBMethod implements KeyLessSteganography {
    @Override
    public void packMessage(String message, BufferedImage image, String newFilePath) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

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
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
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
        return new String(byteArray, "ASCII");
    }
}
/*
                  if(getBit(byteArray[counter/8], counter%8) == 0){
                        blue[y][x] = (int) BitJuggler.initialize(blue[y][x]).clearBits(0).getValue();
                    }
                    if(getBit(byteArray[counter/8], counter%8) == 0){
                        blue[y][x] = (int) BitJuggler.initialize(blue[y][x]).clearBits(0).getValue();
                    }
                    else {
                        throw new IllegalArgumentException();
                    }*/

/*

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public final class BitJuggler {

    //================================================
    // members
    //================================================
    private final long value;
    private static final LongBinaryOperator SET = (x, i) -> x | (1L << i);
    private static final LongBinaryOperator CLEAR = (x, i) -> x & ~(1L << i);
    private static final LongBinaryOperator TOGGLE = (x, i) -> x ^ (1L << i);

    //================================================
    // constructors
    //================================================
    private BitJuggler(long v) {
        value = v;
    }

    //================================================
    // public methods
    //================================================
    public static BitJuggler initialize(long v) {
        return new BitJuggler(v);
    }

    //----------------------------
    public BitJuggler setBits(long... bits) {
        return reduce(value, SET, bits);
    }

    //----------------------------
    public BitJuggler clearBits(long... bits) {
        return reduce(value, CLEAR, bits);
    }

    //----------------------------
    public BitJuggler toggleBits(long... bits) {
        return reduce(value, TOGGLE, bits);
    }

    //----------------------------
    public long getValue() {
        return value;
    }

    //----------------------------
    public static void main(String... args) {
        byte b = -127;
        System.out.println("input:  "+ Long.toBinaryString(b));
        long c = BitJuggler.initialize(b).clearBits(0, 1, 2).setBits(3, 5, 6).toggleBits(8, 9).getValue();
        System.out.println("output: "+ Long.toBinaryString(c));
        byte d = (byte) c;
        System.out.format("%o%n", d);
    }

    //================================================
    // private methods
    //================================================
    private BitJuggler reduce(long v, LongBinaryOperator lbo, long... bits) {
        return new BitJuggler(Arrays.stream(bits).reduce(v, lbo));
    }

    //================================================
    // end of class
    //================================================
}

 */

