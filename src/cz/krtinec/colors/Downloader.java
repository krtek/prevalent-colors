package cz.krtinec.colors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader extends Thread {
    private static final int MAX_QUEUE_SIZE = 30;
    private BufferedReader input;
    private PrintWriter error;
    private List<Image> queue;
    public static boolean RUN = true;
    public Integer total = 0;
    private Integer count = 0;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    //This parameter should be tweaked for particular HW or CPU
    private static final int BUFFER_SIZE = 20;


    public Downloader(BufferedReader input, List<Image> queue, BufferedWriter error) {
        this.input = input;
        this.queue = queue;
        this.error = new PrintWriter(error);

    }

    private void download(String url) {
        try {
            BufferedImage data = ImageIO.read(new URL(url));
            queue.add(new Image(url, data));
            this.total += 1;
        } catch (IOException e) {
            System.err.println("Error downloading: " + url);
            error.println(url);
        }
    }


    @Override
    public void run() {
        String line = "ready";
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        while (RUN && line != null) {
            try {
                while (count < BUFFER_SIZE && (line = input.readLine()) != null) {
                    final String url = line;
                    futures.add(CompletableFuture.runAsync(() -> download(url), executor));
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //wait for all files to be downloaded and for Analyzer to catch up
            while (queue.size() > MAX_QUEUE_SIZE || futures.stream().anyMatch(i -> !i.isDone())) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }
            
            count = 0;
            futures.clear();
        }


        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        RUN = false;
        executor.shutdown();
    }
}
