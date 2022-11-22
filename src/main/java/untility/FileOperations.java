package untility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
