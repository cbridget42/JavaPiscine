package day02.ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length !=  2) {
            System.err.println("Wrong number of arguments");
            System.exit(-1);
        }
        Similarity sim = new Similarity(args[0], args[1]);
        sim.createDictionaryFile();
        System.out.println("Similarity = " + sim.calculateSimilarity());
    }
}
