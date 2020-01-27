package cz.krtinec.colors;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final String INPUT_TXT = "resources/input.txt";
    private static final String OUTPUT_TXT = "resources/output.txt";
    private static final String ERROR_TXT = "resources/error.txt";

    private static List<Image> queue = Collections.synchronizedList(new LinkedList<>());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            BufferedReader input = new BufferedReader(new FileReader(INPUT_TXT));
            BufferedWriter output = new BufferedWriter(new FileWriter(OUTPUT_TXT));
            BufferedWriter error = new BufferedWriter(new FileWriter(ERROR_TXT));

            Downloader downloader = new Downloader(input, queue, error);
            downloader.start();

            Analyzer analyzer = new Analyzer(queue, output);
            analyzer.start();
            
            //Wait for both processes to finish
            while (Downloader.RUN || !queue.isEmpty()) {
                Thread.sleep(10000);
                System.out.printf("Downloaded: %s, analyzed: %s, queue: %s", downloader.total, analyzer.total, queue.size());
                System.out.println();
            }

            //Add another moment to write results
            Thread.sleep(100);

            Analyzer.RUN = false;
            output.close();
            error.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            //Do nothing
        }
        System.out.printf("Processed in %d ms", System.currentTimeMillis() - start);
    }
}
