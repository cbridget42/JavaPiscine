package day02.ex02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class CommandLineEmulator {
    private static final String FLAG = "--current-folder=";
    private Path workingDir;
    private Scanner sc;

    public CommandLineEmulator(String path) {
        if (!path.contains(FLAG)) {
            printError("Invalid argument");
        }
        this.workingDir = Paths.get(path.substring(FLAG.length()));
        if (!workingDir.isAbsolute() || !Files.isDirectory(workingDir)) {
            printError("Wrong path!");
        }
        this.sc = new Scanner(System.in);
        System.out.println(path.substring(FLAG.length()));
    }

    public void commandsLoop() {
        boolean run = true;
        while (run) {
            String[] com = sc.nextLine().split(" ");
            if (com.length == 1 && com[0].equals("ls")) {
                executeLs();
            } else if (com.length == 1 && com[0].equals("exit")) {
                run = false;
            } else {
                System.out.println("Wrong command! try again");
            }
        }
    }

    private void executeLs() {
        try (Stream<Path> paths = Files.walk(workingDir, 1)) {
            long size = 0;
            for (Path tmp : paths.toArray(Path[]::new)) {
                if (tmp.equals(workingDir)) {
                    continue;
                } else if (Files.isDirectory(tmp)) {
                    size = getDirectorySize(tmp);
                } else {
                    size = Files.size(tmp);
                }
                System.out.println(tmp.getFileName().toString() + " " +
                        (size / 1000) + " KB");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private long getDirectorySize(Path pth) {
        long size = 0;
        try (Stream<Path> paths = Files.walk(pth)) {
            for (Path tmp : paths.toArray(Path[]::new)) {
                if (!Files.isDirectory(tmp)) {
                    size += Files.size(tmp);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return size;
    }

    private void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }
}
