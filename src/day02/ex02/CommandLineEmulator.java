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
        if (!path.startsWith(FLAG)) {
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
            } else if (com.length == 2 && com[0].equals("cd")) {
                executeCd(com[1]);
            } else if (com.length == 3 && com[0].equals("mv")) {
                executeMv(com[1], com[2]);
            } else {
                System.out.println("Wrong command! try again");
            }
        }
        sc.close();
    }

    private void executeMv(String oldName, String newName) {
        Path oldPath = Paths.get(workingDir.toString(), oldName);
        if (!Files.exists(oldPath)) {
            System.out.println("Wrong mv argument");
            return;
        }
        Path newPath = Paths.get(workingDir.toString(), newName);
        if (Files.isDirectory(newPath)) {
            newPath = Paths.get(newPath.toString(), oldName);
        }
        try {
            Files.move(oldPath, newPath);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void executeCd(String folder) {
        Path newPath = Paths.get(workingDir.toString(), folder);
        newPath = newPath.normalize();
        if (newPath.isAbsolute() && Files.isDirectory(newPath)) {
            workingDir = newPath;
            System.out.println(workingDir);
        } else {
            System.out.println("Wrong cd argument");
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
                } else if (!Files.isSymbolicLink(tmp)) {
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
                if (!Files.isDirectory(tmp) && !Files.isSymbolicLink(tmp)) {
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
