package day00.ex03;

import java.util.Scanner;

public class Program {
    private static final String WEEK = "Week ";
    private static final String END_OF_LOOP = "42";
    private static final int MAX_WEEKS = 18;
    private static final int NUMBER_OF_TESTS = 5;
    private static final int TENTHLY = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long statistics = 0;
        boolean run = true;
        int curWeek = 1;
        while (run) {
            String input = sc.nextLine();
            if (input.equals(END_OF_LOOP)) {
                run = false;
            }else if (input.equals(WEEK + curWeek) && curWeek <= MAX_WEEKS) {
                statistics += getStatisticOfWeek(sc);
                statistics *= TENTHLY;
                ++curWeek;
            } else {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
        }
        printResult(statistics, curWeek);
    }

    private static void printResult(long stat, int curWeek) {
        long rank = getPow(TENTHLY, curWeek - 1);
        for (int i = 1; i < curWeek; i++) {
            System.out.print(WEEK + i + " ");
            int repeat = (int)(stat / rank % TENTHLY);
            for (int j = 0; j < repeat; j++) {
                System.out.print("=");
            }
            rank /= TENTHLY;
            System.out.println(">");
        }
    }

    private static int getStatisticOfWeek(Scanner sc) {
        int minGrade = 9;
        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            int tmp = sc.nextInt();
            if (minGrade > tmp) {
                minGrade = tmp;
            }
        }
        sc.nextLine();
        return minGrade;
    }

    private static long getPow(long x, int n) {
        for (int i = 1; i < n; i++) {
            x *= TENTHLY;
        }
        return x;
    }
}
