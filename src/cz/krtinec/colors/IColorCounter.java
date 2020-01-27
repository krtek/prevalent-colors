package cz.krtinec.colors;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface IColorCounter {

    /**
     * Counts colors in @source and returns in in the form of Map&lt;color, count&gt;.
     * @param source image
     * @return counted colors in image
     */
    Map<Integer, Integer> count(BufferedImage source);
}
