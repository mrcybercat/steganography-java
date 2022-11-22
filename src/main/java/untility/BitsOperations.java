package untility;

/**
 *
 * The {@code BitsOperations} is an utility class that provides methods
 * to perform operations on bytes bitwise and convert bytes to bit matrices.
 */
public final class BitsOperations {

    public static byte getAtPosition(byte byteIn, int position){
        return (byte) ((byteIn >> position) & 1);
    }

    // Returns modified n.
    public static int modifyAtPosition(int byteValue, int position, int bit){
        int mask = 1 << position;
        return (byteValue & ~mask) | ((bit << position) & mask);
    }

    public static int flipAtPosition(int byteValue, int position){
        return byteValue ^ (1 << position);
    }


    public static byte[][] byteToBitMatrix(byte byteValue) {
        byte[][] bitMatrix = new byte[8][1];
        // BitSet bitset = new BitSet(byteValue);
        for(int i = 0; i < 8; i++){
            if(BitsOperations.getAtPosition(byteValue, i) == 0){
                bitMatrix[i][0] = 0;
            }
            else {
                bitMatrix[i][0] = 1;
            }
        }
        return bitMatrix;
    }

    public static byte bitMatrixToByte(byte[][] bitMatrix) {
        byte byteValue = 0;

        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 0, bitMatrix[0][0]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 1, bitMatrix[0][1]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 2, bitMatrix[0][2]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 3, bitMatrix[0][3]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 4, bitMatrix[0][4]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 5, bitMatrix[0][5]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 6, bitMatrix[0][6]);
        byteValue = (byte) BitsOperations.modifyAtPosition(byteValue, 7, bitMatrix[0][7]);

        return byteValue;
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
