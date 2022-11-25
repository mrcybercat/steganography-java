import steganography.GenericMethod;
import steganography.interfaces.KeyBasedSteganography;
import steganography.keybased.IQMethod;
import steganography.keybased.PRPMethod;
import steganography.keybased.PRIMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.KJBMethod;
import steganography.keyless.LSBMethod;
import untility.operations.FileOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MainDriver} class .
 */
public class MainDriver {

    public static void main(String[] args) throws IOException {
        File picPath = new File("src\\main\\resources\\Input.bmp");
        File filePath1 = new File("src\\main\\resources\\Output.bmp");
        File filePath2 = new File("src\\main\\resources\\Output_Random.bmp");

        BufferedImage img = FileOperations.readImageFromFile(picPath);
        RGBArray rgbArray = new RGBArray();

        rgbArray.imageToRGBArray(img);
        rgbArray.saveImageFromRGBArray(filePath1);

        RGBArray randomRGBArray = new RGBArray();

        randomRGBArray.generateRandomRGBArray(1080, 1920);
        randomRGBArray.saveImageFromRGBArray(filePath2);

        List<Object> methods = initializeMethodList();
        List<File> stegoPathes = initializePathListList();
        String endMessageMarker = "EnD_mes_1!";
        String message = "Hi there!";

        int index = 0;
        for (Object method : methods) {
            genericStegoCycle(method, message, endMessageMarker, img, stegoPathes.get(index));
            index++;
        }
    }

    public static void genericStegoCycle(Object method, String message, String endMessageMarker, BufferedImage image, File outputFile) throws IOException {
        System.out.println("Using method " + method.getClass().toString());

        GenericMethod genericMethod = new GenericMethod();
        int[] key = null;
        if(method instanceof KeyBasedSteganography){
            key = genericMethod.generateKey(method, message + endMessageMarker, image);
        }
        genericMethod.packMessage(method, message + endMessageMarker, key, image, outputFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(outputFile);
        System.out.println(genericMethod.unpackMessage(method, key, imgContainer).split(endMessageMarker)[0]);
    }

    public static List<Object> initializeMethodList(){
        List<Object> methods = new ArrayList<Object>();
        methods.add(new LSBMethod());
        methods.add(new PRPMethod());
        methods.add(new PRIMethod());
        methods.add(new BHMethod());
        methods.add(new IQMethod());
        methods.add(new KJBMethod());
        return methods;
    }

    public static List<File> initializePathListList(){
        List<File> stegoPathes = new ArrayList<File>();
        stegoPathes.add(new File("src\\main\\resources\\StegoLSB.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoPRI.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoPRP.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoBH.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoIQ.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoKJB.bmp"));
        return stegoPathes;
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


        String picPath1 = "src\\main\\resources\\Input.bmp";
        String filePath11 = "src\\main\\resources\\Output.bmp";
        String filePath22 = "src\\main\\resources\\Output_Random.bmp";
        String stegoPath11 = "src\\main\\resources\\StegoLSB.bmp";
        String stegoPath22 = "src\\main\\resources\\StegoPRI.bmp";
        String stegoPath33 = "src\\main\\resources\\StegoPRP.bmp";
        String stegoPath44 = "src\\main\\resources\\StegoBH.bmp";
        String stegoPath55 = "src\\main\\resources\\StegoIQ.bmp";
        String stegoPath66 = "src\\main\\resources\\StegoKJB.bmp";


                System.out.println("LSBMethod !!!!");

        LSBMethod lsbMethod = new LSBMethod();

        lsbMethod.packMessage("Hello it is nice to meet you" + endMessageMarker, img, stegoPath1);
        BufferedImage imgContener1 = FileOperations.readImageFromFile(stegoPath1);
        System.out.println(lsbMethod.unpackMessage(imgContener1).split(endMessageMarker)[0]);

        System.out.println("BHMethod !!!!");

        BHMethod bhMethod = new BHMethod();
        bhMethod.packMessage("Howdy partner" + endMessageMarker, img, stegoPath4);
        BufferedImage imgContener4 = FileOperations.readImageFromFile(stegoPath4);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(bhMethod.unpackMessage(imgContener4).split(endMessageMarker)[0]);

        System.out.println("IQMethod !!!!");

        IQMethod iqMethod = new IQMethod();

        int[] keyIQ = iqMethod.generateKey(img, "Howdy partner" + endMessageMarker);
        System.out.printf("Done!");
        iqMethod.packMessage("Oi mate" + endMessageMarker, keyIQ, img, stegoPath5);
        System.out.printf("Done!");
        BufferedImage imgContener5 = FileOperations.readImageFromFile(stegoPath5);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(iqMethod.unpackMessage(keyIQ, imgContener5).split(endMessageMarker)[0]);

        System.out.println("KJ !!!!");

        KJBMethod kjbMethod = new KJBMethod();
        kjbMethod.packMessage("Howdy partner" + endMessageMarker, img, stegoPath6);
        BufferedImage imgContener6 = FileOperations.readImageFromFile(stegoPath6);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(kjbMethod.unpackMessage(imgContener6).split(endMessageMarker)[0]);

        System.out.println("PRPMethod !!!!");

        PRPMethod prpMethod = new PRPMethod();

        int[] keyPRI = prpMethod.generateKey(img, "Hi, do you" + endMessageMarker);
        prpMethod.packMessage("Hi, do you" + endMessageMarker, keyPRI, img, stegoPath2);
        BufferedImage imgContener2 = FileOperations.readImageFromFile(stegoPath2);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(prpMethod.unpackMessage(keyPRI, imgContener2).split(endMessageMarker)[0]);

        System.out.println("PRIMethod !!!!");

        PRIMethod priMethod = new PRIMethod();

        int[] keyPRP = priMethod.generateKey(img, "Hi, do you pally" + endMessageMarker);
        priMethod.packMessage("Hi, do you pally" + endMessageMarker, keyPRP, img, stegoPath3);
        BufferedImage imgContener3 = FileOperations.readImageFromFile(stegoPath3);
        //prpmethod.unpackMessage(key, imgContener2);
        System.out.println(priMethod.unpackMessage(keyPRP, imgContener3).split(endMessageMarker)[0]);
 */