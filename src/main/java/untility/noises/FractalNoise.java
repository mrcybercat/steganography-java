package untility.noises;

import java.util.Random;

public class FractalNoise implements NoiseGenerator {
    private double alpha;

    private final int poles;
    private final double[] multipliers;

    private final double[] values;
    private final Random rnd;


    /**
     * Generate pink noise with alpha=1.0 using a five-pole IIR.
     */
    public FractalNoise() {
        this(1.0, 5, new Random());
    }


    /**
     * Generate a specific pink noise using a five-pole IIR.
     *
     * @param alpha  the exponent of the pink noise, 1/f^alpha.
     * @throws IllegalArgumentException  if <code>alpha < 0</code> or
     *                                      <code>alpha > 2</code>.
     */
    public FractalNoise(double alpha) {
        this(alpha, 5, new Random());
    }


    /**
     * Generate pink noise specifying alpha and the number of poles.
     * The larger the number of poles, the lower are the lowest
     * frequency components that are amplified.
     *
     * @param alpha   the exponent of the pink noise, 1/f^alpha.
     * @param poles   the number of poles to use.
     * @throws IllegalArgumentException  if <code>alpha < 0</code> or
     *                                      <code>alpha > 2</code>.
     */
    public FractalNoise(double alpha, int poles) {
        this(alpha, poles, new Random());
    }


    /**
     * Generate pink noise from a specific randomness source
     * specifying alpha and the number of poles.  The larger the
     * number of poles, the lower are the lowest frequency components
     * that are amplified.
     *
     * @param alpha   the exponent of the pink noise, 1/f^alpha.
     * @param poles   the number of poles to use.
     * @param random  the randomness source.
     * @throws IllegalArgumentException  if <code>alpha < 0</code> or
     *                                      <code>alpha > 2</code>.
     */
    public FractalNoise(double alpha, int poles, Random random) {
        if (alpha < 0 || alpha > 2) {
            throw new IllegalArgumentException("Invalid pink noise alpha = " +
                    alpha);
        }

        this.rnd = random;
        this.poles = poles;
        this.multipliers = new double[poles];
        this.values = new double[poles];

        double a = 1;
        for (int i=0; i < poles; i++) {
            a = (i - alpha/2) * a / (i+1);
            multipliers[i] = a;
        }

        // Fill the history with random values
        for (int i=0; i < 5*poles; i++)
            this.nextValue();
    }


    /**
     * Return the next pink noise sample.
     *
     * @return  the next pink noise sample.
     */
    @Override
    public double nextValue() {
        /*
         * The following may be changed to  rnd.nextDouble()-0.5
         * if strict Gaussian distribution of resulting values is not
         * required.
         */
        double x = rnd.nextGaussian();

        for (int i=0; i < poles; i++) {
            x -= multipliers[i] * values[i];
        }
        System.arraycopy(values, 0, values, 1, values.length-1);
        values[0] = x;

        return x;
    }

    @Override
    public int nextRGBValue() {
        FractalNoise noise = new FractalNoise(1, 10);
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
/*
    public static void main(String[] arg) {
        FractalNoise source;

        int n;
        double alpha = 1.0;
        int poles = 5;

        if (arg.length < 1 || arg.length > 3) {
            System.out.println("\nUsage:\n" +
                    "    java PinkNoise <samples> [<alpha> [<poles>]]\n\n" +
                    " <samples> = number of samples to output\n" +
                    " <alpha>   = PSD distribution exponent, 1/f^alpha " +
                    "(default 1.0)\n" +
                    " <poles>   = number of IIR poles to use (default 5)\n");
            System.exit(1);
        }

        // Parse arguments:
        n = Integer.parseInt(arg[0]);
        if (arg.length >= 2) {
            alpha = Double.parseDouble(arg[1]);
        }
        if (arg.length >= 3) {
            poles = Integer.parseInt(arg[2]);
        }


        // Generate values:
        source = new FractalNoise(alpha, poles);
        for (int i=0; i < 20; i++) {
            System.out.println(source.nextValue());
        }
    }*/
