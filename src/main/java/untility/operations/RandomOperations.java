package untility.operations;

import java.util.Random;

public class RandomOperations {
    public static int getRandomIntInBounds(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
