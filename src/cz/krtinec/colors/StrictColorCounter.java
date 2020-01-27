package cz.krtinec.colors;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Counts all color from all pixels of the image.
 * @see FastColorCounter
 */
public class StrictColorCounter implements IColorCounter {

    public Map<Integer, Integer> count(BufferedImage source) {
        FastRGB image = new FastRGB(source);
        //Uncomment next line to use standard Java getRGB() implementation
        //BufferedImage image = source;
        Map<Integer, Integer> counter = new HashMap<>();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                final int color = image.getRGB(x, y);
                counter.merge(color, 1, Integer::sum);
            }
        }
        
        return counter;

    }

}
