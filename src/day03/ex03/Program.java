package day03.ex03;

public class Program {
    private static final String FLAG = "--threadsCount=";

    public static void main(String[] args) {
        if (args.length != 1) {
            printError("Wrong number of arguments");
        } else if (!args[0].startsWith(FLAG)) {
            printError("Wrong flag");
        }
        int threadsCount = Integer.parseInt(args[0].substring(FLAG.length()));
        if (threadsCount <= 0) {
            printError("threadsCount cannot be less than or equal to 0");
        }
        Downloader downloader = new Downloader(threadsCount);
        downloader.strartDownload();
        //System.out.println(downloader.getLinkPool());
    }

    public static void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
