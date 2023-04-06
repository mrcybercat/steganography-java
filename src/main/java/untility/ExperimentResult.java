package untility;

public class ExperimentResult {

    private String status;
    private String nature;
    private String description;
    private String format;
    private String size;

    private Double contrast;
    private Double hue;
    private Double saturation;
    private Double value;

    private String algorithm;


    private Integer payload;
    private Double NAAD;
    private Double NCC;
    private Double CQ;
    private Double MSE;
    private Double PSNR;
    private Double UIQI;
    private Double SSIM;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public Integer getPayload() {
        return payload;
    }

    public void setPayload(Integer payload) {
        this.payload = payload;
    }

    public Double getNAAD() {
        return NAAD;
    }

    public void setNAAD(Double NAAD) {
        this.NAAD = NAAD;
    }

    public Double getCQ() {
        return CQ;
    }

    public void setCQ(Double CQ) {
        this.CQ = CQ;
    }

    public Double getNCC() {
        return NCC;
    }

    public void setNCC(Double NCC) {
        this.NCC = NCC;
    }

    public Double getMSE() {
        return MSE;
    }

    public void setMSE(Double MSE) {
        this.MSE = MSE;
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
