package day00.ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.close();
        if (n <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int sqRootN = getSqrt(n);
        for (int i = 2; i <= sqRootN; i++) {
            if (n % i == 0) {
                System.out.println("false " + (i - 1));
                return;
            }
        }
        System.out.println("true " + sqRootN);
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
}
