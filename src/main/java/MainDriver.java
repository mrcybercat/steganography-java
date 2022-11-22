import steganography.keybased.IQMethod;
import steganography.keybased.PRIMethod;
import steganography.keybased.PRPMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.LSBMethod;
import untility.operations.FileOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The {@code MainDriver} class .
 */
public class MainDriver {

    public static void main(String[] args) throws IOException {
        String picPath = "src\\main\\resources\\Input.bmp";
        String filePath1 = "src\\main\\resources\\Output.bmp";
        String filePath2 = "src\\main\\resources\\Output_Random.bmp";
        String stegoPath1 = "src\\main\\resources\\StegoLSB.bmp";
        String stegoPath2 = "src\\main\\resources\\StegoPRI.bmp";
        String stegoPath3 = "src\\main\\resources\\StegoPRP.bmp";
        String stegoPath4 = "src\\main\\resources\\StegoBH.bmp";
        String stegoPath5 = "src\\main\\resources\\StegoIQ.bmp";

        String endMessageMarker = "EnD_mes_1!";

        BufferedImage img = FileOperations.readImageFromFile(picPath);
        RGBArray rgbArray = new RGBArray();

        rgbArray.imageToRGBArray(img);
        rgbArray.saveImageFromRGBArray(filePath1);

        RGBArray randomRGBArray = new RGBArray();

        randomRGBArray.generateRandomRGBArray(1080, 1920);
        randomRGBArray.saveImageFromRGBArray(filePath2);

        System.out.println("LSBMethod !!!!");

        LSBMethod lsbMethod = new LSBMethod();

        lsbMethod.packMessage("Hello it is nice to meet you" + endMessageMarker, img, stegoPath1);
        BufferedImage imgContener1 = FileOperations.readImageFromFile(stegoPath1);
        System.out.println(lsbMethod.unpackMessage(imgContener1).split(endMessageMarker)[0]);


        System.out.println("PRIMethod !!!!");

        PRIMethod priMethod = new PRIMethod();

        int[] keyPRI = priMethod.generateKey(img);
        priMethod.packMessage("Hi, do you" + endMessageMarker, keyPRI, img, stegoPath2);
        BufferedImage imgContener2 = FileOperations.readImageFromFile(stegoPath2);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(priMethod.unpackMessage(keyPRI, imgContener2).split(endMessageMarker)[0]);

        System.out.println("PRPMethod !!!!");

        PRPMethod prpmethod = new PRPMethod();

        int[] keyPRP = prpmethod.generateKey(img);
        prpmethod.packMessage("Hi, do you pally" + endMessageMarker, keyPRP, img, stegoPath3);
        BufferedImage imgContener3 = FileOperations.readImageFromFile(stegoPath3);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(prpmethod.unpackMessage(keyPRP, imgContener3).split(endMessageMarker)[0]);

        System.out.println("BHMethod !!!!");

        BHMethod bhMethod = new BHMethod();
        bhMethod.packMessage("Howdy partner" + endMessageMarker, img, stegoPath4);
        BufferedImage imgContener4 = FileOperations.readImageFromFile(stegoPath4);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(bhMethod.unpackMessage(imgContener4).split(endMessageMarker)[0]);

        System.out.println("IQMethod !!!!");

        IQMethod iqMethod = new IQMethod();
        int[] keyIQ = iqMethod.generateKey(img);
        System.out.printf("Done!");
        iqMethod.packMessage("Oi mate" + endMessageMarker, keyIQ, img, stegoPath5);
        System.out.printf("Done!");
        BufferedImage imgContener5 = FileOperations.readImageFromFile(stegoPath5);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(iqMethod.unpackMessage(keyIQ, imgContener5).split(endMessageMarker)[0]);
    }
}




//    System.out.printf("Height = " + image.getHeight() + " / Width  = " + image.getWidth());

//System.out.printf(.toString() + rgbArray.getGreen().toString() + rgbArray.getBlue().toString());
//System.out.println(Arrays.deepToString(rgbArray.getRed()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//System.out.println("Green");
//System.out.println(Arrays.deepToString(rgbArray.getGreen()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//System.out.println("Blue");
//System.out.println(Arrays.deepToString(rgbArray.getBlue()).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

// int height = rgbArray.getRed().length;
// System.out.println("height i = " + height);
// int width = rgbArray.getRed()[0].length;
// System.out.println("width i = " + width);


//        System.out.println("Red");
//        printMatrix(rgbArray.getRed(), 10);
//        System.out.println("Green");
//        printMatrix(rgbArray.getGreen(), 10);
//        System.out.println("Blue");
//        printMatrix(rgbArray.getBlue(), 10);
//    System.out.println("Loop i = " + i +  " j = " + j  );
/*


    public static untility.RGBArray imageToRGBArray(BufferedImage image){
        int[][] red = new int[image.getHeight()][image.getWidth()];
        int[][] green = new int[image.getHeight()][image.getWidth()];
        int[][] blue = new int[image.getHeight()][image.getWidth()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //                  2          1          0
                //    bitpos     32109876 54321098 76543210
                //    ------   --+--------+--------+--------+
                //    bits     ..|RRRRRRRR|GGGGGGGG|BBBBBBBB|
                red[y][x] = (image.getRGB(x, y) >> 16) & 0xff;
                green[y][x] = (image.getRGB(x, y) >> 8) & 0xff;
                blue[y][x] = (image.getRGB(x, y)) & 0xff;
            //    System.out.println("Loop i = " + i +  " j = " + j  );
            }
        }
        return new untility.RGBArray(red, green, blue);
    }

    public static untility.RGBArray generateRandomRGBArray(int height, int width){
        Random random = new Random();
        int[][] red = new int[height][width];
        int[][] green = new int[height][width];
        int[][] blue = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                red[y][x] = getRandomIntInBounds(0,255);
                green[y][x] = getRandomIntInBounds(0,255);
                blue[y][x] = getRandomIntInBounds(0,255);
            }
        }
        return new untility.RGBArray(red, green, blue);
    }

    private static int getRandomIntInBounds(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void saveImageFromRGBArray(untility.RGBArray rgbArray, String filePath) throws IOException {
        BufferedImage image = new BufferedImage(
                rgbArray.getRed()[0].length, rgbArray.getRed().length, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < rgbArray.getRed().length; y++) {
            for (int x = 0; x < rgbArray.getRed()[0].length; x++) {
                int rgb = rgbArray.getRed()[y][x];
                rgb = (rgb << 8) + rgbArray.getGreen()[y][x];
                rgb = (rgb << 8) + rgbArray.getBlue()[y][x];
                image.setRGB(x, y, rgb);
            }
        }

        File outputFile = new File(filePath);
        ImageIO.write(image, "bmp", outputFile);
    }
    public static void printMatrix(int[][] matrix, int restriction) {
        for (int i = 0; i < restriction; i++) {
            if(restriction > matrix.length){
                restriction = matrix.length;
            }
            System.out.print("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + ", ");
            }
            System.out.print("]");
            System.out.println();
        }
    }
 */