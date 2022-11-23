package untility.operations;

import java.util.Random;

/**
 * The {@code RandomOperations} is an utility class that provides methods
 * for random operations.
 */
public final class RandomOperations {
    public static int getRandomIntInBounds(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
