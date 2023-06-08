package day03.ex01;

public class Program {
    private static final String FLAG = "--count=";
    private static boolean order;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(FLAG)) {
            printError("Wrong argument");
        }
        int count = Integer.parseInt(args[0].substring(FLAG.length()));
        if (count <= 0) {
            printError("count less than or equal to zero");
        }
        try {
            order = true;
            Egg egg = new Egg(count);
            Hen hen = new Hen(count);
            egg.start();
            hen.start();
            egg.join();
            hen.join();
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    public static synchronized void printWord(String word, boolean egg) {
        try {
            if (egg) {
                while (!order) {
                    Program.class.wait();
                }
            } else {
                while (order) {
                    Program.class.wait();
                }
            }
            System.out.println(word);
            order = !order;
            Program.class.notify();
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    private static void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
