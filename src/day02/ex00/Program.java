package day02.ex00;

import java.util.Scanner;

public class Program {
    private static final String TERMINATOR = "42";
    public static void main(String[] args) {
        SignatureChecker checker = new SignatureChecker();
        Scanner sc = new Scanner(System.in);
        String inputFile = sc.nextLine();
        while (!inputFile.equals(TERMINATOR)) {
            checker.checkSignature(inputFile);
            inputFile = sc.nextLine();
        }
    }
}
