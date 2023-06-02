package day00.ex04;

import java.util.Scanner;

public class Program {
    private static final int MAX_CHAR = 65535;
    private static final int SHOW = 10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] input = sc.nextLine().toCharArray();
        sc.close();
        int[] numberOfOccurrences = new int[MAX_CHAR];
        for (int i = 0; i < input.length; i++) {
            numberOfOccurrences[input[i]]++;
        }
        printResult(numberOfOccurrences, getTenMax(numberOfOccurrences));
    }

    private static void printResult(int[] numOfOccur, int[] maxes) {
        int[] columns = calculateColumns(numOfOccur, maxes);
        int row = SHOW;
        if (columns[0] != 0) {
            printNumber(numOfOccur[maxes[0]]);
            System.out.println();
        }
        for (int i = 0; i < SHOW; i++) {
            for (int j = 0; j < columns.length; j++) {
                if (row - 1 == columns[j]) {
                    printNumber(numOfOccur[maxes[j]]);
                }
                if (row <= columns[j]) {
                    System.out.print("  #");
                }
            }
            System.out.println();
            --row;
        }
        for (int i = 0; i < SHOW; i++) {
            System.out.print("  " + (char)maxes[i]);
        }
        System.out.println();
    }

    private static void printNumber(int num) {
        if (num < 10) {
            System.out.print("  ");
        } else if (num < 100) {
            System.out.print(" ");
        }
        System.out.print(num);
    }

    private static int[] calculateColumns(int[] numOfOccur, int[] maxes) {
        int[] res = new int[SHOW];
        if (numOfOccur[maxes[0]] != 0) {
            res[0] = SHOW;
        }
        for (int i = 1; i < SHOW; i++) {
            res[i] = (int)((double)numOfOccur[maxes[i]] / numOfOccur[maxes[0]] * 100) / 10;
        }
        return res;
    }

    private static int[] getTenMax(int[] numOfOccur) {
        int[] res = new int[SHOW];
        for (int i = 0; i < res.length; i++) {
            res[i] = getMax(numOfOccur, res, i);
        }
        return res;
    }

    private static int getMax(int[] numOfOccur, int[] arr, int cur) {
        int max = 0;
        int res = 0;
        for (int i = 0; i < numOfOccur.length; i++) {
            if (numOfOccur[i] > max && isIndexGood(arr, i, cur)) {
                max = numOfOccur[i];
                res = i;
            }
        }
        return res;
    }

    private static boolean isIndexGood(int[] arr, int index, int cur) {
        for (int i = 0; i < cur; i++) {
            if (arr[i] == index) {
                return false;
            }
        }
        return true;
    }
}
