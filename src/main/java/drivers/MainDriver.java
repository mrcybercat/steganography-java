package drivers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import steganography.Method;
import steganography.interfaces.KeyBasedSteganography;
import steganography.keybased.IQMethod;
import steganography.keybased.PRIMethod;
import steganography.keyless.BHMethod;
import steganography.keyless.LSB8bMethod;
import steganography.keyless.LSBMethod;
import untility.ExperimentResult;
import untility.metrics.ImageMetrics;
import untility.operations.FileOperations;
import untility.RGBArray;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import untility.properties.ImageProperties;

/**
 * The {@code drivers.MainDriver} class.
 * TODO: JavaDoc. Obviously
 * TODO: Implement streams?
 */
public class MainDriver {

    public static void main(String[] args) throws IOException {
        File experimentDirectory = new File("src\\main\\resources");
        File outputFile = new File("src\\main\\resources\\Output.bmp");

        //String endMessageMarker = "EnD_mes_1!";
        //String message = "Hi there! ";

        String[] extensions = new String[] { "bmp", "png" };
        List<File> pictures = (List<File>) FileUtils.listFiles(experimentDirectory, extensions, true);
        List<ExperimentResult> experiments = new ArrayList<>();

        for (File picture : pictures) {
            System.out.println("picture: " + picture.getCanonicalPath() + "is loaded");
        }

        for (File picture : pictures) {
            ExperimentResult experimentResult = new ExperimentResult();
            experimentResult.setNature(picture.getName().split("_")[0]);
            //experimentResult.setDescription(picture.getName().split("_")[1]);
            experimentResult.setFormat(FilenameUtils.getExtension(picture.getPath()));

            BufferedImage img = FileOperations.readImageFromFile(picture);
            calculateProperties(img, experimentResult);
            for(Object method : getAllMethods()){
                genericStegoCycle(method, picture, outputFile, experimentResult);
                calculateMetrics(img, outputFile, experimentResult);
                experiments.add(experimentResult);
            }
        }
    }

    private static void calculateMetrics(BufferedImage img, File outputFile, ExperimentResult experimentResult) {
        BufferedImage stegoImage = FileOperations.readImageFromFile(outputFile);
        RGBArray originArray = new RGBArray();
        originArray.imageToRGBArray(img);
        RGBArray stegoArray = new RGBArray();
        stegoArray.imageToRGBArray(stegoImage);
        ImageMetrics metrics = new ImageMetrics(originArray, stegoArray);


        experimentResult.setNAAD(metrics.getNAAD());
        System.out.println("NAAD = " + experimentResult.getNAAD());
        experimentResult.setMSE(metrics.getMSE());
        System.out.println("MSE = " + experimentResult.getMSE());
        experimentResult.setNCC(metrics.getNCC());
        System.out.println("NCC = " + experimentResult.getNCC());
        experimentResult.setCQ(metrics.getCQ());
        System.out.println("MSE = " + experimentResult.getCQ());
        experimentResult.setPSNR(metrics.getPSNR());
        System.out.println("PSNR = " + experimentResult.getPSNR());
        experimentResult.setSSIM(metrics.getSSIM());
        System.out.println("SSIM = " + experimentResult.getSSIM());
        experimentResult.setUIQI(metrics.getUIQI());
        System.out.println("UIQI = " + experimentResult.getUIQI());

    }

    private static void calculateProperties(BufferedImage img, ExperimentResult experimentResult) {
        experimentResult.setSize((img.getWidth() + "x" + img.getHeight()));
        RGBArray originArray = new RGBArray();
        originArray.imageToRGBArray(img);

        ImageProperties properties = new ImageProperties(originArray);

        experimentResult.setContrast(properties.getContrast());
        System.out.println("Contrast = " + experimentResult.getContrast());
        experimentResult.setHue(properties.getHue());
        System.out.println("Hue = " + experimentResult.getHue());
        experimentResult.setSaturation(properties.getSaturation());
        System.out.println("Saturation = " + experimentResult.getSaturation());
        experimentResult.setValue(properties.getValue());
        System.out.println("Value = " + experimentResult.getValue());
    }

    public static void genericStegoCycle(Object method, File inputFile, File outputFile, ExperimentResult experimentResult) throws IOException {
        System.out.println("Using method " + method.getClass().toString() + " on file " + inputFile.getPath());
        experimentResult.setAlgorithm(method.getClass().toString().split("\\.")[method.getClass().toString().split("\\.").length - 1]);
        BufferedImage image = FileOperations.readImageFromFile(inputFile);
        Method genericMethod = new Method();
        int maxPayload = genericMethod.getMaxPayload(method, image);
        String randomStr = RandomStringUtils.randomAscii(maxPayload / 8);
        experimentResult.setPayload(maxPayload);
        //System.out.println("A random string fitting container capacity "+  maxPayload + " + digits: " + randomStr);
        int[] key = null;
        if(method instanceof KeyBasedSteganography){
            key = genericMethod.generateKey(method,randomStr, image);
        }
        genericMethod.packMessage(method,randomStr, inputFile, outputFile);
        BufferedImage imgContainer = FileOperations.readImageFromFile(outputFile);
        //System.out.println(genericMethod.unpackMessage(method, key, imgContainer));
        if(randomStr.equals(genericMethod.unpackMessage(method, key, imgContainer))){
            experimentResult.setStatus("Success");
            System.out.println("Success");
        }
        else {
            experimentResult.setStatus("Warning");
            System.out.println("Warning");
        }
    }

    public static List<Object> getAllMethods(){
        List<Object> methods = new ArrayList<Object>();
        methods.add(new LSBMethod());
        //methods.add(new PRPMethod());
        methods.add(new PRIMethod());
        methods.add(new BHMethod());
        methods.add(new IQMethod());
        //methods.add(new KJBMethod());
        methods.add(new LSB8bMethod());
        return methods;
    }

    public static List<File> initializePathListList(){
        List<File> stegoPathes = new ArrayList<File>();
        stegoPathes.add(new File("src\\main\\resources\\StegoLSB.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoPRI.bmp"));
        //stegoPathes.add(new File("src\\main\\resources\\StegoPRP.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoBH.bmp"));
        stegoPathes.add(new File("src\\main\\resources\\StegoIQ.bmp"));
        //stegoPathes.add(new File("src\\main\\resources\\StegoKJB.bmp"));
        return stegoPathes;
    }
}





;

//        File dir = new File("dir");
//        System.out.println("Getting all .txt and .jsp files in " + dir.getCanonicalPath()
//              + " including those in subdirectories");
//

//    RGBArray rgbArray = new RGBArray();


//  CRC crc = new CRC(CRC.Parameters.CRC32);

//rgbArray.imageToRGBArray(img);
// rgbArray.saveImageFromRGBArray(filePath1);


///        List<Object> methods = initializeMethodList();
//       List<File> stegoPathes = initializePathListList();


//        int index = 0;


//LSBMethod lsbMethod = new LSBMethod();
//lsbMethod.getClass();
//File picPath = new File("src\\main\\resources\\Input.bmp");
//GenericMethod<SteganographyMethod> genericMethod = new GenericMethod<>();
//RGBArray randomRGBArray = new RGBArray();
//File filePath2 = new File("src\\main\\resources\\Output_Random.bmp");
//randomRGBArray.generateRandomRGBArray(1080, 1920);
//randomRGBArray.saveImageFromRGBArray(filePath2);

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