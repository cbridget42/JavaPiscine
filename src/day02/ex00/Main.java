package day02.ex00;

import java.util.Scanner;

public class Main {
    private static final String TERMINATOR = "42";
    public static void main(String[] args) {
        SignatureChecker checker = new SignatureChecker();
        System.out.println(checker.getSignatures());
        Scanner sc = new Scanner(System.in);
        String inputFile = sc.nextLine();
        while (!inputFile.equals(TERMINATOR)) {
            checker.checkSignature(inputFile);
            inputFile = sc.nextLine();
        }
    }
}
