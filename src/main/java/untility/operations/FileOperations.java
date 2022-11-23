package untility.operations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The {@code FileOperations} is an utility class that provides methods
 * to interact with files (reading & writing).
 */
public final class FileOperations {
    public static BufferedImage readImageFromFile(String picPath){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(picPath)); // eventually C:\\ImageTest\\pic2.jpg
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
