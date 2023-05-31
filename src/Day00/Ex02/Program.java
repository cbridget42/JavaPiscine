package Day00.Ex02;

import java.util.Scanner;

public class Program {
    private static final int END_OF_LOOP = 42;
    private static final int TENTHLY = 10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        boolean run = true;
        while (run) {
            int tmp = sc.nextInt();
            if (tmp == END_OF_LOOP) {
                run = false;
            } else {
                if (isPrime(getSumOfDigits(tmp))) {
                    ++count;
                }
            }
        }
        System.out.println("Count of coffee - request - " +  count);
    }

    private static boolean isPrime(int x) {
        int sqRootN = getSqrt(x);
        for (int i = 2; i <= sqRootN; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int getSqrt(int x) {
        double y = x;
        double z = (y + (x / y)) / 2;

        while (getAbs(y - z) >= 0.1) {
            y = z;
            z = (y + (x / y)) / 2;
        }
        return (int)z;
    }

    public static double getAbs(double x) {
        return (x < 0.0) ? -x : x;
    }

    private static int getSumOfDigits(int x) {
        int res = 0;
        while (x != 0) {
            res += x % TENTHLY;
            x /= TENTHLY;
        }
        return res;
    }
}
