package day03.ex03;

import java.io.BufferedReader;
import java.io.FileReader;
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
}
