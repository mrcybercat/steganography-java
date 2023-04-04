package untility.noises;

import java.util.Random;

public class GaussianNoise implements NoiseGenerator{


    private final double mean;
    private final double standardDeviation;

    private final Random rnd;

    public GaussianNoise() {
        this(0, 1, new Random());
    }

    public GaussianNoise(double mean) {
        this(mean, 5, new Random());
    }

    public GaussianNoise(double mean, double standardDeviation) {
        this(mean, standardDeviation, new Random());
    }

    public GaussianNoise(double mean, double standardDeviation, Random random) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.rnd = random;
    }

    @Override
    public double nextValue() {
        double x = mean + rnd.nextGaussian()*standardDeviation;
        return x;
    }

    @Override
    public int nextRGBValue() {
        GaussianNoise noise = new GaussianNoise(1, 10);
        int result = (int)(128 + noise.nextValue()*128);
        if(result < 0){
            result = 0;
        }
        else if (result > 255){
            result = 255;
        }
        return result;
    }
}
