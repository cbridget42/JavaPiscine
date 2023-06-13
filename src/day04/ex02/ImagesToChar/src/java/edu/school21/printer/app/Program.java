package edu.school21.printer.app;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.printer.logic.Logic;
import edu.school21.printer.logic.Args;
import com.beust.jcommander.JCommander;
import com.diogonunes.jcdp.color.ColoredPrinter;

public class Program {
    public static void main(String[] args) {
        Args jcArgs = new Args();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(jcArgs)
                .build();
        jCommander.parse(args);
        ColoredPrinter cPrinter = new ColoredPrinter();
        try {
            boolean[][] array = Logic.BMPToArray();
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    String color = (array[i][j]) ? jcArgs.getBlack() : jcArgs.getWhite();
                    cPrinter.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.NONE, Ansi.BColor.valueOf(color));
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
