package steganography.keyless;

import steganography.Method;
import steganography.interfaces.KeyLessSteganography;
import steganography.interfaces.SteganographyMethod;
import untility.RGBArray;
import untility.operations.BitsOperations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * The {@code KJBMethod} class provides methods to perform steganographic packing
 * and unpacking of a message using an KJB approach (Kutter-Jordan-Bossen steganographic algorithm).
 *
 * <p>
 * This class implements the {@link KeyLessSteganography KeyLessSteganography} interface.
 *
 * @see KeyLessSteganography
 */
public class KJBMethod  extends Method implements KeyLessSteganography, SteganographyMethod {
    static final float gamma = 0.75F;
    static final int sigma = 3;

    @Override
    public void packMessage(String message, BufferedImage image, File outputFile) throws IOException {
        Charset charset = Charset.forName("ASCII");
        byte[] byteArray = message.getBytes(charset);

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = sigma; y < rgbArray.getBlue().length - sigma; y++) {
            if((counter/8) < byteArray.length){
                //System.out.println("y " + y + "bit " +  BitsOperations.getAtPosition(byteArray[counter/8], counter%8));

                int pixelMod = modifyPixel(rgbArray, y, y, BitsOperations.getAtPosition(byteArray[counter/8], counter%8));
                //System.out.println("pixelMod " + pixelMod );

                if(pixelMod >= 0 && pixelMod <= 255){
                    //System.out.println("clause 1 ");
                    blue[y][y] = pixelMod;
                }
                else if(pixelMod > 255){
                    //System.out.println("clause 2 ");
                    blue[y][y] = 255;
                }
                else if(pixelMod < 0){
                    //System.out.println("clause 3 ");
                    blue[y][y] = 0;
                }
                else{
                    throw new UnknownError();
                }
            }
            counter++;
        }
        rgbArray.setBlue(blue);
        rgbArray.saveImageFromRGBArray(outputFile);
    }

    @Override
    public String unpackMessage(BufferedImage image) throws UnsupportedEncodingException {
        byte[] byteArray = new byte[image.getWidth()/8];

        RGBArray rgbArray = new RGBArray();
        rgbArray.imageToRGBArray(image);
        int[][] blue = rgbArray.getBlue();
        int counter = 0;

        for (int y = sigma; y < rgbArray.getBlue().length - sigma; y++) {
            double pixelPrediction = 0;
            for (int x = y - sigma; x < y; x++){
                pixelPrediction = pixelPrediction + blue[y][x];
            }
            for (int x = y - sigma; x < y; x++){
                pixelPrediction = pixelPrediction + blue[x][y];
            }
            for (int x = y + 1; x < y + sigma + 1; x++){
                pixelPrediction = pixelPrediction + blue[y][x];
            }
            for (int x = y + 1; x < y + sigma + 1; x++){
                pixelPrediction = pixelPrediction + blue[x][y];
            }
            pixelPrediction = pixelPrediction / (4* sigma);

            //System.out.println("pixelPrediction = " + pixelPrediction);
            //System.out.println("blue[y][y] = " + blue[y][y]);

            if(pixelPrediction < blue[y][y]){
                byteArray[y/8] = (byte) BitsOperations.modifyAtPosition(byteArray[y/8],counter%8, 1);
            }
            else if(pixelPrediction >= blue[y][y]){
                byteArray[y/8]  = (byte) BitsOperations.modifyAtPosition(byteArray[y/8],counter%8, 0);
            }
            else{
                throw new UnknownError();
            }
            counter++;
        }
        return new String(byteArray, "ASCII");
    }

    public int modifyPixel(RGBArray rgbArray, int x, int y, int bit ){
        return Math.round(rgbArray.getBlue()[y][x] + (2 * bit - 1) * gamma * lambdaFunction(x, y, rgbArray));
    }

    public float lambdaFunction(int x, int y, RGBArray rgbArray){
        //System.out.printf("cooK " + (0.29890 * rgbArray.getRed()[y][x] + 0.58662 * rgbArray.getGreen()[y][x] + 0.11448 * rgbArray.getBlue()[y][x]));
        return (float) (0.29890 * rgbArray.getRed()[y][x] + 0.58662 * rgbArray.getGreen()[y][x] + 0.11448 * rgbArray.getBlue()[y][x]);
    }

    @Override
    public String getName() {
        return "KJBMethod";
    }
}
