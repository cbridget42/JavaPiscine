package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            printError("Wrong number of arguments");
        }
        if (args[0].length() != 1 || args[1].length() != 1) {
            printError("the first two arguments must be a character");
        }
        try {
            char[][] array = Logic.BMPToArray(args[0].charAt(0), args[1].charAt(0));
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j]);
                }
                System.out.println();
            }
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    public static void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
