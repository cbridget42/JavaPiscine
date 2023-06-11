package day03.ex03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
        //System.out.println(this.linkPool.toString());
    }

    public void strartDownload() {
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
                URL fileLink = null;
                //BufferedWriter fileWriter = null;
                try {
                    fileLink = new URL(link);
                    String[] arrTmp = fileLink.getFile().split("/");
                    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(arrTmp[arrTmp.length - 1]));

                } catch (Exception e) {
                    Program.printError(e.toString());
                }
                System.out.printf("Thread-%d start download file number %d%nFile name: %s%n", index, entry.getKey(), fileLink.getFile());
            }
        }
    }
}
