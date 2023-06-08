package day03.ex00;

public class Program {
    private static final String FLAG = "--count=";
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith(FLAG)) {
            printError("Wrong argument");
        }
        int count = Integer.parseInt(args[0].substring(FLAG.length()));
        if (count <= 0) {
            printError("count less than or equal to zero");
        }
        try {
            Egg egg = new Egg(count);
            Hen hen = new Hen(count);
            egg.start();
            hen.start();
            egg.join();
            hen.join();
            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    private static void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
