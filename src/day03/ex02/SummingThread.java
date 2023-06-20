package day03.ex02;

public class SummingThread extends Thread {
    private final int[] array;
    private final int index;
    private final int numThreads;
    private final int addLenght;
    private static int sum = 0;

    public SummingThread(int[] array, int index, int numThreads) {
        this.array = array;
        this.index = index;
        this.numThreads = numThreads;
        this.addLenght = ((array.length / numThreads + 1) * (numThreads - 1) < array.length) ? 1 : 0;
    }

    @Override
    public void run() {
        int threadSum = 0;
        int start = (array.length / numThreads + addLenght) * index;
        int end = (array.length / numThreads + addLenght) * (index + 1);
        if (index + 1 == numThreads && end != array.length) {
            end = array.length;
        }
        for (int i = start; i < end; i++) {
            threadSum += array[i];
        }
        synchronized (this) {
            this.sum += threadSum;
            System.out.printf("Thread %d: from %d to %d sum is %d%n", index + 1, start, end - 1, threadSum);
        }
    }

    public static int getSum() {return sum;}
}
