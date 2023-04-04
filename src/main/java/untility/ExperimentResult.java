package untility;

public class ExperimentResult {

    private String nature;
    private String format;
    private String size;

    private Double contrast;
    private Double hue;
    private Double saturation;
    private Double lightness;

    private String algorithm;

    private Double NAD;
    private Double CQ;
    private Double PSNR;
    private Double UIQI;
    private Double SSIM;

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getContrast() {
        return contrast;
    }

    public void setContrast(Double contrast) {
        this.contrast = contrast;
    }

    public Double getHue() {
        return hue;
    }

    public void setHue(Double hue) {
        this.hue = hue;
    }

    public Double getSaturation() {
        return saturation;
    }

    public void setSaturation(Double saturation) {
        this.saturation = saturation;
    }

    public Double getLightness() {
        return lightness;
    }

    public void setLightness(Double lightness) {
        this.lightness = lightness;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Double getNAD() {
        return NAD;
    }

    public void setNAD(Double NAD) {
        this.NAD = NAD;
    }

    public Double getCQ() {
        return CQ;
    }

    public void setCQ(Double CQ) {
        this.CQ = CQ;
    }

    public Double getPSNR() {
        return PSNR;
    }

    public void setPSNR(Double PSNR) {
        this.PSNR = PSNR;
    }

    public Double getUIQI() {
        return UIQI;
    }

    public void setUIQI(Double UIQI) {
        this.UIQI = UIQI;
    }

    public Double getSSIM() {
        return SSIM;
    }

    public void setSSIM(Double SSIM) {
        this.SSIM = SSIM;
    }
}
