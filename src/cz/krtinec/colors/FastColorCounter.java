package cz.krtinec.colors;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Counts color information from 1/4 randomly selected pixels. It's significantly faster than {@link StrictColorCounter}
 * but can (unlikely) return slightly inaccurate results.
 * @see StrictColorCounter
 */
public class FastColorCounter implements IColorCounter {

    public Map<Integer, Integer> count(BufferedImage source) {
        FastRGB image = new FastRGB(source);
        //Uncomment next line to use standard Java getRGB() implementation
        //BufferedImage image = source;
        Random random = new Random(System.currentTimeMillis());
        Map<Integer, Integer> counter = new HashMap<>();
        for (int x = 0; x < image.getWidth() / 2 + 1; x++) {
            for (int y = 0; y < image.getHeight() / 2 + 1; y++) {
                final int color = image.getRGB(random.nextInt(source.getWidth()), random.nextInt(source.getHeight()));
                counter.merge(color, 1, Integer::sum);
            }
        }

        return counter;

    }

}
