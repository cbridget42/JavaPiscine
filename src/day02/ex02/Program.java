package day02.ex02;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("wrong number of arguments!");
            System.exit(-1);
        }
        CommandLineEmulator emulator = new CommandLineEmulator(args[0]);
        emulator.commandsLoop();
    }
}
