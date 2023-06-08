package day03.ex02;

public class Program {
    public static void main(String[] args) {
        if (args.length != ProgramUtils.NUMBER_OF_ARGUMENTS) {
            ProgramUtils.printError("Wrong number of arguments");
        } else if (!args[0].startsWith(ProgramUtils.ARRAY_SIZE) ||
                !args[1].startsWith(ProgramUtils.THREAD_COUNT)) {
            ProgramUtils.printError("Wrong flags");
        }
        int arrSize = Integer.parseInt(args[0].substring(ProgramUtils.ARRAY_SIZE.length()));
        int numThreads = Integer.parseInt(args[1].substring(ProgramUtils.THREAD_COUNT.length()));
        if (arrSize < 0 || arrSize < numThreads || arrSize > ProgramUtils.MAX_ARRAY_SIZE) {
            ProgramUtils.printError("Wrong arguments");
        }

        int[] array = ProgramUtils.createRandomArray(arrSize);
        ProgramUtils.summarizationWithThreads(array, numThreads);
    }
}
