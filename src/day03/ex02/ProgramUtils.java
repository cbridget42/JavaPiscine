package day03.ex02;

import java.util.Random;

public class ProgramUtils {
    public static final int NUMBER_OF_ARGUMENTS = 2;
    public static final int MAX_ARRAY_SIZE = 2_000_000;
    public static final String ARRAY_SIZE = "--arraySize=";
    public static final String THREAD_COUNT = "--threadsCount=";

    public static void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }

    public static int[] createRandomArray(int size) {
        Random rand = new Random();
        int[] res = new int[size];
        int sum = 0;
        for (int i = 0; i < size; i++) {
            res[i] = rand.nextInt(2000) - 1000;
            sum += res[i];
        }
        System.out.println("Sum: " + sum);
        return res;
    }

    public static void summarizationWithThreads(int[] array, int numThreads) {
        SummingThread[] threads = new SummingThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SummingThread(array, i, numThreads);
            threads[i].start();
        }
        try {
            for (SummingThread tmp : threads) {
                tmp.join();
            }
        } catch (Exception e) {
            printError(e.toString());
        }
        System.out.println("Sum by threads: " + SummingThread.getSum());
    }
}
