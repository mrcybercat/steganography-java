package untility.properties;

import untility.RGBArray;

public class ImageProperties {
    RGBArray rgbArray;

    public ImageProperties(RGBArray rgbArray) {
        this.rgbArray = rgbArray;
    }

    public double getContrast(){
        return Contrast.calculateContrast(rgbArray);
    }
    public double getHue(){
        return Hue.calculateHue(rgbArray);
    }
    public double getSaturation(){
        return Saturation.calculateSaturation(rgbArray);
    }
    public double getValue(){
        return Value.calculateValue(rgbArray);
    }
}
