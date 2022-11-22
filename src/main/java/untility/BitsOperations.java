package untility;

public final class BitsOperations {

    public static byte getOnPosition(byte byteIn, int position){
        return (byte) ((byteIn >> position) & 1);
    }

    // Returns modified n.
    public static int modifyOnPosition(int byteValue, int position, int bit){
        int mask = 1 << position;
        return (byteValue & ~mask) | ((bit << position) & mask);
    }
}
