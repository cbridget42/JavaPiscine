package day03.ex03;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class Downloader {
    private static final String URLS = "src/day03/ex03/files_urls.txt";
    private final int threadsCount;
    private Map<Integer, String> linkPool;

    public Downloader(int threadsCount) {
        this.threadsCount = threadsCount;
        this.linkPool = new TreeMap<>();
        readLinks();
    }

    public void startDownload() {
        ThreadsDownloader[] threads = new ThreadsDownloader[threadsCount];
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new ThreadsDownloader(i + 1);
            threads[i].start();
        }
        try {
            for (int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }
        } catch (Exception e) {
            Program.printError(e.toString());
        }
    }

    private void readLinks() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(URLS))) {
            String strTmp;
            while ((strTmp = fileReader.readLine()) != null) {
                String[] arrTmp = strTmp.split("\\s+");
                this.linkPool.put(Integer.parseInt(arrTmp[0]), arrTmp[1]);
            }
        } catch (Exception e) {
            Program.printError(e.toString());
        }
    }

    public Map<Integer, String> getLinkPool() {return this.linkPool;}

    class ThreadsDownloader extends Thread {
        private static final int BUF_SIZE = 1024;
        private final int index;

        ThreadsDownloader(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            for (Map.Entry<Integer, String> entry : linkPool.entrySet()) {
                String link;
                synchronized (Downloader.this) {
                    link = linkPool.get(entry.getKey());
                    if (link != null) {
                        linkPool.put(entry.getKey(), null);
                    } else {
                        continue;
                    }
                }
                String[] arrTmp = link.split("/");
                String outputFile = arrTmp[arrTmp.length - 1];
                System.out.printf("Thread-%d start download file number %d%n", index, entry.getKey());
                try (FileOutputStream resultFile = new FileOutputStream(outputFile);
                     BufferedInputStream linkReader = new BufferedInputStream(new URL(link).openStream())) {
                    byte[] buf = new byte[BUF_SIZE];
                    int len;
                    while ((len = linkReader.read(buf)) != -1) {
                        resultFile.write(buf, 0, len);
                    }
                } catch (Exception e) {
                    Program.printError(e.toString());
                }
                System.out.printf("Thread-%d finish download file number %d%n", index, entry.getKey());
            }
        }
    }
}
