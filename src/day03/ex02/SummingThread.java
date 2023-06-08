package day03.ex02;

public class SummingThread extends Thread {
    private int[] array;
    private int index;
    private int numThreads;
    private int sum;

    public SummingThread(int[] array, int index, int numThreads) {
        this.array = array;
        this.index = index;
        this.numThreads = numThreads;
        this.sum = 0;
    }

    public int getSum() {
        start();
        return this.sum;
    }

    @Override
    public void run() {
        int start = (array.length / numThreads) * index;
        int end = (array.length / numThreads) * (index + 1);
        if (end > array.length) {
            end = array.length;
        }
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        System.out.printf("Thread %d: from %d to %d sum is %d", index, start, end, sum);
    }
}
