package untility.properties;

import org.apache.commons.lang3.ArrayUtils;
import untility.RGBArray;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Contrast {
    public static double calculateContrast(RGBArray rgbArray) {
        int[] red = Stream.of(rgbArray.getRed()) //we start with a stream of objects Stream<int[]>
                .flatMapToInt(IntStream::of) //we I'll map each int[] to IntStream
                .toArray();
        int[] green = Stream.of(rgbArray.getRed()) //we start with a stream of objects Stream<int[]>
                .flatMapToInt(IntStream::of) //we I'll map each int[] to IntStream
                .toArray();
        int[] blue = Stream.of(rgbArray.getRed()) //we start with a stream of objects Stream<int[]>
                .flatMapToInt(IntStream::of) //we I'll map each int[] to IntStream
                .toArray();

        Integer minRed = Collections.min(Arrays.asList(ArrayUtils.toObject(red)));
        Integer maxRed = Collections.max(Arrays.asList(ArrayUtils.toObject(red)));

        Integer minGreen = Collections.min(Arrays.asList(ArrayUtils.toObject(green)));
        Integer maxGreen = Collections.max(Arrays.asList(ArrayUtils.toObject(green)));

        Integer minBlue = Collections.min(Arrays.asList(ArrayUtils.toObject(blue)));
        Integer maxBlue = Collections.max(Arrays.asList(ArrayUtils.toObject(blue)));

        return ((maxRed - minRed) + (maxGreen - minGreen) + (maxBlue - minBlue)) / 3.0;
    }


}
