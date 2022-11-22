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


    public static byte[][] byteToBitMatrix(byte byteValue) {
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

    public static byte bitMatrixToByte(byte[][] byteMatrix) {
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
