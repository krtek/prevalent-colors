package cz.krtinec.colors;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Analyzes image. Counts all colors in image and writes top 3 to output file.
 */
public class Analyzer extends Thread {
    private List<Image> queue;
    public static Boolean RUN = true;
    //Switch to FastColorCounter for faster version.
    private IColorCounter counter = new StrictColorCounter();
    private PrintWriter output;
    public Integer total = 0;

    public Analyzer(List<Image> queue, BufferedWriter output) {
        this.queue = queue;
        this.output = new PrintWriter(output);
    }

    @Override
    public void run() {
        while (RUN) {
            if (!queue.isEmpty()) {
                final Image image = queue.remove(0);

                long start = System.currentTimeMillis();
                List<Color> top3 = top3(counter.count(image.getData()));

                    output.write(String.format("%s,%s,%s,%s\n",
                            image.getUrl(),
                            top3.remove(0),
                            top3.remove(0),
                            top3.remove(0)
                            //System.currentTimeMillis() - start
                    ));
                    total += 1;

            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //do nothing
                }
            }
        }
    }

    private static List<Color> top3(Map<Integer, Integer> map) {
        return map.entrySet()
                .stream()
                .map(Analyzer::toColor)
                .sorted()
                .limit(3)
                .collect(Collectors.toList());
    }

    private static Color toColor(Map.Entry<Integer, Integer> entry) {
        return new Color(entry.getKey(), entry.getValue());
    }
}
