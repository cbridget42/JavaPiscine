package day02.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SignatureChecker {
    private static final String SIGNATURE_PATH = "src/day02/ex00/signatures.txt";
    private static final String RESULT_PATH = "src/day02/ex00/result.txt";
    private static final int BUFF_SIZE = 2048;
    private static final int MAX_SIGNATURE_LENGTH = 20;
    private static final String UNDEF = "UNDEFINED";
    private static final String PROC = "PROCESSED";
    private final Map<String, String> signatures;
    private static boolean append = false;

    public SignatureChecker() {
        this.signatures = new HashMap<>();
        try (FileInputStream signFile = new FileInputStream(SIGNATURE_PATH)) {
            int size = signFile.available();
            byte[] bytes = new byte[size];
            if (signFile.read(bytes) != size) {
                printError("can't read file");
            }
            char[] buf = new char[BUFF_SIZE];
            for (int i = 0, j = 0; i < bytes.length; i++) {
                if ((char) bytes[i] != '\n') {
                    buf[j++] = (char) bytes[i];
                }
                if ((char) bytes[i] == '\n' || i + 1 == bytes.length) {
                    String[] tmp = String.valueOf(buf, 0, j).replace(" ", "").split(",");
                    this.signatures.put(tmp[1], tmp[0]);
                    j = 0;
                }
            }
        } catch (Exception e) {
            printError(e.toString());
        }
    }

    public void checkSignature(String inputFilePath) {
        try (FileInputStream inputFile = new FileInputStream(inputFilePath)) {
            byte[] bytes = new byte[MAX_SIGNATURE_LENGTH];
            int size = (inputFile.available() >= MAX_SIGNATURE_LENGTH) ?
                    MAX_SIGNATURE_LENGTH : inputFile.available();
            if (inputFile.read(bytes, 0, size) != size) {
                System.err.println("can't read file");
                return;
            }
            String sign = getFileSignature(bytes);
            if (sign == null) {
                System.out.println(UNDEF);
            } else {
                writeResult(sign + '\n');
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void writeResult(String sign) {
        try (FileOutputStream resultFile = new FileOutputStream(RESULT_PATH, append)) {
            resultFile.write(sign.getBytes());
            System.out.println(PROC);
        } catch (Exception e) {
            System.err.println(e);
        }
        if (!append) {
            append = true;
        }
    }

    private String getFileSignature(byte[] a) {
        String hexSign = "";
        String res = null;
        for(byte b : a) {
            hexSign += String.format("%02X", b);
            res = signatures.get(hexSign);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    private void printError(String err) {
        System.err.println(err);
        System.exit(-1);
    }

    public Map<String, String> getSignatures() {return this.signatures;}
}
